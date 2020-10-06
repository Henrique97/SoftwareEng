package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class BookingConstructorTest {
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 21);
	private static final String HOTEL_NIF = "987654321";
	private static final String HOTEL_IBAN = "IBAN1";
	private static final String BUYER_NIF = "12345678";
	private static final String BUYER_IBAN = "IBAN2";
	
	private Hotel hotel;
	private Room room;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Londres", HOTEL_NIF, HOTEL_IBAN);
    this.room = new Room(this.hotel,"201",Room.Type.SINGLE,120);
	}

	@Test
	public void success() {
		Booking booking = new Booking(this.hotel, this.room, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
		
		Assert.assertTrue(booking.getReference().startsWith(this.hotel.getCode()));
		Assert.assertTrue(booking.getReference().length() > Hotel.CODE_SIZE);
		Assert.assertEquals(this.arrival, booking.getArrival());
		Assert.assertEquals(this.departure, booking.getDeparture());
		Assert.assertEquals(BUYER_NIF, booking.getNif());
		Assert.assertEquals(BUYER_IBAN, booking.getIban());
		Assert.assertEquals(HOTEL_NIF, booking.getHotelNif());
	}

	@Test(expected = HotelException.class)
	public void nullHotel() {
		new Booking(null, this.room, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
	}
	
	@Test(expected = HotelException.class)
	public void nullRoom() {
		new Booking(this.hotel, null, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
	}

	@Test(expected = HotelException.class)
	public void nullArrival() {
		new Booking(this.hotel, this.room, null, this.departure, BUYER_NIF, BUYER_IBAN);
	}

	@Test(expected = HotelException.class)
	public void nullDeparture() {
		new Booking(this.hotel, this.room, this.arrival, null, BUYER_NIF, BUYER_IBAN);
	}
	
	@Test(expected = HotelException.class)
	public void nullNif() {
		new Booking(this.hotel, this.room, this.arrival, this.departure, null, BUYER_IBAN);
	}
	
	@Test(expected = HotelException.class)
	public void emptyNif() {
		new Booking(this.hotel, this.room, this.arrival, this.departure, "", BUYER_IBAN);
	}

	@Test(expected = HotelException.class)
	public void nullIban() {
		new Booking(this.hotel, this.room, this.arrival, this.departure, BUYER_NIF, null);
	}
	
	@Test(expected = HotelException.class)
	public void emptyIban() {
		new Booking(this.hotel, this.room, this.arrival, this.departure, BUYER_NIF, "");
	}

	@Test(expected = HotelException.class)
	public void departureBeforeArrival() {
		new Booking(this.hotel, this.room, this.arrival, this.arrival.minusDays(1), BUYER_NIF, BUYER_IBAN);
	}

	@Test
	public void arrivalEqualDeparture() {
		new Booking(this.hotel, this.room, this.arrival, this.arrival, BUYER_NIF, BUYER_IBAN);
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
