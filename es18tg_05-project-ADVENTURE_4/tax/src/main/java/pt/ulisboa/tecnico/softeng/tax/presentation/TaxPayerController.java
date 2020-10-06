package pt.ulisboa.tecnico.softeng.tax.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.TaxPayerData;

@Controller
@RequestMapping(value = "/taxpayers")
public class TaxPayerController {
	private static Logger logger = LoggerFactory.getLogger(TaxPayerController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String taxpayerForm(Model model) {
		logger.info("taxpayerForm");
		model.addAttribute("taxpayer", new TaxPayerData());
		model.addAttribute("taxpayers", TaxInterface.getTaxPayers());

		return "taxpayers";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String taxpayerSubmit(Model model, @ModelAttribute TaxPayerData taxpayer) {
		logger.info("taxpayerSubmit nif:{}, name:{}, address:{}, type:{}", taxpayer.getNif(), taxpayer.getName(),
				taxpayer.getAddress(), taxpayer.getType());

		try {
			// check taxpayer type
			if (taxpayer.getType().equals("Seller"))
				TaxInterface.createSeller(taxpayer);
			else
				TaxInterface.createBuyer(taxpayer);

		} catch (TaxException te) {
			model.addAttribute("error", "Error: it was not possible to create the taxpayer");
			model.addAttribute("taxpayer", taxpayer);
			model.addAttribute("taxpayers", TaxInterface.getTaxPayers());

			return "taxpayers";
		}

		return "redirect:/taxpayers";
	}

}
