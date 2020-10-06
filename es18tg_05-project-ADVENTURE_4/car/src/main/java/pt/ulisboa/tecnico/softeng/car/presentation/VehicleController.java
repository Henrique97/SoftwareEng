package pt.ulisboa.tecnico.softeng.car.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.CarInterface;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.VehicleData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/rentACars/{rentacar}/vehicles")
public class VehicleController {
	private static Logger logger = LoggerFactory.getLogger(VehicleController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String vehicleForm(Model model, @PathVariable String rentacar) {
		logger.info("vehicleForm");
        List<VehicleData> vehicleDataSet = CarInterface.listVehicles(rentacar);
		model.addAttribute("vehicle", new VehicleData());
		model.addAttribute("vehicles", vehicleDataSet);
		return "vehicle";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String rentingSubmit(Model model, @ModelAttribute VehicleData vehicle, @PathVariable String rentacar) {
		logger.info("vehicleSubmit plate:{}, kilometers:{}, price:{}, rentacar:{}", vehicle.getPlate(), vehicle.getKilometers(), vehicle.getPrice(), rentacar);
		try {
			// check vehicle type
			if (vehicle.getType().equals("Car"))
				CarInterface.createCar(vehicle, rentacar);
			else
				CarInterface.createMotorcycle(vehicle, rentacar);

		} catch(CarException e) {
			model.addAttribute("error", "Error: it was not possible to create the Vehicle");
			model.addAttribute("vehicle", vehicle);
			model.addAttribute("vehicles", CarInterface.listVehicles(rentacar));

			return "vehicle";
		}
		return "redirect:/rentACars/" + rentacar + "/vehicles";
	}

}