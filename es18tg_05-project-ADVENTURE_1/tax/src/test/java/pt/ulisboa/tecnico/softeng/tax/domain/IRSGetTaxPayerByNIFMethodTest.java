package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRSGetTaxPayerByNIFMethodTest {
	private static IRS Irs = IRS.getIRS();
	private static String shortNIF = "12345678";
	private static String longNIF = "1234567890";
	private static Buyer buyer = new Buyer("123456789","Jonh Doe","Noville");
	
	@Test
	public void success() {
		Irs.addTaxPayer(buyer);
		Assert.assertEquals(buyer, Irs.getTaxPayerByNIF(buyer.getNIF()));
	}
	
	@Test(expected = TaxException.class)
	public void NIFtooShort() {
		Irs.getTaxPayerByNIF(shortNIF);
	}
	
	@Test(expected = TaxException.class)
	public void NIFtooLong() {
		Irs.getTaxPayerByNIF(longNIF);
	}
	
	@Test(expected = TaxException.class)
	public void TaxPayerNotInIRS() {
		Irs.getTaxPayerByNIF(buyer.getNIF());
	}
	
	@Test(expected = TaxException.class)
	public void nullNIF() {
		Irs.getTaxPayerByNIF(null);
	}
	
	@Test(expected = TaxException.class)
	public void emptyNIF() {
		Irs.getTaxPayerByNIF("");
	}
	
	@After
	public void tearDown() {
		IRS.taxPayers.clear();
	}
	
	
}
