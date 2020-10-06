package pt.ulisboa.tecnico.softeng.broker.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;
import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.broker.domain.Client;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;
import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;
import pt.ulisboa.tecnico.softeng.tax.domain.Seller;

public class AdventureProcessMethodTest {
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);

	private static final String HOTEL_NIF = "987654321";
	private static final String HOTEL_IBAN = "IBAN1";
	private static final String BUYERNIF = "987654321";
  
	private Broker broker;
	private String IBAN;
	private RentACar rentACar;
	private ItemType item;
	private static final int TAX = 23;
	private static final String ADVENTURE = "ADVENTURE";
	private String 	seller;
	private String buyer;
	
	@Before
	public void setUp() {
		this.broker = new Broker("BR98", "EXTREMEADVENTURE","967456789","987235321", "BE68578507547034");

		Bank bank = new Bank("Money", "BK01");
		pt.ulisboa.tecnico.softeng.bank.domain.Client client = new pt.ulisboa.tecnico.softeng.bank.domain.Client(bank, "António");
		Account account = new Account(bank, client);
		this.IBAN = account.getIBAN();
		account.deposit(1000);
		Hotel hotel = new Hotel("XPTO123", "Paris", HOTEL_NIF, HOTEL_IBAN);
		new Room(hotel, "01", Type.SINGLE,70);
		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure", "NIF", "IBAN");
		Activity activity = new Activity(provider, "Bush Walking", 18, 80, 10);
		new ActivityOffer(activity, this.begin, this.end, 30);
		new ActivityOffer(activity, this.begin, this.begin, 30);
		rentACar = new RentACar("Rentings", "987654379", "IBAN7");
		this.item = new ItemType(IRS.getIRS(), ADVENTURE, TAX);
		new Seller(IRS.getIRS(), broker.getSellerNIF(), "BR98", "Somewhere");
		new Buyer(IRS.getIRS(), new Client(this.IBAN, 20, BUYERNIF).getNIF(), "Manuel Comprado", "Anywhere");
	}

	@Test
	public void success() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, new Client(this.IBAN, 20, BUYERNIF), 300);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		assertEquals(Adventure.State.CONFIRMED, adventure.getState());
		assertNotNull(adventure.getPaymentConfirmation());
		assertNotNull(adventure.getRoomConfirmation());
		assertNotNull(adventure.getActivityConfirmation());
	}

	@Test
	public void successNoHotelBooking() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.begin, new Client(this.IBAN, 20, BUYERNIF), 300);

		adventure.process();		
		adventure.process();
		adventure.process();
		adventure.process();
		
		assertEquals(Adventure.State.CONFIRMED, adventure.getState());
		assertNotNull(adventure.getPaymentConfirmation());
		assertNotNull(adventure.getActivityConfirmation());
	}

	@After
	public void tearDown() {
		Bank.banks.clear();
		Hotel.hotels.clear();
		ActivityProvider.providers.clear();
		Broker.brokers.clear();
		RentACar.rentACars.clear();
		IRS.getIRS().clearAll();
	}
}
