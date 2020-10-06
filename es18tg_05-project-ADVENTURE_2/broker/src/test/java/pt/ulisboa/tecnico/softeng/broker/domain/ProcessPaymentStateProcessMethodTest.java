package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
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

@RunWith(JMockit.class)
public class ProcessPaymentStateProcessMethodTest {
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private static final String PAYMENT_CONFIRMATION = "PaymentConfirmation";
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private Adventure adventure;
	private static final String BUYERNIF = "987654321";
	private static final String ACTIVITY_CONFIRMATION = "ActivityConfirmation";
	private static final String ACTIVITY_CANCELLATION = "ActivityCancellation";
	private static final LocalDate arrival = new LocalDate(2016, 12, 19);
	private static final LocalDate departure = new LocalDate(2016, 12, 21);
	

	@Injectable
	private Broker broker;

	@Before
	public void setUp() {
		this.adventure = new Adventure(this.broker, this.begin, this.end, new Client(IBAN, 20, BUYERNIF), AMOUNT);
		this.adventure.setState(State.PROCESS_PAYMENT);
	}

	@Test
	public void success(@Mocked final BankInterface bankInterface,@Mocked final ActivityInterface activityInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		new Expectations() {
			{

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				
				
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION).getAmount();
				this.result = 10;
				
				BankInterface.processPayment(IBAN, AMOUNT+10);
				this.result = PAYMENT_CONFIRMATION;
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.TAX_PAYMENT, this.adventure.getState());
	}

	@Test
	public void bankException(@Mocked final BankInterface bankInterface,@Mocked final ActivityInterface activityInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				
				
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION).getAmount();
				this.result = 10;
				BankInterface.processPayment(IBAN, AMOUNT+10);
				this.result = new BankException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.CANCELLED, this.adventure.getState());
	}

	@Test
	public void singleRemoteAccessException(@Mocked final BankInterface bankInterface,@Mocked final ActivityInterface activityInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				
				
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION).getAmount();
				this.result = 10;
				BankInterface.processPayment(IBAN, AMOUNT+10);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.PROCESS_PAYMENT, this.adventure.getState());
	}

	@Test
	public void maxRemoteAccessException(@Mocked final BankInterface bankInterface,@Mocked final ActivityInterface activityInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		new Expectations() {
			{
					ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
					
					
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION).getAmount();
				this.result = 10;
				BankInterface.processPayment(IBAN, AMOUNT+10);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.CANCELLED, this.adventure.getState());
	}

	@Test
	public void maxMinusOneRemoteAccessException(@Mocked final BankInterface bankInterface,@Mocked final ActivityInterface activityInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		new Expectations() {
			{

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				
				
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION).getAmount();
				this.result = 10;
				BankInterface.processPayment(IBAN, AMOUNT+10);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.PROCESS_PAYMENT, this.adventure.getState());
	}

	@Test
	public void twoRemoteAccessExceptionOneSuccess(@Mocked final BankInterface bankInterface,@Mocked final ActivityInterface activityInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				
				
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION).getAmount();
				this.result = 10;
				BankInterface.processPayment(IBAN, AMOUNT+10);
				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 2) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							return PAYMENT_CONFIRMATION;
						}
					}
				};
				this.times = 3;

			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.TAX_PAYMENT, this.adventure.getState());
	}

	@Test
	public void oneRemoteAccessExceptionOneBankException(@Mocked final BankInterface bankInterface,@Mocked final ActivityInterface activityInterface) {
		this.adventure.setActivityConfirmation(ACTIVITY_CONFIRMATION);
		new Expectations() {
			{
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				
				
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION).getAmount();
				this.result = 10;
				BankInterface.processPayment(IBAN, AMOUNT+10);

				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 1) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							throw new BankException();
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

}