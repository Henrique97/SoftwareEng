package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class Renting {
	private static String drivingLicenseFormat = "^[a-zA-Z]+\\d+$";
	private static int counter;
	private static final String type = "RENT";
	private final RentACar rentACar;
	private final String reference;
	private final String renterNif;
	private final String nif;
	private final String iban;
	private String paymentReference;
	private String invoiceReference;

	private final String drivingLicense;
	private final LocalDate begin;
	private final LocalDate end;
	private int kilometers = -1;
	private final Vehicle vehicle;
	private final double amount;

	public Renting(RentACar rentACar, String drivingLicense, LocalDate begin, LocalDate end, Vehicle vehicle, String nif, String iban) {
		checkArguments(rentACar, drivingLicense, begin, end, vehicle, nif, iban);
		this.rentACar = rentACar;
		this.reference = Integer.toString(++Renting.counter);
		this.drivingLicense = drivingLicense;
		this.begin = begin;
		this.end = end;
		this.vehicle = vehicle;
		this.amount = vehicle.getAmount();
		this.renterNif = rentACar.getNif();
		this.nif = nif;
		this.iban = iban;
	}

	private void checkArguments(RentACar rentACar, String drivingLicense, LocalDate begin, LocalDate end, Vehicle vehicle, String nif, String iban) {
		if (rentACar == null || drivingLicense == null || !drivingLicense.matches(drivingLicenseFormat) || begin == null || end == null
				|| vehicle == null || end.isBefore(begin) || nif == null || nif.trim().length() == 0 || iban == null || iban.trim().length() == 0)
			throw new CarException();
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @return the drivingLicense
	 */
	public String getDrivingLicense() {
		return drivingLicense;
	}
	
	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getInvoiceReference() {
		return invoiceReference;
	}

	public void setInvoiceReference(String invoiceReference) {
		this.invoiceReference = invoiceReference;
	}

	/**
	 * @return the begin
	 */
	public LocalDate getBegin() {
		return begin;
	}

	/**
	 * @return the end
	 */
	public LocalDate getEnd() {
		return end;
	}

	/**
	 * @return the vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	public  String getType() {
		return type;
	}
	
	/**
	 * @return the price
	 */
	public double getAmount() {
		return amount;
	}

	public String getRenterNif() {
		return this.renterNif;
	}

	public String getNif() {
		return this.nif;
	}

	public String getIban() {
		return this.iban;
	}
	/**
	 * @param begin
	 * @param end
	 * @return <code>true</code> if this Renting conflicts with the given date
	 *         range.
	 */
	public boolean conflict(LocalDate begin, LocalDate end) {
		if (end.isBefore(begin)) {
			throw new CarException("Error: end date is before begin date.");
		} else if ((begin.equals(this.getBegin()) || begin.isAfter(this.getBegin()))
				&& (begin.isBefore(this.getEnd()) || begin.equals(this.getEnd()))) {
			return true;
		} else if ((end.equals(this.getEnd()) || end.isBefore(this.getEnd()))
				&& (end.isAfter(this.getBegin()) || end.isEqual(this.getBegin()))) {
			return true;
		} else if ((begin.isBefore(this.getBegin()) && end.isAfter(this.getEnd()))) {
			return true;
		}

		return false;
	}

	/**
	 * Settle this renting and update the kilometers in the vehicle.
	 * 
	 * @param kilometers
	 */
	public void checkout(int kilometers) {
		this.kilometers = kilometers;
		this.vehicle.addKilometers(this.kilometers);
	}

}
