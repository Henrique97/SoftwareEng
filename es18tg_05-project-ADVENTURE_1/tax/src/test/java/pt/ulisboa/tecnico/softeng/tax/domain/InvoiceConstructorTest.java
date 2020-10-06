package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class InvoiceConstructorTest {
	private static final float INVOICE_VALUE = 1.0f;
	private static LocalDate INVOICE_DATE = null;
	private static ItemType INVOICE_ITEMTYPE = null;
	private static Seller INVOICE_SELLER = null;
	private static Buyer INVOICE_BUYER = null;
	
	@Before
	public void setUp() {
		INVOICE_DATE = new LocalDate(1997, 1, 1);
		INVOICE_ITEMTYPE = new ItemType("test", 23);
		INVOICE_SELLER = new Seller("123456789","abc", "def");
		INVOICE_BUYER = new Buyer("987654321", "def", "abc");
	}
	
	
	@Test
	public void success() {
		Invoice invoice = new Invoice(INVOICE_VALUE, INVOICE_DATE, INVOICE_ITEMTYPE, INVOICE_SELLER, INVOICE_BUYER);
		
		Assert.assertEquals(INVOICE_VALUE, invoice.getValue(), 0.0001);
		Assert.assertEquals(INVOICE_DATE, invoice.getDate());
		Assert.assertEquals(INVOICE_SELLER, invoice.getSeller());
		Assert.assertEquals(INVOICE_BUYER, invoice.getBuyer());
		Assert.assertEquals(INVOICE_ITEMTYPE, invoice.getItemType());
		Assert.assertEquals((INVOICE_ITEMTYPE.getTax()/100f) * invoice.getValue(), invoice.getIVA(), 0.0001);
		
		Assert.assertNotEquals(null, invoice.getReference());
		Assert.assertNotEquals("", invoice.getReference());
		
	}
	
	@Test
	public void consistenteDateLimit() {
		new Invoice(INVOICE_VALUE, new LocalDate(2017,10,1), INVOICE_ITEMTYPE, INVOICE_SELLER, INVOICE_BUYER);
	}
	
	@Test
	public void consistenteDatePosterior() {
		new Invoice(INVOICE_VALUE, new LocalDate(1970,10,1), INVOICE_ITEMTYPE, INVOICE_SELLER, INVOICE_BUYER);
	}
		
	@Test(expected = TaxException.class)
	public void inconsistentDatePrevious() {
		new Invoice(INVOICE_VALUE, new LocalDate(1969,10,1), INVOICE_ITEMTYPE, INVOICE_SELLER, INVOICE_BUYER);
	}
	
	@After
	public void tearDown() {
		IRS.taxPayers.clear();
		IRS.itemtypes.clear();
	}
	
	
}
