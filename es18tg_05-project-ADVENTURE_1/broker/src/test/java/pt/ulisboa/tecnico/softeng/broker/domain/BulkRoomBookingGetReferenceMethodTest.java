package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Assert;

import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

public class BulkRoomBookingGetReferenceMethodTest {
	private final int BULK_NUMBER = 1;
	private final LocalDate BULK_ARRIVAL = new LocalDate(2018, 2, 10);
	private final LocalDate BULK_DEPARTURE = new LocalDate(2018, 2, 14);
	private String reference;
	private BulkRoomBooking bulk;
	
	@Before
	public void setUp() {
		// create hotel
		Hotel hotel = new Hotel("XPTO123", "Évora");
		// add room
		new Room(hotel, "02", Type.SINGLE);

		// create and process bulk
		this.bulk = new BulkRoomBooking(BULK_NUMBER, BULK_ARRIVAL, BULK_DEPARTURE);
		this.bulk.processBooking();

		// store the only reference
		for (String ref : this.bulk.getReferences())
			this.reference = ref;
	}
	
	@Test
	public void success() {
		Assert.assertEquals(this.reference, this.bulk.getReference(Type.SINGLE.name()));
	}
	
	@Test
	public void inexistentReference() {
		this.bulk.getReference(Type.SINGLE.name());		
		Assert.assertEquals(null, this.bulk.getReference(Type.SINGLE.name()));
	}
	
	@Test
	public void cancelBooking() {
		int i = 0;
		
		// make sure the booking is canceled (and that we process a canceled booking)
		while (i < BulkRoomBooking.MAX_HOTEL_EXCEPTIONS + 1) {
			this.bulk.processBooking();
			i++;
		}
		
		Assert.assertEquals(null, this.bulk.getReference(Type.SINGLE.name()));
	}
	
	@Test
	public void incoherentRoomType() {
		Assert.assertEquals(null, this.bulk.getReference(Type.DOUBLE.name()));
	}
	
	@Test
	public void hotelException() {
		// force all hotels to be cleared
		Hotel.hotels.clear();
		Assert.assertEquals(null, this.bulk.getReference(Type.SINGLE.name()));
	}
	
	@After
	public void tearDown() {
		// make sure to empty all the hotels
		Hotel.hotels.clear();
	}
}
