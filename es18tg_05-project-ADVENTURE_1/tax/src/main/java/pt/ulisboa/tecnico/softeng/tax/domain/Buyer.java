package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Buyer extends TaxPayer {
	
	public Buyer(String NIF, String name, String address) {
		super(NIF, name, address);
	}
	
	public float taxReturn(int year) throws TaxException {
		if (year < 1970) {
			throw new TaxException();
		}
		
		float result = 0f;
		
		for (Invoice invoice : this.getInvoices()) {
			if (invoice.getDate().getYear() == year) {
				result += invoice.getIVA() * .05;
			}
		}
		return result;
	}
	
}
