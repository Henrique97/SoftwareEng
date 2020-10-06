package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class CarConstructorMethodTest {
	private String _plate;
	private int _kilometers;
	private RentACar _rentACar;

	@Before
	public void setUp() {
		this._plate = "01-JT-42";
		this._kilometers = 100;
		this._rentACar = new RentACar("EuropTest");
	}
	
	@Test
	public void success() {
		Car car1 = new Car(this._plate,this._kilometers,this._rentACar);

		Assert.assertEquals(this._plate, car1.getPlate());
		Assert.assertEquals(this._kilometers, car1.getKilometers());
		Assert.assertEquals(this._rentACar, car1.getRentACar());
		Assert.assertEquals(1, this._rentACar.vehicles.size());
	}
	
	@Test(expected = CarException.class)
	public void notNegativeKilometersArgument() {
		new Car(this._plate, -this._kilometers, this._rentACar);
	}

	
	@Test(expected = CarException.class)
	public void notNullPlateArgument() {
		new Car(null, this._kilometers, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void notUniquePlateArgument() {
		new Car(this._plate, this._kilometers, this._rentACar);
		new Car(this._plate, this._kilometers, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void notFormatLongerPlateArgument() {
		new Car(this._plate + "-21", this._kilometers, this._rentACar);
	}
	
	@Test(expected = CarException.class)
	public void notFormatUnderScorePlateArgument() {
		new Car("12-1A-__", this._kilometers, this._rentACar);
	}

	@Test(expected = CarException.class)
	public void notFormatShorterPlateArgument() {
		new Car("21", this._kilometers, this._rentACar);
	}
	
	
	@Test(expected = CarException.class)
	public void notEmptyPlateArgument() {
		new Car("", this._kilometers, this._rentACar);
	}

	@Test(expected = CarException.class)
	public void notEmptyrentACarArgument() {
		new Car(this._plate,this._kilometers,null);
	}

	@Test(expected = CarException.class)
	public void notNullPlateandrentACarArgument() {
		new Car(null, this._kilometers, null);
	}
	
	@Test(expected = CarException.class)
	public void notEmptyPlateandnotNullrentACarArgument() {
		new Car("", this._kilometers, null);
	}

 
	@After
	public void tearDown() {
		_rentACar.vehicles.clear();
		RentACar.rentACars.clear();
	}
	
}
