package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.domain.Client;
import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class ClientConstructorMethodTest {
	private static final int AGE = 20;
	private static final String IBAN = "BK011234567";
	private static final String NIF = "987654321";


	@Test
	public void success() {
		Client client = new Client(IBAN, AGE, NIF);
		Assert.assertEquals(NIF, client.getNIF());
		Assert.assertEquals(AGE, client.getAge());
		Assert.assertEquals(IBAN, client.getIBAN());
		
	}

	@Test(expected = BrokerException.class)
	public void nullIBAN() {
		new Client(null, AGE, NIF);
	}

	@Test(expected = BrokerException.class)
	public void nullNIF() {
		new Client(IBAN, AGE, null);
	}
	
	@Test(expected = BrokerException.class)
	public void nullAge() {
		new Client(IBAN, 0 , NIF);
	}

	@Test(expected = BrokerException.class)
	public void blankNIF() {
		new Client(IBAN, AGE ,"    ");
	}
	
	@Test(expected = BrokerException.class)
	public void blankIBAN() {
		new Client("   ", AGE ,NIF);
	}
}
