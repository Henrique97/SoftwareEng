package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.After;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.joda.time.LocalDate;
import pt.ulisboa.tecnico.softeng.car.exception.*;

public class RentingCheckoutMethodTest {
	
	private final int kilometers = 100;
	private RentACar rt1 ;
	private Vehicle v1;
	private final LocalDate begin = new LocalDate(2017, 01, 13);
	private final LocalDate end = new LocalDate(2017, 02, 20);
	private String drivingLicense = "A12345678";
	

	@Before
	public void setUp() {
	this.rt1 = new RentACar("RentACar 1");
	this.v1 = new Vehicle("21-12-21",10 ,rt1);

	}

	@Test
	public void success() {
		Renting renting = new Renting(this.drivingLicense, this.begin, this.end,v1);
		renting.checkout(kilometers);
		Assert.assertEquals(this.kilometers, v1.getKilometers()-10);
	}

	@Test(expected = CarException.class)
	public void negativeKilometers() {
		Vehicle v3 = new Vehicle("23-12-21",10 ,rt1);
		Renting renting = new Renting(this.drivingLicense,this.begin,this.end,v3);
		renting.checkout(-kilometers);
	}
	
	@After
	public void tearDown() {
		rt1.vehicles.clear();
		RentACar.rentACars.clear();
	}

}

