package pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;

public class ItemTypeData {
	private IRS irs;
	private String name;
	private int tax;

	public ItemTypeData() {
	}

	public ItemTypeData(ItemType itemType) {
		this.irs = itemType.getIrs();
		this.name = itemType.getName();
		this.tax = itemType.getTax();
	}

	public IRS getIrs() {
		return irs;
	}

	public void setIrs(IRS irs) {
		this.irs = irs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

}
