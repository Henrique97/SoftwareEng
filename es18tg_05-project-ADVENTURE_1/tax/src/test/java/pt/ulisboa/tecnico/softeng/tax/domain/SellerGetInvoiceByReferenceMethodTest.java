package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class SellerGetInvoiceByReferenceMethodTest {
	private static Seller SELLER;
	private static Buyer BUYER;
	private static Invoice INVOICE;
	private static ItemType ITEM_TYPE = new ItemType("Produtos", 1);
	
	@Before
	public void setUp() {
		SELLER = new Seller("123456789", "Francisco", "Rua do Francisco");
		BUYER = new Buyer("987654321", "Carlos", "Rua do Carlos");
		INVOICE = new Invoice(0, new LocalDate(), ITEM_TYPE, SELLER, BUYER);
	}
	
	@Test
	public void success() {
		Assert.assertEquals(INVOICE, SELLER.getInvoiceByReference(INVOICE.getReference()));
	}
	
	@Test(expected = TaxException.class)
	public void incoherentInvoiceReference() {
		SELLER.getInvoiceByReference("WRONG_REFERENCE");
	}
	
	@Test(expected = TaxException.class)
	public void nullReference() {
		SELLER.getInvoiceByReference(null);
	}
	
	@Test(expected = TaxException.class)
	public void emptyReference() {
		SELLER.getInvoiceByReference("");
	}
	
	@After
	public void tearDown() {
		IRS.taxPayers.clear();
		IRS.itemtypes.clear();
	}
	
}
