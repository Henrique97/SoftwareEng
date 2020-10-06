package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;


public class IRSSubmitInvoiceTest {
	private static final String SELLER_NIF = "333444555";
	private static final String SELLER_NAME = "Tomas";
	private static final String SELLER_ADDRESS = "Taguspark";
	private static final String BUYER_NIF = "666777888";
	private static final String BUYER_NAME = "JT";
	private static final String BUYER_ADDRESS = "Reguengos";
	
	private static Seller seller = null;
	private static Buyer buyer = null;
	private static String reference = null;
	private static IRS irs = null;
	private static InvoiceData invoiceData = null;
	private static ItemType ITEMTYPE = null;
	
	@Before
	public void setUp() {
		irs = IRS.getIRS();
		seller = new Seller(SELLER_NIF, SELLER_NAME, SELLER_ADDRESS);
		buyer = new Buyer(BUYER_NIF, BUYER_NAME, BUYER_ADDRESS);
		ITEMTYPE = new ItemType("Cerveja", 23);
		invoiceData = new InvoiceData(seller.getNIF(), buyer.getNIF(),
				ITEMTYPE.getItemType(), 22.2f, new LocalDate(2017, 3, 11));
	}
	
	@Test
	public void success() {
		reference = irs.submitInvoice(invoiceData);
		
		Assert.assertEquals(22.2f, buyer.getInvoiceByReference(reference).getValue(), 0.0001);
		Assert.assertEquals(new LocalDate(2017, 3, 11), buyer.getInvoiceByReference(reference).getDate());
		Assert.assertEquals(ITEMTYPE, buyer.getInvoiceByReference(reference).getItemType());
		Assert.assertEquals(seller, seller.getInvoiceByReference(reference).getSeller());
		Assert.assertEquals(buyer, buyer.getInvoiceByReference(reference).getBuyer());
		Assert.assertEquals(5.106, seller.getInvoiceByReference(reference).getIVA(), 0.0001);
		Assert.assertEquals(1, seller.getInvoices().size());
		Assert.assertEquals(1, buyer.getInvoices().size());
		Assert.assertEquals(1, irs.getNumberOfItems());
		
		Assert.assertNotNull(reference);
		Assert.assertNotEquals("", reference);
	}
	
	@Test(expected = TaxException.class)
    public void nullItemType() {
        reference = irs.submitInvoice(null);
    }
	
	@After
	public void tearDown() {
		IRS.taxPayers.clear();
		IRS.itemtypes.clear();
	}
	
}
