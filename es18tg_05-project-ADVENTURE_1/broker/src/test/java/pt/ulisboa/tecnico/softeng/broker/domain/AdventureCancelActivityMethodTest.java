package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;
import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

public class AdventureCancelActivityMethodTest {
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private Broker broker;
	private String IBAN;
	private Adventure adventure;

	@Before
	public void setUp() {
		this.broker = new Broker("BR01", "eXtremeADVENTURE");

		Bank bank = new Bank("Money", "BK01");
		Client client = new Client(bank, "António");
		Account account = new Account(bank, client);
		this.IBAN = account.getIBAN();
		account.deposit(1000);

		Hotel hotel = new Hotel("XPTO123", "Paris");
		new Room(hotel, "01", Type.SINGLE);

		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure");
		Activity activity = new Activity(provider, "Bush Walking", 18, 80, 10);
		new ActivityOffer(activity, this.begin, this.end);
		new ActivityOffer(activity, this.begin, this.begin);
		this.adventure = new Adventure(this.broker, this.begin, this.end, 20, this.IBAN, 300);
	}

	@Test
	public void activityConfirmedNotCancelled() {
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		
		// the activity is confirmed, so it can be cancelled
		Assert.assertTrue(this.adventure.cancelActivity());
	}
	
	@Test
	public void activityNotConfirmedNotCancelled() {
		// the activity is not confirmed
		Assert.assertFalse(this.adventure.cancelActivity());
	}
	
	@Test
	public void activityConfirmedCancelled() {
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		
		// force the UNDO state
		this.adventure.setState(State.UNDO);
		this.adventure.process();
		
		// the activity is already cancelled
		Assert.assertFalse(this.adventure.cancelActivity());
	}

	@After
	public void tearDown() {
		Bank.banks.clear();
		Hotel.hotels.clear();
		ActivityProvider.providers.clear();
		Broker.brokers.clear();
	}
}
