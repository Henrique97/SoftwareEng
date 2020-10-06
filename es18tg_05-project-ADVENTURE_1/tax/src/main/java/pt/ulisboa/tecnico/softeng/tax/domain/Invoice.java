package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Invoice {
	private static int referenceCounter = 0;
	
	private final String reference;
	private float value;
	private float IVA;
	private LocalDate date;
	private Seller seller;
	private Buyer buyer;
	private ItemType itemType;
	
	public Invoice (float value, LocalDate date,  ItemType itemtype, Seller seller, Buyer buyer ) {
		checkArguments(value, date,itemtype, seller, buyer);
		
		this.reference = Integer.toString(referenceCounter);
		Invoice.referenceCounter++;
		
		this.value = value;
		this.date = date;
		this.seller = seller;
		this.buyer = buyer;
		this.itemType = itemtype;
		
		this.IVA = (itemtype.getTax()/100f) * this.value;
		
		itemtype.addInvoice(this);
		seller.addInvoice(this);
		buyer.addInvoice(this);
		
	}
	
	private void checkArguments(float value, LocalDate date, ItemType itemtype, Seller seller, Buyer buyer) {
		if (date.getYear() < 1970) {
			throw new TaxException();
		}
	}

	public String getReference() {
		return reference;
	}

	public float getValue() {
		return value;
	}

	public float getIVA() {
		return IVA;
	}

	public LocalDate getDate() {
		return date;
	}

	public Seller getSeller() {
		return seller;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public ItemType getItemType() {
		return itemType;
	}
	
	
}
