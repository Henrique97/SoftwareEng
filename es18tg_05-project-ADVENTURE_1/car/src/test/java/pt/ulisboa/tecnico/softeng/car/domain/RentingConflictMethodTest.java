package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RentingConflictMethodTest {
	private RentACar _rentACar;
	private Vehicle _vehicle;
	private Renting _rent;
	
	@Before
	public void setUp() {
		_rentACar = new RentACar("Europcar");
		_vehicle = new Vehicle("10-PB-30", 50, _rentACar);
		_rent = new Renting( "L100000000", new LocalDate(2018, 01, 19), new LocalDate(2018, 01, 21), _vehicle);
	}
	
	@Test
	public void success() {
		Assert.assertEquals(false, _rent.conflict(new LocalDate(2018, 01, 17), new LocalDate(2018, 01, 18)));
		Assert.assertEquals(true, _rent.conflict(new LocalDate(2018, 01, 18), new LocalDate(2018, 01, 19)));
		Assert.assertEquals(true, _rent.conflict(new LocalDate(2018, 01, 19), new LocalDate(2018, 01, 20)));
		Assert.assertEquals(true, _rent.conflict(new LocalDate(2018, 01, 20), new LocalDate(2018, 01, 21)));
		Assert.assertEquals(true, _rent.conflict(new LocalDate(2018, 01, 21), new LocalDate(2018, 01, 22)));
		Assert.assertEquals(false, _rent.conflict(new LocalDate(2018, 01, 22), new LocalDate(2018, 01, 23)));
	}
	
	@After
	public void tearDown() {
		RentACar.rentACars.clear();
	}
}
