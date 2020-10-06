package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.After;
import org.joda.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;


public class RentingIsDrivingLicenseMethodTest {
	private LocalDate begin;
	private LocalDate end;
	private Vehicle v1;
	private RentACar rentACar;
	private String drivingLicense = "ACP001";

	@Before
	public void setUp() {
		rentACar = new RentACar("MafraCar");
		this.v1 = new Vehicle("AB-12-12",100,rentACar);
	}

	@Test
	public void success() {
		Renting rg = new Renting(drivingLicense, begin, end, v1);
		Assert.assertEquals(this.drivingLicense, rg.getDrivingLicense());
		
	}

	
	@Test(expected = CarException.class)
	public void wrongOnlyLettersLicense() {
		new Renting("AAA",begin,end,v1);
	}
	
	@Test(expected = CarException.class)
	public void wrongOnlyNumbersLicense() {
		new Renting("000000",begin,end,v1);
	}
	
	@Test(expected = CarException.class)
	public void wrongOrderLicense() {
		new Renting("1322ABB",begin,end,v1);
	}

	@After
	public void tearDown() {
		rentACar.vehicles.clear();
		RentACar.rentACars.clear();
	}

}
