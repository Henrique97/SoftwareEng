package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

@RunWith(JMockit.class)
public class TaxPaymentStateProcessMethodTest {
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private static final String TAX_CONFIRMATION = "TaxConfirmation";
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private Adventure adventure;
	private static final String BUYERNIF = "987654321";
	
	
	private Broker broker;

	@Before
	public void setUp() {
		broker = new Broker("BR99", "WeExplorer", "123456689","987854321", "BE68539002347034");
		this.adventure = new Adventure(this.broker, this.begin, this.end, new Client(IBAN, 20, BUYERNIF), AMOUNT);
		/*invoice = new InvoiceData(adventure.getBroker().getSellerNIF(),
				adventure.getBroker().getBuyerNIF(),
				"ADVENTURE",(200)*(1+adventure.getAmount()),adventure.getBegin());*/
		this.adventure.setState(State.TAX_PAYMENT);
	}

	@Test
	public void success(@Mocked final TaxInterface taxInterface, @Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{	
				ActivityInterface.getActivityReservationData(this.anyString);
				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = TAX_CONFIRMATION;
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.CONFIRMED, this.adventure.getState());
	}

	@Test
	public void TaxException(@Mocked final TaxInterface taxInterface, @Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(this.anyString);
				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = new TaxException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.CANCELLED, this.adventure.getState());
	}

	@Test
	public void singleRemoteAccessException(@Mocked final TaxInterface taxInterface, @Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(this.anyString);
				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.TAX_PAYMENT, this.adventure.getState());
	}

	@Test
	public void maxRemoteAccessException(@Mocked final TaxInterface taxInterface, @Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(this.anyString);
				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.CANCELLED, this.adventure.getState());
	}

	@Test
	public void maxMinusOneRemoteAccessException(@Mocked final TaxInterface taxInterface, @Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(this.anyString);
				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.TAX_PAYMENT, this.adventure.getState());
	}

	@Test
	public void twoRemoteAccessExceptionOneSuccess(@Mocked final TaxInterface taxInterface, @Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(this.anyString);
				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 2) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							return TAX_CONFIRMATION;
						}
					}
				};
				this.times = 3;

			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.CONFIRMED, this.adventure.getState());
	}

	@Test
	public void oneRemoteAccessExceptionOneTaxException(@Mocked final TaxInterface taxInterface, @Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(this.anyString);
				TaxInterface.submitInvoice((InvoiceData) this.any);

				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 1) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							throw new TaxException();
						}
					}
				};
				this.times = 2;

			}
		};

		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.CANCELLED, this.adventure.getState());
	}
	
	@After
	public void tearDown() {
		Broker.brokers.clear();
	}

}