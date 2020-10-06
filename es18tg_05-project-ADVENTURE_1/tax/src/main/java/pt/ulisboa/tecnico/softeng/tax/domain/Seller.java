package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Seller extends TaxPayer {
	
	public Seller(String NIF, String name, String address) {
		super(NIF, name, address);
	}
	
	public float toPay(int year) throws TaxException {
		if (year < 1970) {
			throw new TaxException();
		}
		
		float result = 0f;
		
		for (Invoice invoice : this.getInvoices()) {
			if (invoice.getDate().getYear() == year) {
				result += invoice.getIVA();
			}
		}
		return result;
	}
	
}
