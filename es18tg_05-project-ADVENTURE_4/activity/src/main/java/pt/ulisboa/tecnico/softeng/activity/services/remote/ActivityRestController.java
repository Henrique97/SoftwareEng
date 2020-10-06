package pt.ulisboa.tecnico.softeng.activity.services.remote;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.activity.services.local.ActivityInterface;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityReservationData;

public class ActivityRestController {
	private static Logger logger = LoggerFactory.getLogger(ActivityRestController.class);

	@RequestMapping(value = "/reserveActivity", method = RequestMethod.POST)
	public ResponseEntity<String> reserveActivity(@RequestParam LocalDate begin, @RequestParam LocalDate end,
			@RequestParam int age, @RequestParam String nif, @RequestParam String iban) {
		logger.info("rentCar begin:{}, end:{}, age:{}, nif:{}, iban:{}", begin, end, age, nif, iban);
		try {
			LocalDate newBegin = new LocalDate(begin);
			LocalDate newEnd = new LocalDate(end);

			return new ResponseEntity<>(
					ActivityInterface.reserveActivity(newBegin, newEnd, age, nif, iban), HttpStatus.OK);
		} catch (ActivityException be) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/cancelReservation", method = RequestMethod.POST)
	public ResponseEntity<String> cancelReservation(@RequestParam String activityConfirmation) {
		logger.info("cancelReservation activityConfirmation:{}", activityConfirmation);
		try {
			return new ResponseEntity<>(ActivityInterface.cancelReservation(activityConfirmation), HttpStatus.OK);
		} catch (ActivityException be) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/reservations", method = RequestMethod.GET)
	public ResponseEntity<ActivityReservationData> getActivityReservationData(@RequestParam String reference) {
		logger.info("getActivityReservationData reservation:{}", reference);
		try {
			ActivityReservationData result = ActivityInterface.getActivityReservationData(reference);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (ActivityException be) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
