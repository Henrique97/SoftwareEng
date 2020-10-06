package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Delegate;
import mockit.Expectations;
import mockit.FullVerifications;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.car.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.car.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.car.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

@RunWith(JMockit.class)
public class InvoiceProcessorSubmitBookingMethodTest {
	private static final String CANCEL_PAYMENT_REFERENCE = "CancelPaymentReference";
	private static final String INVOICE_REFERENCE = "InvoiceReference";
	private static final String PAYMENT_REFERENCE = "PaymentReference";
	private static final double AMOUNT = 20000.00;
	private static final String DRIVING_LICENSE = "br112233";
	private static final String IBAN = "IBAN";
	private static final String NIF = "123456789";
	private static final String RENT_A_CAR_IBAN = "IBAN2";
	private static final String RENT_A_CAR_NIF = "987654321";
	private static final LocalDate BEGIN = new LocalDate(2016, 12, 19);
	private static final LocalDate END = new LocalDate(2016, 12, 21);
	private RentACar rentACar;
	private Vehicle vehicle;
	private Renting renting;

	@Before
	public void setUp() {
		this.rentACar = new RentACar("XtremX", RENT_A_CAR_NIF, RENT_A_CAR_IBAN);
		this.vehicle = new Car("22-33-HZ", 20000, this.rentACar, AMOUNT);
		this.renting = new Renting(this.rentACar, DRIVING_LICENSE, BEGIN, END, this.vehicle, NIF, IBAN);

	}

	@Test
	public void success(@Mocked final TaxInterface taxInterface, @Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);
				TaxInterface.submitInvoice((InvoiceData) this.any);
			}
		};

		this.rentACar.getProcessor().submitRenting(this.renting);

		new FullVerifications() {
			{
			}
		};
	}

	@Test
	public void oneTaxFailureOnSubmitInvoice(@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);
				this.result = PAYMENT_REFERENCE;
				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = new TaxException();
				this.result = INVOICE_REFERENCE;
			}
		};

		this.rentACar.getProcessor().submitRenting(this.renting);
		this.rentACar.getProcessor().submitRenting(new Renting(this.rentACar, DRIVING_LICENSE, BEGIN, END, this.vehicle, NIF, IBAN));

		new FullVerifications(taxInterface) {
			{
				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.times = 3;
			}
		};
	}

	@Test
	public void oneRemoteFailureOnSubmitInvoice(@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);
				this.result = PAYMENT_REFERENCE;
				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = new RemoteAccessException();
				this.result = INVOICE_REFERENCE;
			}
		};

		this.rentACar.getProcessor().submitRenting(this.renting);
		this.rentACar.getProcessor().submitRenting(new Renting(this.rentACar, DRIVING_LICENSE, BEGIN, END, this.vehicle, NIF, IBAN));
		
		new FullVerifications(taxInterface) {
			{
				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.times = 3;
			}
		};
	}

	@Test
	public void oneBankFailureOnProcessPayment(@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);
				this.result = new BankException();
				this.result = PAYMENT_REFERENCE;
				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = INVOICE_REFERENCE;
			}
		};

		this.rentACar.getProcessor().submitRenting(this.renting);
		this.rentACar.getProcessor().submitRenting(new Renting(this.rentACar, DRIVING_LICENSE, BEGIN, END, this.vehicle, NIF, IBAN));
		
		new FullVerifications(bankInterface) {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);
				this.times = 3;
			}
		};
	}

	@Test
	public void oneRemoteFailureOnProcessPayment(@Mocked final TaxInterface taxInterface,
			@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);
				this.result = new RemoteAccessException();
				this.result = PAYMENT_REFERENCE;
				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = INVOICE_REFERENCE;
			}
		};

		this.rentACar.getProcessor().submitRenting(this.renting);
		this.rentACar.getProcessor().submitRenting(new Renting(this.rentACar,DRIVING_LICENSE, BEGIN, END, this.vehicle, NIF, IBAN));
		
		new FullVerifications(bankInterface) {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);
				this.times = 3;
			}
		};
	}


	@After
	public void tearDown() {
		Vehicle.plates.clear();
		RentACar.rentACars.clear();
	}

}
