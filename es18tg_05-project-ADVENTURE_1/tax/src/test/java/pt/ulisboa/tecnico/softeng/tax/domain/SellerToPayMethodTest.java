package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class SellerToPayMethodTest {
	private static final String SELLER_NIF = "123456789";
	private static final String SELLER_NAME = "Henrique";
	private static final String SELLER_ADDRESS = "Taguspark";
	private static final String BUYER_NIF = "111222333";
	private static final String BUYER_NAME = "Jonas";
	private static final String BUYER_ADDRESS = "Carnide";
	
	private static Seller seller = null;
	private static Buyer buyer = null;
	
	private static ItemType INVOICE_ITEMTYPE = null;
	
	@Before
	public void setUp() {
		seller = new Seller(SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);
		buyer = new Buyer(BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);
		INVOICE_ITEMTYPE = new ItemType("Taxa", 23);
		
		new Invoice(11.1f, new LocalDate(2017, 1, 11), INVOICE_ITEMTYPE, seller, buyer);
		new Invoice(22.2f, new LocalDate(2017, 2, 11), INVOICE_ITEMTYPE, seller, buyer);
		new Invoice(33.3f, new LocalDate(2017, 3, 11), INVOICE_ITEMTYPE, seller, buyer);
		new Invoice(44.4f, new LocalDate(2016, 4, 11), INVOICE_ITEMTYPE, seller, buyer);
	}
	
	@Test
	public void success() {
		Assert.assertEquals(15.3180f, seller.toPay(2017), 0.0001);
	}
	
	@Test
	public void successNoInvoices() {
		Assert.assertEquals(0f, seller.toPay(2018), 0.0001);
	}
	@Test(expected = TaxException.class)
	public void invalidYearBeforeLimit() {
		seller.toPay(1969);
	}
	
	@Test
	public void validYearLimit() {
		Assert.assertEquals(0f, seller.toPay(1970), 0.0001);
	}
	
	@Test
	public void validYearAfterLimit() {
		Assert.assertEquals(0f, seller.toPay(1971), 0.0001);
	}
	
	@After
	public void tearDown() {
		IRS.taxPayers.clear();
		IRS.itemtypes.clear();
	}

}
