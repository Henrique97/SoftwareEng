package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class SellerConstructorMethodTest {
	private static final String SELLER_NIF = "817392847";
	private static final String SELLER_NAME = "António";
	private static final String SELLER_ADDRESS = "Rua da Pinta";
	
	@Test
	public void success() {
		Seller seller = new Seller(SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);
		
		Assert.assertEquals(SELLER_NIF, seller.getNIF());
		Assert.assertEquals(SELLER_NAME, seller.getName());
		Assert.assertEquals(SELLER_ADDRESS, seller.getAddress());
	}
	
	@Test(expected = TaxException.class)
	public void nullNIF() {
		new Seller(null, SELLER_NAME, SELLER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void nullName() {
		new Seller(SELLER_NIF, null, SELLER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void nullAddress() {
		new Seller(SELLER_NIF, SELLER_NAME, null);
	}
	
	@Test(expected = TaxException.class)
	public void emptyNIF() {
		new Seller(" ", SELLER_NAME, SELLER_ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void emptyName() {
		new Seller(SELLER_NIF, " ", SELLER_ADDRESS);
	}
	
	@Test(expected = TaxException.class)
	public void emptyAddress() {
		new Seller(SELLER_NIF, SELLER_NAME, " ");
	}

	@Test(expected = TaxException.class)
	public void inconsistentNIFSmaller() {
		new Seller("34", SELLER_NAME, SELLER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void inconsistentNIFBigger() {
		new Seller("8475837465", SELLER_NAME, SELLER_ADDRESS);
	}

	@Test
	public void notUniqueNIF() {
		new Seller(SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);
		try {
			new Seller(SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);
			Assert.fail();
		} catch (TaxException te) {
			Assert.assertEquals(1, IRS.taxPayers.size());
		}
	}

	@After
	public void tearDown() {
		IRS.taxPayers.clear();
	}
	
}
