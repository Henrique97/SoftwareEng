package pt.ulisboa.tecnico.softeng.tax.domain;


import org.junit.Assert;
import org.junit.Test;

public class IRSConstructorMethodTest {

	@Test
	public void success() {
		IRS irs = IRS.getIRS();
		Assert.assertNotNull(irs);
		Assert.assertEquals(irs, IRS.getIRS());
		Assert.assertEquals(0, IRS.itemtypes.size());
		Assert.assertEquals(0, IRS.taxPayers.size());
	}
}