package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentingConstructorTest {
	private static final String RENT_A_CAR_NAME = "Eartz";
	private static final String RENT_A_CAR_NIF = "NIF";
	private static final String RENT_A_CAR_IBAN = "IBAN";
	private static final String PLATE_CAR = "22-33-HZ";
	private static final String DRIVING_LICENSE = "br112233";
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-07");
	private static final String RENTING_NIF = "987654321";
	private static final String RENTING_IBAN = "IBAN2";
	
	
	private Car car;
	private RentACar rentACar;

	@Before
	public void setUp() {
		this.rentACar = new RentACar(RENT_A_CAR_NAME, RENT_A_CAR_NIF, RENT_A_CAR_IBAN);
		this.car = new Car(PLATE_CAR, 10, rentACar,50);
	}

	@Test
	public void success() {
		Renting renting = new Renting(rentACar, DRIVING_LICENSE, date1, date2, car, RENTING_NIF, RENTING_IBAN);
		assertEquals(DRIVING_LICENSE, renting.getDrivingLicense());
		assertEquals(RENTING_NIF, renting.getNif());
		assertEquals(RENTING_IBAN, renting.getIban());
	}
	
	@Test(expected = CarException.class)
	public void nullRentACar() {
		new Renting(null, DRIVING_LICENSE, date1, date2, car, RENTING_NIF, RENTING_IBAN);
	}
	
	@Test(expected = CarException.class)
	public void nullDrivingLicense() {
		new Renting(rentACar, null, date1, date2, car, RENTING_NIF, RENTING_IBAN);
	}

	@Test(expected = CarException.class)
	public void emptyDrivingLicense() {
		new Renting(rentACar, "", date1, date2, car, RENTING_NIF, RENTING_IBAN);
	}

	@Test(expected = CarException.class)
	public void invalidDrivingLicense() {
		new Renting(rentACar, "12", date1, date2, car, RENTING_NIF, RENTING_IBAN);
	}

	@Test(expected = CarException.class)
	public void nullBegin() {
		new Renting(rentACar, DRIVING_LICENSE, null, date2, car, RENTING_NIF, RENTING_IBAN);
	}

	@Test(expected = CarException.class)
	public void nullEnd() {
		new Renting(rentACar, DRIVING_LICENSE, date1, null, car, RENTING_NIF, RENTING_IBAN);
	}
	
	@Test(expected = CarException.class)
	public void endBeforeBegin() {
		new Renting(rentACar, DRIVING_LICENSE, date2, date1, car, RENTING_NIF, RENTING_IBAN);
	}

	@Test(expected = CarException.class)
	public void nullCar() {
		new Renting(rentACar, DRIVING_LICENSE, date1, date2, null, RENTING_NIF, RENTING_IBAN);
	}
	
	@Test(expected = CarException.class)
	public void nullNIF() {
		new Renting(rentACar, DRIVING_LICENSE, date1, date2, car, null, RENTING_IBAN);
	}

	@Test(expected = CarException.class)
	public void emptyNIF() {
		new Renting(rentACar, DRIVING_LICENSE, date1, date2, car, "", RENTING_IBAN);
	}
	
	@Test(expected = CarException.class)
	public void nullIBAN() {
		new Renting(rentACar, DRIVING_LICENSE, date1, date2, car, RENTING_NIF, null);
	}

	@Test(expected = CarException.class)
	public void emptyIBAN() {
		new Renting(rentACar, DRIVING_LICENSE, date1, date2, car, RENTING_NIF, "");
	}
	
	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}

}
