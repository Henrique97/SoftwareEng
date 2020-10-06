package pt.ulisboa.tecnico.softeng.car.presentation;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.CarInterface;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentingData;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Controller
@RequestMapping(value = "/rentACars/{rentacar}/{plate}/rentings/{ref}/checkout")
public class CheckoutController {
	private static Logger logger = LoggerFactory.getLogger(CheckoutController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String checkoutForm(Model model, @PathVariable String plate,@PathVariable String rentacar,@PathVariable String ref) {
		logger.info("checkoutForm");
		model.addAttribute("checkout", new RentingData());
		return "checkout";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String checkoutSubmit(Model model, @ModelAttribute RentingData checkout, @PathVariable String plate, @PathVariable String rentacar,
	@PathVariable String ref) {

		logger.info("checkout");
		try {
			CarInterface.doCheckout(checkout.getKilometers(), plate, ref);
		} catch (CarException be) {
			model.addAttribute("error", "Error: it was not possible to checkout renting");
			model.addAttribute("checkout", new RentingData());
			return "checkout";
		}

		return "redirect:/rentACars/"+ rentacar +"/"+ plate + "/rentings";
	}


}
