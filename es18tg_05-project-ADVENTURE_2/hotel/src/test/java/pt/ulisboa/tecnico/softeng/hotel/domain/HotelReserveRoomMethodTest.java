package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;
import mockit.Delegate;
import mockit.Expectations;
import mockit.FullVerifications;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;


@RunWith(JMockit.class)
public class HotelReserveRoomMethodTest {
    private final LocalDate arrival = new LocalDate(2016, 12, 19);
    private final LocalDate departure = new LocalDate(2016, 12, 24);

	private static final String HOTEL_NIF = "987654321";
	private static final String HOTEL_IBAN = "IBAN1";
	private static final String BUYER_NIF = "123456789";
	private static final String BUYER_IBAN = "IBAN2";

    private Room room;
    private Hotel hotel;

    @Before
    public void setUp() {
        hotel = new Hotel("XPTO123", "Lisboa", HOTEL_NIF, HOTEL_IBAN);
        this.room = new Room(hotel, "01", Room.Type.SINGLE,70);
    }

    @Test
    public void success() {
        String ref = Hotel.reserveRoom(Room.Type.SINGLE, arrival, departure, BUYER_NIF, BUYER_IBAN);
        assertTrue(ref.startsWith("XPTO12"));
    }

    @Test(expected = HotelException.class)
    public void noHotels() {
        Hotel.hotels.clear();
        Hotel.reserveRoom(Room.Type.SINGLE, arrival, departure, BUYER_NIF, BUYER_IBAN);
    }

    @Test(expected = HotelException.class)
    public void noVacancy() {
        hotel.removeRooms();
        String ref = Hotel.reserveRoom(Room.Type.SINGLE, arrival, new LocalDate(2016, 12, 25), BUYER_NIF, BUYER_IBAN);
        System.out.println(ref);
    }

    @Test(expected = HotelException.class)
    public void noRooms() {
        hotel.removeRooms();
        Hotel.reserveRoom(Room.Type.SINGLE, arrival, new LocalDate(2016, 12, 25), BUYER_NIF, BUYER_IBAN);
    }
    
    @Test
	public void reserveRoom(@Mocked final TaxInterface taxInterface, @Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(this.anyString, this.anyDouble);

				TaxInterface.submitInvoice((InvoiceData) this.any);
			}
		};
		
		String reserv = Hotel.reserveRoom(room.getType(), arrival, departure, BUYER_NIF, BUYER_IBAN);

		Assert.assertTrue(reserv != null);
		Assert.assertTrue(reserv.startsWith("XPTO123"));
	}

    @After
    public void tearDown() {
        Hotel.hotels.clear();
    }

}