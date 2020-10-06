package pt.ulisboa.tecnico.softeng.car.services.remote;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.CarInterface;
import pt.ulisboa.tecnico.softeng.car.services.local.CarInterface.Type;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentingData;

@RestController
@RequestMapping(value = "/rest/rentACars")
public class CarRestController {
	private static Logger logger = LoggerFactory.getLogger(CarRestController.class);

	@RequestMapping(value = "/rentCar", method = RequestMethod.POST)
	public ResponseEntity<String> rentCar(@RequestParam Type vehicleType, @RequestParam String drivingLicense,
			@RequestParam String nif, @RequestParam String iban, @RequestParam LocalDate begin,
			@RequestParam LocalDate end) {
		logger.info("rentCar vehicleType:{}, drivingLicense:{}, nif:{}, iban:{}, begin:{}, end:{}", vehicleType, drivingLicense, nif, iban, begin,end);
		try {
			LocalDate newBegin = new LocalDate(begin);
			LocalDate newEnd = new LocalDate(end);
			
			return new ResponseEntity<>(CarInterface.rentCar(vehicleType, drivingLicense, nif, iban, newBegin,newEnd), HttpStatus.OK);
		} catch (CarException be) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/cancelRenting", method = RequestMethod.POST)
	public ResponseEntity<String> cancelRenting(@RequestParam String rentingReference) {
		logger.info("cancelRenting rentingReference:{}", rentingReference);
		try {
			return new ResponseEntity<>(CarInterface.cancelRenting(rentingReference), HttpStatus.OK);
		} catch (CarException be) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/renting", method = RequestMethod.GET)
	public ResponseEntity<RentingData> getRentingData(@RequestParam String reference) {
		logger.info("getRentingData reference:{}", reference);
		try {
			RentingData result = CarInterface.getRentingData(reference);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (CarException be) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
