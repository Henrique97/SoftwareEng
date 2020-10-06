package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.ArrayList;
import java.util.List;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;


public class ItemType {
	protected List<Invoice> invoices = new ArrayList<>();
	protected IRS irs;
	
	private int tax;
	private String itemType;
	
	public ItemType(String ITEM_TYPE, int TAX) {
		checkArguments(ITEM_TYPE,TAX);
		
		this.tax = TAX;
		this.itemType = ITEM_TYPE;
		
		this.irs = IRS.getIRS();
		this.irs.addItemType(this);
	}
	
	private void checkArguments(String ITEM_TYPE, int TAX) {
		if (ITEM_TYPE == null || ITEM_TYPE.trim().equals("")) {
			throw new TaxException();
		}

		if (TAX<0) {
			throw new TaxException();
		}

		for (ItemType itemType : IRS.itemtypes) {
			if (itemType.getItemType().equals(ITEM_TYPE)) {
				throw new TaxException();
			}
		}
	}
	
	public void addInvoice(Invoice invoice) {
		this.invoices.add(invoice);
	}
	
	public int getTax() {
		return this.tax;
	}
	
	public String getItemType() {
		return this.itemType;
	}	
}
