package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.fail;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class RoomReserveMethodTest {
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 24);
	private static final String HOTEL_NIF = "987654321";
	private static final String HOTEL_IBAN = "IBAN1";
	private static final String BUYER_NIF = "12345678";
	private static final String BUYER_IBAN = "IBAN2";
	
	private Room room;

	@Before
	public void setUp() {
		Hotel hotel = new Hotel("XPTO123", "Lisboa", HOTEL_NIF, HOTEL_IBAN);
		this.room = new Room(hotel, "01", Type.SINGLE,70);
	}

	@Test
	public void success() {
		Booking booking = this.room.reserve(Type.SINGLE, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);

		Assert.assertEquals(1, this.room.getNumberOfBookings());
		Assert.assertTrue(booking.getReference().length() > 0);
		Assert.assertEquals(this.arrival, booking.getArrival());
		Assert.assertEquals(this.departure, booking.getDeparture());
	}

	@Test(expected = HotelException.class)
	public void noDouble() {
		this.room.reserve(Type.DOUBLE, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
	}

	@Test(expected = HotelException.class)
	public void nullType() {
		this.room.reserve(null, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
	}

	@Test(expected = HotelException.class)
	public void nullArrival() {
		this.room.reserve(Type.SINGLE, null, this.departure, BUYER_NIF, BUYER_IBAN);
	}

	@Test(expected = HotelException.class)
	public void nullDeparture() {
		this.room.reserve(Type.SINGLE, this.arrival, null, BUYER_NIF, BUYER_IBAN);
	}
	
	@Test(expected = HotelException.class)
	public void nullNif() {
		this.room.reserve(Type.SINGLE, this.arrival, this.departure, null, BUYER_IBAN);
	}
	
	@Test(expected = HotelException.class)
	public void emptyNif() {
		this.room.reserve(Type.SINGLE, this.arrival, this.departure, "", BUYER_IBAN);
	}
	
	@Test(expected = HotelException.class)
	public void nullIban() {
		this.room.reserve(Type.SINGLE, this.arrival, this.departure, BUYER_NIF, null);
	}
	
	@Test(expected = HotelException.class)
	public void emptyIban() {
		this.room.reserve(Type.SINGLE, this.arrival, this.departure, BUYER_NIF, "");
	}

	@Test
	public void allConflict() {
		this.room.reserve(Type.SINGLE, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);

		try {
			this.room.reserve(Type.SINGLE, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
			fail();
		} catch (HotelException he) {
			Assert.assertEquals(1, this.room.getNumberOfBookings());
		}
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
}
