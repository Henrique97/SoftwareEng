package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class ItemTypeConstructorTest {
	private static final String ITEM_TYPE = "Alimentos";
	private static final String ITEM_TYPE2 = "Vinho";
	private static final int TAX = 10;
	
	@Test
	public void success() {
		ItemType itemType = new ItemType(ITEM_TYPE, TAX);
		ItemType itemType2 = new ItemType(ITEM_TYPE2, TAX);

		Assert.assertEquals(TAX, itemType.getTax());
		Assert.assertEquals(ITEM_TYPE,itemType.getItemType());
		Assert.assertEquals(TAX, itemType2.getTax());
		Assert.assertEquals(ITEM_TYPE2,itemType2.getItemType());
	}
	
	@Test(expected = TaxException.class)
	public void nullItemType() {
		new ItemType(null, TAX);
	}
	
	@Test(expected = TaxException.class)
	public void emptyItemType() {
		new ItemType("", TAX);
	}
	
	@Test(expected = TaxException.class)
	public void negativeTax() {
		new ItemType(ITEM_TYPE,-TAX);
	}
	
	@Test
	public void notUniqueItemType() {
		new ItemType(ITEM_TYPE,TAX);
		try {
			new ItemType(ITEM_TYPE,TAX);
			Assert.fail();
		} catch (TaxException te) {
			Assert.assertEquals(1, IRS.itemtypes.size());
		}
	}
	
	@After
	public void tearDown() {
		IRS.itemtypes.clear();
	}
	

}
