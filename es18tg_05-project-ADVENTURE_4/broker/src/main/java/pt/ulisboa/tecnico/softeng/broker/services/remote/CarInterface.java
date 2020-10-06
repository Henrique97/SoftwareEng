package pt.ulisboa.tecnico.softeng.broker.services.remote;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.broker.services.remote.exception.CarException;
import pt.ulisboa.tecnico.softeng.broker.services.remote.exception.RemoteAccessException;

public class CarInterface {
	private static Logger logger = LoggerFactory.getLogger(TaxInterface.class);
	private static final String ENDPOINT = "http://localhost:8084";

	public static enum Type {
		CAR, MOTORCYCLE
	}

	public static String rentCar(Type vehicleType, String drivingLicense, String nif, String iban, LocalDate begin,
			LocalDate end) {
		logger.info("rentCar vehicleType:{}, drivingLicense:{}, nif:{}, iban:{}, begin:{}, end:{}", vehicleType,
				drivingLicense, nif, iban, begin, end);

		RestTemplate restTemplate = new RestTemplate();
		try {
			String result = restTemplate.postForObject(
					ENDPOINT + "/rest/rentACars/rentCar?vehicleType=" + vehicleType + "&drivingLicense="
							+ drivingLicense + "&nif=" + nif + "&iban=" + iban + "&begin=" + begin + "&end=" + end,
					null, String.class);

			logger.info("rentCar result:{}", result);
			return result;
		} catch (HttpClientErrorException e) {
			throw new CarException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}

	public static String cancelRenting(String rentingReference) {
		logger.info("cancenlRenting reference:{}", rentingReference);

		RestTemplate restTemplate = new RestTemplate();
		try {
			String result = restTemplate.postForObject(
					ENDPOINT + "/rest/rentACars/cancelRenting?reference=" + rentingReference, null, String.class);
			return result;
		} catch (HttpClientErrorException e) {
			throw new CarException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}

	public static RentingData getRentingData(String reference) {
		logger.info("getRentingData reference:{}", reference);

		RestTemplate restTemplate = new RestTemplate();
		try {
			RentingData result = restTemplate.postForObject(ENDPOINT + "/rest/rentACars/renting?reference=" + reference,
					null, RentingData.class);
			return result;
		} catch (HttpClientErrorException e) {
			throw new CarException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}
	}

}
