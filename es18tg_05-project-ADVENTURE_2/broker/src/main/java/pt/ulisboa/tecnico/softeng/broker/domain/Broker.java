package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class Broker {
	private static Logger logger = LoggerFactory.getLogger(Broker.class);

	public static Set<Broker> brokers = new HashSet<>();

	private final String code;
	private final String name;
	private final Set<Adventure> adventures = new HashSet<>();
	private final Set<BulkRoomBooking> bulkBookings = new HashSet<>();
	private String sellerNIF;
	private String buyerNIF;
	private String IBAN;
	public Broker(String code, String name, String sellerNIF, String buyerNIF, String IBAN) {
		checkCode(code);
		this.code = code;
		this.IBAN=IBAN;
		if ((!verifyUniqueSellerNIF(sellerNIF))||sellerNIF ==null) throw new BrokerException();
		this.sellerNIF = sellerNIF;
		
		if (!verifyUniqueBuyerNIF(buyerNIF)||buyerNIF ==null) throw new BrokerException();
		this.buyerNIF = buyerNIF;

		checkName(name);
		this.name = name;

		Broker.brokers.add(this);
	}

	private void checkCode(String code) {
		if (code == null || code.trim().length() == 0) {
			throw new BrokerException();
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getCode().equals(code)) {
				throw new BrokerException();
			}
		}
	}

	private void checkName(String name) {
		if (name == null || name.trim().length() == 0) {
			throw new BrokerException();
		}
	}

	String getCode() {
		return this.code;
	}

	String getName() {
		return this.name;
	}

	public int getNumberOfAdventures() {
		return this.adventures.size();
	}

	public void addAdventure(Adventure adventure) {
		this.adventures.add(adventure);
	}

	public boolean hasAdventure(Adventure adventure) {
		return this.adventures.contains(adventure);
	}

	public void bulkBooking(int number, LocalDate arrival, LocalDate departure) {
		BulkRoomBooking bulkBooking = new BulkRoomBooking(number, arrival, departure);
		this.bulkBookings.add(bulkBooking);
		bulkBooking.processBooking();
	}
	
	public boolean verifyUniqueSellerNIF(String nif) {
		for(Broker bkr: Broker.brokers) {
			if(bkr.getSellerNIF().equals(nif)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean verifyUniqueBuyerNIF(String nif) {
		for(Broker bkr: Broker.brokers) {
			if(bkr.getBuyerNIF().equals(nif)) {
				return false;
			}
		}
		return true;
	}
	
	public String getSellerNIF() {
		return this.sellerNIF;
	}
	
	public String getBuyerNIF() {
		return this.buyerNIF;
	}
	public String getIBAN() {
		return this.IBAN;
	}
	

}