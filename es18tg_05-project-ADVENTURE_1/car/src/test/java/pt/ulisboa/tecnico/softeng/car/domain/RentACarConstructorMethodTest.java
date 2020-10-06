package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.After;

import org.junit.Assert;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.exception.*;

public class RentACarConstructorMethodTest {

	@Test
	public void success() {
		RentACar rentACar = new RentACar("Rent your car");

		Assert.assertEquals("Rent your car", rentACar.getName());
		Assert.assertTrue(RentACar.rentACars.contains(rentACar));
	}

	@Test
	public void nullCode() {
		try {
			new RentACar(null);
			Assert.fail();
		} catch (CarException be) {
			Assert.assertEquals(0, RentACar.rentACars.size());
		}
	}

	@Test
	public void emptyCode() {
		try {
			new RentACar("");
			Assert.fail();
		} catch (CarException be) {
			Assert.assertEquals(0, RentACar.rentACars.size());
		}
	}

	@Test
	public void blankCode() {
		try {
			new RentACar("");
			Assert.fail();
		} catch (CarException be) {
			Assert.assertEquals(0, RentACar.rentACars.size());
		}
	}

	@Test
	public void uniqueCode() {
		RentACar rentACar = new RentACar("Rent your car");

		try {
			new RentACar("Rent your car");
			Assert.fail();
		} catch (CarException be) {
			Assert.assertEquals(1, RentACar.rentACars.size());
			Assert.assertTrue(RentACar.rentACars.contains(rentACar));
		}
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
	}

}
