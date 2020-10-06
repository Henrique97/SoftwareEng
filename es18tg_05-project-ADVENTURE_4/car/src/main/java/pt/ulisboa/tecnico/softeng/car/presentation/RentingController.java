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
@RequestMapping(value = "/rentACars/{rentacar}/{plate}/rentings")
public class RentingController {
	private static Logger logger = LoggerFactory.getLogger(RentingController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String rentingForm(Model model, @PathVariable String plate,@PathVariable String rentacar) {
		logger.info("carForm");
		Set<RentingData> rentingDataSet = CarInterface.listRentingsByVehicle(plate);
		model.addAttribute("renting", new RentingData());
		model.addAttribute("rentings", rentingDataSet);
		return "renting";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String rentingSubmit(Model model, @ModelAttribute RentingData renting, @PathVariable String plate, @PathVariable String rentacar) {
		logger.info("plate: "+ plate + " IBAN " + renting.getIban() + " End " + renting.getEnd() + " NIF "+ renting.getNif() + " drivingLicense " +renting.getDrivingLicense());
		try {
			CarInterface.rentVehicle(plate,renting.getDrivingLicense(),renting.getBegin(),renting.getEnd(), renting.getNif(), renting.getIban());
		} catch (CarException be) {
			model.addAttribute("error", "Error: it was not possible to rent the vehicle");
			model.addAttribute("renting", new RentingData());
			model.addAttribute("rentings", CarInterface.listRentingsByVehicle(plate));
			return "renting";
		}

		return "redirect:/rentACars/"+ rentacar +"/"+ plate + "/rentings";
	}

}
