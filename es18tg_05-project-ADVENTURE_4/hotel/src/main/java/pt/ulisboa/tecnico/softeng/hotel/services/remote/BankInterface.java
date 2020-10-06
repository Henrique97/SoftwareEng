package pt.ulisboa.tecnico.softeng.hotel.services.remote;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pt.ulisboa.tecnico.softeng.hotel.services.remote.exceptions.BankException;
import pt.ulisboa.tecnico.softeng.hotel.services.remote.exceptions.RemoteAccessException;

public class BankInterface {
	public static String processPayment(String IBAN, double amount) {
		// return Bank.processPayment(IBAN, amount);
		// TODO: implement in the final version as a rest invocation
		final String uri = "http://localhost:8082/banks/rest/accounts/";
		RestTemplate restTemplate = new RestTemplate();
		try {
			return restTemplate.postForObject(uri + IBAN + "/processPayment", amount, String.class);
		}  catch (HttpClientErrorException e) {
			throw new BankException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}

	}

	public static String cancelPayment(String reference) {
		// return Bank.cancelPayment(reference);
		// TODO: implement in the final version as a rest invocation
		final String uri = "http://localhost:8082/banks/rest/accounts";
		RestTemplate restTemplate = new RestTemplate();
		try {
			return restTemplate.postForObject(uri + "/cancelPayment", reference, String.class);
		} catch (HttpClientErrorException e) {
			throw new BankException();
		} catch (Exception e) {
			throw new RemoteAccessException();
		}

	}
}
