package pt.ulisboa.tecnico.softeng.broker.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;
import pt.ulisboa.tecnico.softeng.broker.services.local.BrokerInterface;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.AdventureData;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.BrokerData;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.BrokerData.CopyDepth;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.ClientData;

@Controller
@RequestMapping(value = "/brokers/{brokerCode}/clients/{clientNIF}/adventures")
public class AdventureController {
	private static Logger logger = LoggerFactory.getLogger(AdventureController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String showAdventures(Model model, @PathVariable String brokerCode, @PathVariable String clientNIF) {
		logger.info("showAdventures code:{}, NIF:{}", brokerCode, clientNIF);

		ClientData clientData = BrokerInterface.getClientDataByNIF(brokerCode, clientNIF);

		if (clientData == null) {
			model.addAttribute("error",
					"Error: it does not exist a client with nif " + clientNIF + "of broker " + brokerCode);
			model.addAttribute("broker", new BrokerData());
			model.addAttribute("brokers", BrokerInterface.getBrokers());
			return "brokers";
		} else {
			model.addAttribute("adventure", new AdventureData());
			model.addAttribute("client", clientData);
			return "adventures";
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitAdventure(Model model, @PathVariable String brokerCode, @PathVariable String clientNIF,
	                              @ModelAttribute AdventureData adventureData) {
		logger.info("adventureSubmit brokerCode:{}, clientNIF:{}, begin:{}, end:{}, amount:{}, margin:{}",
				brokerCode, clientNIF, adventureData.getBegin(), adventureData.getEnd(),
				adventureData.getAmount(), adventureData.getMargin());

		try {
			BrokerInterface.createAdventure(brokerCode, adventureData, clientNIF);
		} catch (BrokerException be) {
			logger.info("Error creating adventure");
			model.addAttribute("error", "Error: it was not possible to create the adventure");
			model.addAttribute("adventure", adventureData);
			model.addAttribute("broker", BrokerInterface.getBrokerDataByCode(brokerCode, CopyDepth.ADVENTURES));
			return "adventures";
		}

		return "redirect:/brokers/" + brokerCode + "/clients/" + clientNIF + "/adventures";
	}

}
