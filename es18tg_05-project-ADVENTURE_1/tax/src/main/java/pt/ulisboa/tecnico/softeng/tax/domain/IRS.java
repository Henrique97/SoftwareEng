package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRS {
	public static Set<ItemType> itemtypes = new HashSet<>();
	public static Set<TaxPayer> taxPayers = new HashSet<>();
	
	private static IRS irs;
	
	private IRS() {
	}
	
	public static IRS getIRS() {
		if (irs == null)
			irs = new IRS();
		return irs;
	}
	
	public void addItemType(ItemType itemType) {
		itemtypes.add(itemType);
	}
	
	public void addTaxPayer(TaxPayer payer) {
		taxPayers.add(payer);
	}
	
	public int getNumberOfItems() {
		return itemtypes.size();
	}
	
	public ItemType getItemTypeByName(String name) {
		if (name == null || name.trim().equals(""))
			throw new TaxException();

		for (ItemType itemType : IRS.itemtypes)
			if (itemType.getItemType().equals(name))
				return itemType;
		
		throw new TaxException();
	}
	
	public TaxPayer getTaxPayerByNIF(String NIF) {
		if(NIF==null ||NIF.length()>9||NIF.length()<9)
			throw new TaxException();
		for (TaxPayer taxpayer : taxPayers) {
			if (taxpayer.getNIF().equals(NIF)) {
				return taxpayer;
			}
		}
		throw new TaxException();
	}
	
	public String submitInvoice(InvoiceData data) {
		if (data == null) {
            throw new TaxException();
        }
		
		Invoice invoice = new Invoice(data.getValue(), data.getDate(), 
				this.getItemTypeByName(data.getItemType()),
				(Seller) this.getTaxPayerByNIF(data.getSellerNIF()),
				(Buyer) this.getTaxPayerByNIF(data.getBuyerNIF()));
		
		return invoice.getReference();
	}
	
}
