package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IRSCancelInvoiceTest {
	private static final String SELLER_NIF = "123456789";
	private static final String BUYER_NIF = "987654321";
	private static final String FOOD = "FOOD";
	private static final int VALUE = 16;
	private final LocalDate date = new LocalDate(2018, 04, 03);
	
	private Invoice invoice;
	private Seller seller;
	private Buyer buyer;
	private ItemType itemtype;
	
	/**
	 * No exception is thrown.
	 */

	@Before
	public void setUp() {
		this.seller = new Seller(IRS.getIRS(), SELLER_NIF, "José Vendido", "Somewhere");
		this.buyer = new Buyer(IRS.getIRS(), BUYER_NIF, "Manuel Comprado", "Anywhere");
		this.itemtype = new ItemType(IRS.getIRS(), FOOD, VALUE);
		
		this.invoice = new Invoice(VALUE, date, itemtype, seller, buyer);
	}
	
	@Test
	public void nullReference() {
		IRS.cancelInvoice(null);
		Assert.assertEquals(false, this.invoice.isCancelled());
	}
	
	@Test
	public void emptyReference() {
		IRS.cancelInvoice("");
		Assert.assertEquals(false, invoice.isCancelled());
	}
	
	@Test
	public void blankReference() {
		IRS.cancelInvoice("  ");
		Assert.assertEquals(false, invoice.isCancelled());
	}
	
	@Test
	public void cancelledInvoice() {
		IRS.cancelInvoice(invoice.getReference());
		Assert.assertEquals(true, invoice.isCancelled());
	}
	
	@Test
	public void aNewCancelledInvoice() {
		// a new invoice is going to be added
		Invoice newInvoice = new Invoice(VALUE, date, itemtype, seller, buyer);
		
		// lets cancel the old invoice, but not the new one
		IRS.cancelInvoice(invoice.getReference());
		
		Assert.assertEquals(true, invoice.isCancelled());
		Assert.assertEquals(false, newInvoice.isCancelled());
	}
	
	@After
	public void tearDown() {
		IRS.getIRS().clearAll();
	}
	
}
