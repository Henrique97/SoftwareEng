package pt.ulisboa.tecnico.softeng.broker.services.remote;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects.ActivityReservationData;
import pt.ulisboa.tecnico.softeng.broker.services.remote.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.broker.services.remote.exception.RemoteAccessException;

public class ActivityInterface {

	private static Logger logger = LoggerFactory.getLogger(TaxInterface.class);
	private static final String ENDPOINT = "http://localhost:8081";

	public static String reserveActivity(LocalDate begin, LocalDate end, int age, String nif, String iban) {
		logger.info("rentCar begin:{}, end:{}, nif:{}, age:{}, nif:{}, iban:{}", begin, end, age, nif, iban);

		RestTemplate restTemplate = new RestTemplate();
		try {
			String result = restTemplate.postForObject(ENDPOINT + "/rest/providers/reserveActivity?begin=" + begin + "&end="
					+ end + "&age=" + age + "&nif=" + nif + "&iban=" + iban, null, String.class);

			logger.info("submitInvoice invoiceReference:{}", result);
			return result;
		} catch (HttpClientErrorException e) {
			throw new ActivityException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}

	public static String cancelReservation(String activityConfirmation) {
		logger.info("cancenlRenting reference:{}", activityConfirmation);

		RestTemplate restTemplate = new RestTemplate();
		try {
			String result = restTemplate.postForObject(
					ENDPOINT + "/rest/providers/cancelReservation?activityConfirmation=" + activityConfirmation, null,
					String.class);
			return result;
		} catch (HttpClientErrorException e) {
			throw new ActivityException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}

	public static ActivityReservationData getActivityReservationData(String reference) {
		logger.info("getRentingData reference:{}", reference);

		RestTemplate restTemplate = new RestTemplate();
		try {
			ActivityReservationData result = restTemplate.postForObject(
					ENDPOINT + "/rest/providers/reservations?reference=" + reference, null, ActivityReservationData.class);
			return result;
		} catch (HttpClientErrorException e) {
			throw new ActivityException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}
}
