package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class BuyerConstructorTest {
	private static final String BUYER_NIF = "245880631";
	private static final String BUYER_NAME = "Francisco";
	private static final String BUYER_ADDRESS = "Residencia Ramoa RIbeiro";
	
	@Test
	public void success() {
		Buyer buyer = new Buyer(BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);

		Assert.assertEquals(BUYER_NIF, buyer.getNIF());
		Assert.assertEquals(BUYER_NAME, buyer.getName());
		Assert.assertEquals(BUYER_ADDRESS, buyer.getAddress());
	}
	
	@Test(expected = TaxException.class)
	public void nullNIF() {
		new Buyer(null, BUYER_NAME, BUYER_ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void emptyNIF() {
		new Buyer("", BUYER_NAME, BUYER_ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void nullName() {
		new Buyer(BUYER_NIF, null, BUYER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void emptyName() {
		new Buyer(BUYER_NIF, "", BUYER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void nullAddress() {
		new Buyer(BUYER_NIF, BUYER_NAME, null);
	}

	@Test(expected = TaxException.class)
	public void emptyAddress() {
		new Buyer(BUYER_NIF, BUYER_NAME, "");
	}
	
	@Test(expected = TaxException.class)
	public void inconsistentCodeSmaller() {
		new Buyer("1234", BUYER_NAME, BUYER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void inconsistentCodeBigger() {
		new Buyer("12345678910", BUYER_NAME, BUYER_ADDRESS);
	}
	
	@Test
	public void notUniqueNIF() {
		new Buyer(BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);
		try {
			new Buyer(BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);
			Assert.fail();
		} catch (TaxException te) {
			Assert.assertEquals(1,IRS.taxPayers.size());
		}
	}
	
	@After
	public void tearDown() {
		IRS.taxPayers.clear();
	}
	

}
