package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;
import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;
import pt.ulisboa.tecnico.softeng.tax.domain.Seller;

@RunWith(JMockit.class)
public class AdventureSequenceTest {
	private static final String IBAN = "BK01987654321";
	private static final String IBANb = "BK01987654322";
	private static final String NIFb = "123456789";
	private static final int AMOUNT = 300;
	private static final int AGE = 20;
	private static final String PAYMENT_CONFIRMATION = "PaymentConfirmation";
	private static final String PAYMENT_CANCELLATION = "PaymentCancellation";
	private static final String ACTIVITY_CONFIRMATION = "ActivityConfirmation";
	private static final String ACTIVITY_CANCELLATION = "ActivityCancellation";
	private static final String TAX_CONFIRMATION = "TaxConfirmation";
	private static final String TAX_CANCELLATION = "TaxCancellation";
	private static final String ROOM_CONFIRMATION = "RoomConfirmation";
	private static final String ROOM_CANCELLATION = "RoomCancellation";
	private static final LocalDate arrival = new LocalDate(2016, 12, 19);
	private static final LocalDate departure = new LocalDate(2016, 12, 21);
	private static final String BUYERNIF = "987652121";
	private static final String PLATE_CAR = "22-33-HH";
	private static final String RENT_A_CAR_NAME = "Eartz2";
	private static final String RENT_A_CAR_NIF = "NIF2";
	private static final String RENT_A_CAR_IBAN = "IBAN2";
	private static final RentACar RENTER = new RentACar(RENT_A_CAR_NAME, RENT_A_CAR_NIF, RENT_A_CAR_IBAN);
	private static final Vehicle CART = new Car(PLATE_CAR, 10, RENTER ,50);
	private static final Client CLIENT = new Client(IBAN, AGE, BUYERNIF);
	private static final Set<Vehicle> CARS = Stream.of(CART).collect(Collectors.toCollection(HashSet::new));
/*	private static final ItemType item = new ItemType(IRS.getIRS(), "ADVENTURE", 23);
	private static final Seller seller = new Seller(IRS.getIRS(), NIFb, "BR98", "Somewhere");
	private static final Buyer buyer = new Buyer(IRS.getIRS(), BUYERNIF, "Manuel Comprado", "Anywhere"); */
	
	@Injectable
	private Broker broker;

	@Test
	public void successSequenceOne(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface,
			@Mocked final CarInterface carInterface, @Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(IBAN, AMOUNT+20);
				this.result = PAYMENT_CONFIRMATION;

				ActivityInterface.reserveActivity(arrival, departure, AGE,broker.getBuyerNIF(), broker.getIBAN());
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, broker.getBuyerNIF(), broker.getIBAN());
				this.result = ROOM_CONFIRMATION;

				BankInterface.getOperationData(PAYMENT_CONFIRMATION);

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);
				
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION).getAmount();
				this.result = 10;
				
				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION).getPaymentReference();
				this.result="PayRef";

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION).getInvoiceReference();
				this.result="InvoiceRef";
				
				HotelInterface.getRoomBookingData(ROOM_CONFIRMATION);
				
				HotelInterface.getRoomBookingData(ROOM_CONFIRMATION).getAmount();
				this.result=10;
				
				HotelInterface.getRoomBookingData(ROOM_CONFIRMATION).getPaymentReference();
				this.result = "PayRef";
				
				HotelInterface.getRoomBookingData(ROOM_CONFIRMATION).getInvoiceReference();
				this.result = "InvoiceRef";

				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = TAX_CONFIRMATION;
				
			}
		};

		Adventure adventure = new Adventure(this.broker, arrival, departure, CLIENT, AMOUNT);
		
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		
		Assert.assertEquals(State.CONFIRMED, adventure.getState());
	}

	@Test
	public void successSequenceTwo(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface, @Mocked final TaxInterface taxinterface) {
		new Expectations() {
			{
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;

				ActivityInterface.reserveActivity(arrival, arrival, AGE,broker.getBuyerNIF(), broker.getIBAN());
				this.result = ACTIVITY_CONFIRMATION;

				BankInterface.getOperationData(PAYMENT_CONFIRMATION);

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION).getPaymentReference();
				this.result="PayRef";

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION).getInvoiceReference();
				this.result="InvoiceRef";
				
				TaxInterface.submitInvoice((InvoiceData) this.any);
				this.result = TAX_CONFIRMATION;
			}
		};

		Adventure adventure = new Adventure(this.broker, arrival, arrival, new Client(IBAN, AGE, BUYERNIF), AMOUNT);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();


		Assert.assertEquals(State.CONFIRMED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceOne(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(arrival, departure, AGE,broker.getBuyerNIF(), broker.getIBAN());
				this.result = new ActivityException();
			}
		};

		Adventure adventure = new Adventure(this.broker, arrival, departure, new Client(IBAN, AGE, BUYERNIF), AMOUNT);
		adventure.process();

		Assert.assertEquals(State.UNDO, adventure.getState());
	}

	
	@Test
	public void unsuccessSequenceTwo(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(arrival, departure, AGE,broker.getBuyerNIF(), broker.getIBAN());
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, broker.getBuyerNIF(), broker.getIBAN());
				this.result = new HotelException();

				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;
			}
		};

		Adventure adventure = new Adventure(this.broker, arrival, departure, new Client(IBAN, AGE, BUYERNIF), AMOUNT);

		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}


	@Test
	public void unsuccessSequenceThree(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface) {
		new Expectations() {
			{
				
				ActivityInterface.reserveActivity(arrival, departure, AGE,broker.getBuyerNIF(), broker.getIBAN());
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, broker.getBuyerNIF(), broker.getIBAN());
				this.result = ROOM_CONFIRMATION;

				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = new BankException();
				
			}
		};

		Adventure adventure = new Adventure(this.broker, arrival, departure, new Client(IBAN, AGE, BUYERNIF), AMOUNT);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();


		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceFour(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;

				ActivityInterface.reserveActivity(arrival, departure, AGE,broker.getBuyerNIF(), broker.getIBAN());
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, broker.getIBAN(), broker.getIBAN());
				this.result = ROOM_CONFIRMATION;
				
				
			}
		};

		Adventure adventure = new Adventure(this.broker, arrival, departure, new Client(IBAN, AGE, BUYERNIF), AMOUNT);

		adventure.process();
		adventure.process();
		adventure.process();
		for (int i = 0; i < ConfirmedState.MAX_BANK_EXCEPTIONS; i++) {
			adventure.process();
		}


		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

}