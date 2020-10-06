package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelBulkBookingMethodTest {
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 21);
	private static final String HOTEL_NIF = "987654321";
	private static final String HOTEL_NIF2 = "123456789";
	private static final String HOTEL_IBAN = "IBAN1";
	private static final String BUYER_NIF = "666666666";
	private static final String BUYER_IBAN = "IBAN2";
	
	private Hotel hotel;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Paris", HOTEL_NIF, HOTEL_IBAN);
		new Room(this.hotel, "01", Type.DOUBLE, 200);
		new Room(this.hotel, "02", Type.SINGLE, 120);
		new Room(this.hotel, "03", Type.DOUBLE, 200);
		new Room(this.hotel, "04", Type.SINGLE, 120);

		this.hotel = new Hotel("XPTO124", "Paris", HOTEL_NIF2, HOTEL_IBAN);
		new Room(this.hotel, "01", Type.DOUBLE, 300);
		new Room(this.hotel, "02", Type.SINGLE, 150);
		new Room(this.hotel, "03", Type.DOUBLE, 300);
		new Room(this.hotel, "04", Type.SINGLE, 150);
	}

	@Test
	public void success() {
		Set<String> references = Hotel.bulkBooking(2, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);

		assertEquals(2, references.size());
	}

	@Test(expected = HotelException.class)
	public void zeroNumber() {
		Hotel.bulkBooking(0, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
	}

	@Test(expected = HotelException.class)
	public void noRooms() {
		Hotel.hotels.clear();
		this.hotel = new Hotel("XPTO124", "Paris", HOTEL_NIF, HOTEL_IBAN);

		Hotel.bulkBooking(3, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
	}

	@Test
	public void OneNumber() {
		Set<String> references = Hotel.bulkBooking(1, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);

		assertEquals(1, references.size());
	}

	@Test(expected = HotelException.class)
	public void nullArrival() {
		Hotel.bulkBooking(2, null, this.departure, BUYER_NIF, BUYER_IBAN);
	}

	@Test(expected = HotelException.class)
	public void nullDeparture() {
		Hotel.bulkBooking(2, this.arrival, null, BUYER_NIF, BUYER_IBAN);
	}

	@Test
	public void reserveAll() {
		Set<String> references = Hotel.bulkBooking(8, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);

		assertEquals(8, references.size());
	}

	@Test
	public void reserveAllPlusOne() {
		try {
			Hotel.bulkBooking(9, this.arrival, this.departure, BUYER_NIF, BUYER_IBAN);
			fail();
		} catch (HotelException he) {
			assertEquals(8, Hotel.getAvailableRooms(8, this.arrival, this.departure).size());
		}
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
