package pt.ulisboa.tecnico.softeng.broker.domain;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class Client {

	private final int age;
	private final String IBAN;
	private final String NIF;
	
	public Client(String IBAN, int age, String nif) {
		checkArguments(IBAN, age, nif);
		this.IBAN = IBAN;
		this.age = age;
		this.NIF = nif;
	}

	private void checkArguments(String IBAN, int age, String nif) {
		if (IBAN == null || nif == null || age == 0 || IBAN.trim().equals("") || nif.trim().equals("")) {
			throw new BrokerException();
		} 
	}

	public String getIBAN() {
		return this.IBAN;
	}

	public int getAge() {
		return this.age;
	}
	
	public String getNIF() {
		return this.NIF;
	}

}
