package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ist.fenixframework.FenixFramework;
import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Invoice extends Invoice_Base {

	Invoice(double value, LocalDate date, ItemType itemType, Seller seller, Buyer buyer) {
		checkArguments(value, date, itemType, seller, buyer);
		
		setReference(Integer.toString(getCounter()));
		setValue(value);
		setDate(date);
		setItemType(itemType);
		addTaxPayer(seller);
		addTaxPayer(buyer);
		setIva(value * itemType.getTax() / 100);
		setCancelled(false);
		seller.addInvoice(this);
		buyer.addInvoice(this);
	}

	private void checkArguments(double value, LocalDate date, ItemType itemType, Seller seller, Buyer buyer) {
		if (value <= 0.0f) {
			throw new TaxException();
		}

		if (date == null || date.getYear() < 1970) {
			throw new TaxException();
		}

		if (itemType == null) {
			throw new TaxException();
		}

		if (seller == null) {
			throw new TaxException();
		}

		if (buyer == null) {
			throw new TaxException();
		}
	}

	public void cancel() {
		setCancelled(true);
	}

	public boolean isCancelled() {
		return getCancelled();
	}
	
	@Override
	public int getCounter() {
		int counter = super.getCounter() + 1;
		setCounter(counter);
		return counter;
	}
	
	public Seller getSeller() {
		return (Seller)getTaxPayerSet().toArray()[0];
	}
	
	public Buyer getBuyer() {
		return (Buyer)getTaxPayerSet().toArray()[1];
	}

	public void delete() {
		getTaxPayerSet().clear();
		getItemType().delete();
		deleteDomainObject();
	}
	
}
