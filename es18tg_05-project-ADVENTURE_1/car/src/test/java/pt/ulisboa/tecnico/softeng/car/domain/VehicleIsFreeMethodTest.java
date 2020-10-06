package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class VehicleIsFreeMethodTest {
	private Vehicle _vehicle1;
	private RentACar _rentacar;
	private final LocalDate _begin = new LocalDate(2018, 01, 19);
	private final LocalDate _end = new LocalDate(2018, 01, 21);
	
	@Before
	public void setUp() {
		this._rentacar = new RentACar("Europcar");
		
	}
	
	@Test
	public void success() {
		this._vehicle1 = new Vehicle("10-PB-31", 65000, this._rentacar);
		this._vehicle1.rent("L334138012", _begin, _end);
		
		Assert.assertEquals(false, _vehicle1.isFree(_begin, _end));
		
		
		Assert.assertEquals(true, _vehicle1.isFree(new LocalDate(2018, 01, 17), new LocalDate(2018, 01, 18)));
		Assert.assertEquals(false, _vehicle1.isFree(new LocalDate(2018, 01, 18), new LocalDate(2018, 01, 19)));
		Assert.assertEquals(false, _vehicle1.isFree(new LocalDate(2018, 01, 19), new LocalDate(2018, 01, 20)));
		Assert.assertEquals(false, _vehicle1.isFree(new LocalDate(2018, 01, 20), new LocalDate(2018, 01, 21)));
		Assert.assertEquals(false, _vehicle1.isFree(new LocalDate(2018, 01, 21), new LocalDate(2018, 01, 22)));
		Assert.assertEquals(true, _vehicle1.isFree(new LocalDate(2018, 01, 22), new LocalDate(2018, 01, 23)));

	}
	@Test
	public void nullEntry() {
		this._vehicle1 = new Vehicle("10-PB-92", 65000, this._rentacar);
		Assert.assertEquals(true, _vehicle1.isFree(_begin, _end));
	}
	
	
	@After
	public void tearDown() {
		RentACar.rentACars.clear();
	}
}
	
