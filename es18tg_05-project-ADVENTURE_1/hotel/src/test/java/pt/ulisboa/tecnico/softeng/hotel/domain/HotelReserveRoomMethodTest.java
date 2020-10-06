package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;


public class HotelReserveRoomMethodTest {
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 24);
	private Hotel hotel;
	
	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Lisboa");
		new Room(this.hotel, "02", Type.SINGLE);
	}

	@Test
	public void success() {
		String reference = Hotel.reserveRoom(Type.SINGLE, this.arrival, this.departure);
		Assert.assertNotNull(reference);
	}

	@Test(expected = HotelException.class)
	public void noRoom() {
		Hotel.reserveRoom(Type.DOUBLE, this.arrival, this.departure);
	}

	@Test(expected = HotelException.class)
	public void nullRoom() {
		Hotel.reserveRoom(null, this.arrival, this.departure);
	}

	@Test(expected = HotelException.class)
	public void nullArrival() {
		Hotel.reserveRoom(Type.SINGLE, null, this.departure);
	}

	@Test(expected = HotelException.class)
	public void nullDeparture() {
		Hotel.reserveRoom(Type.SINGLE, this.arrival, null);
	}
	
	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
}
