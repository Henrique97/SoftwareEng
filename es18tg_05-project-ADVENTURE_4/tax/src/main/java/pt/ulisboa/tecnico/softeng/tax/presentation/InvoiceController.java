package pt.ulisboa.tecnico.softeng.tax.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.TaxPayerData;

@Controller
@RequestMapping(value = "/taxpayers/{nif}/invoices")
public class InvoiceController {
	private static Logger logger = LoggerFactory.getLogger(InvoiceController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String invoiceForm(Model model, @PathVariable String nif) {
		logger.info("invoiceForm");

		TaxPayerData taxpayerData = TaxInterface.getTaxPayerDataByNif(nif);
		if (taxpayerData == null) {
			model.addAttribute("error", "Error: it does not exist a taxpayer with the nif " + nif);
			return new TaxPayerController().taxpayerForm(model);
		}

		model.addAttribute("invoice", new InvoiceData());
		model.addAttribute("invoices", TaxInterface.getInvoices(taxpayerData));
		model.addAttribute("taxpayer", taxpayerData);

		// we want all buyers
		model.addAttribute("taxpayers", TaxInterface.getBuyers());
		model.addAttribute("itemtypes", TaxInterface.getItemTypes());

		return "invoices";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String invoiceSubmit(Model model, @PathVariable String nif, @ModelAttribute InvoiceData invoice) {
		logger.info("invoiceSubmit value:{}, date:{}, itemtype:{}, seller:{}, buyer:{}", invoice.getValue(),
				invoice.getDate(), invoice.getItemType(), invoice.getSellerNIF(), invoice.getBuyerNIF());
		try {
			TaxInterface.submitInvoice(invoice);
			return "redirect:/taxpayers/" + nif + "/invoices";

		} catch (TaxException be) {
			model.addAttribute("error", "Error: it was not possible to create the invoice");

			model.addAttribute("invoice", invoice);
			model.addAttribute("invoices", TaxInterface.getInvoices(TaxInterface.getTaxPayerDataByNif(nif)));
			model.addAttribute("taxpayer", TaxInterface.getTaxPayerDataByNif(nif));

			// we want all buyers
			model.addAttribute("taxpayers", TaxInterface.getBuyers());
			model.addAttribute("itemtypes", TaxInterface.getItemTypes());

			return "invoices";
		}
	}
}
