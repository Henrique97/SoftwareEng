package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleRentMethodTest {
	
	private Vehicle _vehicle1;
	private RentACar _rentacar;
	private final LocalDate _begin = new LocalDate(2018, 01, 19);
	private final LocalDate _end = new LocalDate(2018, 01, 21);
	private final String _drivingLicense = "L334138012";
	private Renting _rent;
	
	@Before
	public void setUp() {
		this._rentacar = new RentACar("Europcar");
		this._vehicle1 = new Vehicle("10-PB-32", 65000, this._rentacar);
	}
	
	@Test
	public void success() {
		String reference = this._vehicle1.rent(this._drivingLicense, _begin, _end);
		
		Assert.assertNotNull(reference);
		
		_rent = _vehicle1.getRent(reference);
		
		Assert.assertNotNull(_rent);
		Assert.assertNotNull(_rent.getReference());
		Assert.assertEquals(this._drivingLicense, _rent.getDrivingLicense());
		Assert.assertEquals(this._begin, _rent.getBegin());
		Assert.assertEquals(this._end, _rent.getEnd());
	}
	
	@Test(expected = CarException.class)
	public void exceptionTest() {
		this._vehicle1.rent(this._drivingLicense, _begin, _end);
		this._vehicle1.rent(this._drivingLicense, _begin, _end);
		
	}
	
	@After
	public void tearDown() {
		RentACar.rentACars.clear();
	}
}
