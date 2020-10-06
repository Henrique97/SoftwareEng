package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleConstructorTest {
	private static final String PLATE_CAR = "22-33-HZ";
	private static final String PLATE_MOTORCYCLE = "44-33-HZ";
	private static final String RENT_A_CAR_NAME = "Eartz";
	private static final String RENT_A_CAR_NIF = "NIF";
	private static final String RENT_A_CAR_IBAN = "IBAN";
	
	private RentACar rentACar;

	@Before
	public void setUp() {
		this.rentACar = new RentACar(RENT_A_CAR_NAME, RENT_A_CAR_NIF, RENT_A_CAR_IBAN);
	}

	@Test
	public void success() {
		Vehicle car = new Car(PLATE_CAR, 10, this.rentACar,50);
		Vehicle motorcycle = new Motorcycle(PLATE_MOTORCYCLE, 10, this.rentACar,50);

		assertEquals(PLATE_CAR, car.getPlate());
		assertTrue(this.rentACar.hasVehicle(PLATE_CAR));
		assertEquals(PLATE_MOTORCYCLE, motorcycle.getPlate());
		assertTrue(this.rentACar.hasVehicle(PLATE_MOTORCYCLE));
	}

	@Test(expected = CarException.class)
	public void emptyLicensePlate() {
		new Car("", 10, this.rentACar,50);
	}

	@Test(expected = CarException.class)
	public void nullLicensePlate() {
		new Car(null, 10, this.rentACar,50);
	}

	@Test(expected = CarException.class)
	public void invalidLicensePlate() {
		new Car("AA-XX-a", 10, this.rentACar,50);
	}

	@Test(expected = CarException.class)
	public void invalidLicensePlate2() {
		new Car("AA-XX-aaa", 10, this.rentACar,50);
	}

	@Test(expected = CarException.class)
	public void duplicatedPlate() {
		new Car(PLATE_CAR, 0, this.rentACar,50);
		new Car(PLATE_CAR, 0, this.rentACar,50);
	}

	@Test(expected = CarException.class)
	public void duplicatedPlateDifferentRentACar() {
		new Car(PLATE_CAR, 0, rentACar,50);
		RentACar rentACar2 = new RentACar(RENT_A_CAR_NAME + "2", RENT_A_CAR_NIF, RENT_A_CAR_IBAN);
		new Car(PLATE_CAR, 2, rentACar2,50);
	}

	@Test(expected = CarException.class)
	public void negativeKilometers() {
		new Car(PLATE_CAR, -1, this.rentACar,50);
	}

	@Test(expected = CarException.class)
	public void noRentACar() {
		new Car(PLATE_CAR, 0, null,50);
	}
	
	@Test(expected = CarException.class)
	public void negAmount() {
		new Car(PLATE_CAR, 0, null,-50);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}

}
