package pt.ulisboa.tecnico.softeng.car.presentation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.CarInterface;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentACarData;


@Controller
@RequestMapping(value = "/rentACars")
public class RentACarController {
	private static Logger logger = LoggerFactory.getLogger(RentACarController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String rentACarForm(Model model) {
		logger.info("rentACarForm");
        List<RentACarData> rentACarDataSet = CarInterface.listRentACars();
		model.addAttribute("rentACar", new RentACarData());
		model.addAttribute("rentACars", rentACarDataSet);
		return "rentACar";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String rentACarSubmit(Model model, @ModelAttribute RentACarData rentACarData) {
		logger.info("rentACarSubmit name:{}, nif:{}, iban:{}", rentACarData.getName(), rentACarData.getNif(), rentACarData.getIban());
		try {
			CarInterface.createRentACar(rentACarData);
		} catch (CarException be) {
			model.addAttribute("error", "Error: it was not possible to create a Rent-a-Car");
			model.addAttribute("rentACar", rentACarData);
			model.addAttribute("rentACars", CarInterface.listRentACars());
			return "rentACar";
		}

		return "redirect:/rentACars";
	}
}
