package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarConstructorTest extends RollbackTestAbstractClass {
	private String NAME;
	private String NIF;
	private String IBAN;
	
	@Override
	public void populate4Test() {
		this.NAME = "eartz";
		this.NIF = "NIF";
		this.IBAN = "IBAN";
	}

	@Test
	public void success() {
		RentACar rentACar = new RentACar(NAME, NIF, IBAN);
		assertEquals(NAME, rentACar.getName());
	}

	@Test(expected = CarException.class)
	public void nullName() {
		new RentACar(null, NIF, IBAN);
	}

	@Test(expected = CarException.class)
	public void emptyName() {
		new RentACar("", NIF, IBAN);
	}

}
