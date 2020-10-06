package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.After;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;


public class MotorcycleContructorMethodTest {
	private String plate;
	private int kilometers;
	private RentACar rentACar;

	@Before
	public void setUp() {
		this.plate = "AB-12-CD";
		this.kilometers = 100;
		this.rentACar = new RentACar("Rent-ACar 01");
	}

	@Test
	public void success() {
		Motorcycle moto1 = new Motorcycle(this.plate,this.kilometers,this.rentACar);

		Assert.assertEquals(this.plate, moto1.getPlate());
		Assert.assertEquals(this.kilometers, moto1.getKilometers());
		Assert.assertEquals(this.rentACar, moto1.getRentACar());
		Assert.assertEquals(1, this.rentACar.vehicles.size());
	}

	@Test(expected = CarException.class)
	public void notNegativeKilometersArgument() {
		new Motorcycle(this.plate, -this.kilometers, this.rentACar);
	}

	
	@Test(expected = CarException.class)
	public void notNullPlateArgument() {
		new Motorcycle(null, this.kilometers, this.rentACar);
	}
	
	@Test(expected = CarException.class)
	public void notUniquePlateArgument() {
		new Motorcycle(this.plate, this.kilometers, this.rentACar);
		new Motorcycle(this.plate, this.kilometers, this.rentACar);
	}
	
	@Test(expected = CarException.class)
	public void notFormatLongerPlateArgument() {
		new Motorcycle(this.plate + "-21", this.kilometers, this.rentACar);
	}

	@Test(expected = CarException.class)
	public void notFormatUnderScorePlateArgument() {
		new Motorcycle("12-1A-__", this.kilometers, this.rentACar);
	}

	@Test(expected = CarException.class)
	public void notFormatShorterPlateArgument() {
		new Motorcycle("21", this.kilometers, this.rentACar);
	}
	
	
	@Test(expected = CarException.class)
	public void notEmptyPlateArgument() {
		new Motorcycle("", this.kilometers, this.rentACar);
	}

	@Test(expected = CarException.class)
	public void notEmptyrentACarArgument() {
		new Motorcycle(this.plate,this.kilometers,null);
	}

	@Test(expected = CarException.class)
	public void notNullPlateandrentACarArgument() {
		new Motorcycle(null, this.kilometers, null);
	}
	
	@Test(expected = CarException.class)
	public void notEmptyPlateandnotNullrentACarArgument() {
		new Motorcycle("", this.kilometers, null);
	}

 
	@After
	public void tearDown() {
		rentACar.vehicles.clear();
		RentACar.rentACars.clear();
	}

}
