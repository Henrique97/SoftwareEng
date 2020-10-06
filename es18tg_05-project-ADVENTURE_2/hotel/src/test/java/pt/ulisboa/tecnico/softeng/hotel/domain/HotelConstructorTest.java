package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelConstructorTest {
	private static final String HOTEL_NAME = "Londres";
	private static final String HOTEL_CODE = "XPTO123";
	private static final String HOTEL_CODE2 = "XPTO333";
	private static final String HOTEL_NIF = "987654321";
	private static final String HOTEL_IBAN = "IBAN1";

	@Test
	public void success() {
		Hotel hotel = new Hotel(HOTEL_CODE, HOTEL_NAME, HOTEL_NIF, HOTEL_IBAN);

		Assert.assertEquals(HOTEL_NAME, hotel.getName());
		Assert.assertEquals(HOTEL_NIF, hotel.getNif());
		Assert.assertEquals(HOTEL_IBAN, hotel.getIban());
		Assert.assertTrue(hotel.getCode().length() == Hotel.CODE_SIZE);
		Assert.assertEquals(0, hotel.getNumberOfRooms());
		Assert.assertEquals(1, Hotel.hotels.size());
	}

	@Test(expected = HotelException.class)
	public void nullCode() {
		new Hotel(null, HOTEL_NAME, HOTEL_NIF, HOTEL_IBAN);
	}

	@Test(expected = HotelException.class)
	public void blankCode() {
		new Hotel("      ", HOTEL_NAME, HOTEL_NIF, HOTEL_IBAN);
	}

	@Test(expected = HotelException.class)
	public void emptyCode() {
		new Hotel("", HOTEL_NAME, HOTEL_NIF, HOTEL_IBAN);
	}

	@Test(expected = HotelException.class)
	public void nullName() {
		new Hotel(HOTEL_CODE, null, HOTEL_NIF, HOTEL_IBAN);
	}

	@Test(expected = HotelException.class)
	public void blankName() {
		new Hotel(HOTEL_CODE, "  ", HOTEL_NIF, HOTEL_IBAN);
	}

	@Test(expected = HotelException.class)
	public void emptyName() {
		new Hotel(HOTEL_CODE, "", HOTEL_NIF, HOTEL_IBAN);
	}

	@Test(expected = HotelException.class)
	public void codeSizeLess() {
		new Hotel("123456", HOTEL_NAME, HOTEL_NIF, HOTEL_IBAN);
	}

	@Test(expected = HotelException.class)
	public void codeSizeMore() {
		new Hotel("12345678", HOTEL_NAME, HOTEL_NIF, HOTEL_IBAN);
	}

	@Test(expected = HotelException.class)
	public void codeNotUnique() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, HOTEL_NIF, HOTEL_IBAN);
		new Hotel(HOTEL_CODE, HOTEL_NAME + " City", "123456789", "IBAN2");
	}
	
	@Test(expected = HotelException.class)
	public void nullNif() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, null, HOTEL_IBAN);
	}

	@Test(expected = HotelException.class)
	public void blankNif() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, "      ", HOTEL_IBAN);
	}

	@Test(expected = HotelException.class)
	public void emptyNif() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, "", HOTEL_IBAN);
	}
	
	@Test(expected = HotelException.class)
	public void NifNotUnique() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, HOTEL_NIF, HOTEL_IBAN);
		new Hotel(HOTEL_CODE2, HOTEL_NAME, HOTEL_NIF, HOTEL_IBAN);
	}
	
	@Test(expected = HotelException.class)
	public void nullIban() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, HOTEL_NIF, null);
	}

	@Test(expected = HotelException.class)
	public void blankIban() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, HOTEL_NIF, "      ");
	}

	@Test(expected = HotelException.class)
	public void emptyIban() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, HOTEL_NIF, "");
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
