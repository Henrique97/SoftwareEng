package pt.ulisboa.tecnico.softeng.tax.services.local;

import java.util.List;
import java.util.stream.Collectors;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;
import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;
import pt.ulisboa.tecnico.softeng.tax.domain.Seller;
import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.ItemTypeData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.TaxPayerData;

public class TaxInterface {

	/** ItemType Manager */

	@Atomic(mode = TxMode.WRITE)
	public static void createItemType(ItemTypeData itemTypeData) {
		itemTypeData.setIrs(IRS.getIRSInstance());
		new ItemType(itemTypeData.getIrs(), itemTypeData.getName(), itemTypeData.getTax());
	}

	@Atomic(mode = TxMode.WRITE)
	public static List<ItemTypeData> getItemTypes() {
		return IRS.getIRSInstance().getItemTypeSet().stream().sorted((b1, b2) -> b1.getName().compareTo(b2.getName()))
				.map(b -> new ItemTypeData(b)).collect(Collectors.toList());
	}

	/** TaxPayer Manager */

	@Atomic(mode = TxMode.WRITE)
	public static void createBuyer(TaxPayerData taxPayerData) {
		taxPayerData.setIrs(IRS.getIRSInstance());
		new Buyer(taxPayerData.getIrs(), taxPayerData.getNif(), taxPayerData.getName(), taxPayerData.getAddress());
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createSeller(TaxPayerData taxPayerData) {
		taxPayerData.setIrs(IRS.getIRSInstance());
		new Seller(taxPayerData.getIrs(), taxPayerData.getNif(), taxPayerData.getName(), taxPayerData.getAddress());
	}

	@Atomic(mode = TxMode.WRITE)
	public static List<TaxPayerData> getTaxPayers() {
		return IRS.getIRSInstance().getTaxPayerSet().stream().sorted((b1, b2) -> b1.getName().compareTo(b2.getName()))
				.map(b -> new TaxPayerData(b)).collect(Collectors.toList());
	}

	@Atomic(mode = TxMode.READ)
	public static TaxPayerData getTaxPayerDataByNif(String nif) {
		TaxPayer taxpayer = IRS.getIRSInstance().getTaxPayerByNIF(nif);
		if (taxpayer == null) {
			return null;
		}

		return new TaxPayerData(taxpayer);
	}

	@Atomic(mode = TxMode.READ)
	public static List<TaxPayerData> getBuyers() {
		return getTaxPayers().stream().filter(b -> b.getType().equals("Buyer")).collect(Collectors.toList());
	}

	/** Invoice Manager */
	
	@Atomic(mode = TxMode.WRITE)
	public static void cancelInvoice(String invoiceReference) {
		IRS.cancelInvoice(invoiceReference);
	}

	@Atomic(mode = TxMode.WRITE)
	public static String submitInvoice(InvoiceData invoiceData) {
		return IRS.submitInvoice(invoiceData);
	}

	@Atomic(mode = TxMode.WRITE)
	public static List<InvoiceData> getInvoices(TaxPayerData taxPayerData) {
		return IRS.getIRSInstance().getInvoiceSet().stream()
				.filter(b -> b.getBuyer().getNif().equals(taxPayerData.getNif())
						|| b.getSeller().getNif().equals(taxPayerData.getNif()))
				.sorted((b1, b2) -> b1.getDate().compareTo(b2.getDate()))
				.map(b -> new InvoiceData(b, b.isCancelled()))
				.collect(Collectors.toList());
	}
}
