package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRSGetItemTypeByNameMethodTest {
	private static IRS Irs = IRS.getIRS();
	private static ItemType ITEM_TYPE = null;
	
	@Before
	public void setUp() {
		ITEM_TYPE = new ItemType("NAME", 10);
	}
	
	@Test
	public void success() {
		Assert.assertEquals(ITEM_TYPE, Irs.getItemTypeByName(ITEM_TYPE.getItemType()));
	}
	
	@Test(expected = TaxException.class)
	public void incoherentItemName() {
		Irs.getItemTypeByName("WRONG_NAME");
	}
	
	@Test(expected = TaxException.class)
	public void itemTypeNotInIRS() {
		IRS.itemtypes.clear();
		Irs.getItemTypeByName(ITEM_TYPE.getItemType());
	}
	
	@Test(expected = TaxException.class)
	public void nullItemType() {
		Irs.getItemTypeByName(null);
	}
	
	@Test(expected = TaxException.class)
	public void emptyItemType() {
		Irs.getItemTypeByName("");
	}
	
	@After
	public void tearDown() {
		IRS.itemtypes.clear();
	}
	
	
}
