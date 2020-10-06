package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public abstract class TaxPayer {
	protected List<Invoice> invoices = new ArrayList<>();
	
	/* The set of TaxPayers was removed, because it was redundant.
	 * A similar set already exists in class IRS (a singleton class). 
	 */
	
	protected IRS irs;
	
	public static final int NIF_SIZE = 9;
	
	private final String NIF;
	private final String name;
	private final String address;
	
	public TaxPayer(String NIF, String name, String address) throws TaxException {
		checkArguments(NIF, name, address);
		
		this.NIF = NIF;
		this.name = name;
		this.address = address;
		
		IRS.taxPayers.add(this);
		this.irs = IRS.getIRS();
	}

	private void checkArguments(String NIF, String name, String address) throws TaxException {
		if (NIF == null || NIF.trim().equals(""))
			throw new TaxException();
		
		if (name == null || name.trim().equals(""))
			throw new TaxException();
		
		if (address == null || address.trim().equals(""))
			throw new TaxException();
		
		if (NIF.length() != TaxPayer.NIF_SIZE)
			throw new TaxException();

		for (TaxPayer taxpayer : IRS.taxPayers) {
			if (taxpayer.getNIF().equals(NIF)) {
				throw new TaxException();
			}
		}
	}

	public String getNIF() {
		return NIF;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}
	
	public void addInvoice(Invoice invoice) {
		this.invoices.add(invoice);
	}
	
	public List<Invoice> getInvoices() {
		return this.invoices;
	}
	
	public Invoice getInvoiceByReference(String reference) throws TaxException {
		if (reference == null || reference.trim().equals(""))
			throw new TaxException();

		for (Invoice invoice : this.invoices)
			if (invoice.getReference().equals(reference))
				return invoice;
		
		throw new TaxException();
	}
	
}
