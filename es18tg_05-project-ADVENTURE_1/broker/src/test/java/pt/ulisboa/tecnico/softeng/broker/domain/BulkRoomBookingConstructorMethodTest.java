package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

public class BulkRoomBookingConstructorMethodTest {
	private static final int BULK_NUMBER = 10;
	private static final LocalDate BULK_ARRIVAL = new LocalDate(2018, 02, 11);
	private static final LocalDate BULK_DEPARTURE = new LocalDate(2018, 03, 11);
	
	
	@Test
	public void success() {
		BulkRoomBooking brb = new BulkRoomBooking(BULK_NUMBER, BULK_ARRIVAL, BULK_DEPARTURE);
		
		Assert.assertEquals(BULK_NUMBER, brb.getNumber());
		Assert.assertEquals(BULK_ARRIVAL, brb.getArrival());
		Assert.assertEquals(BULK_DEPARTURE, brb.getDeparture());
		Assert.assertEquals(0, brb.getReferences().size());
	}
}
