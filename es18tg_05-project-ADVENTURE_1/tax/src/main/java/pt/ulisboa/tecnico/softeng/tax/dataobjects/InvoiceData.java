package pt.ulisboa.tecnico.softeng.tax.dataobjects;

import org.joda.time.LocalDate;


public class InvoiceData {
	
	private float value;
	private LocalDate date;
	private String sellerNIF;
	private String buyerNIF;
	private String itemType;
	
	public InvoiceData() {
	}
	
	public InvoiceData(String sellerNIF, String buyerNIF, String itemType, float value, LocalDate date) {
		this.value = value;
		this.date = date;
		this.sellerNIF = sellerNIF;
		this.buyerNIF = buyerNIF;
		this.itemType = itemType;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getSellerNIF() {
		return sellerNIF;
	}

	public void setSellerNIF(String sellerNIF) {
		this.sellerNIF = sellerNIF;
	}

	public String getBuyerNIF() {
		return buyerNIF;
	}

	public void setBuyerNIF(String buyerNIF) {
		this.buyerNIF = buyerNIF;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	
	
	
}
