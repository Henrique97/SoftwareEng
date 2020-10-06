package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class Booking {
	private static int counter = 0;
	private final String type = "BOOK";
	private final Hotel hotel;
	private final Room room;
	private final String reference;
	private final String hotelNif;
	private final String nif;
	private final String iban;
	private String cancellation;
	private LocalDate cancellationDate;
	private final LocalDate arrival;
	private final LocalDate departure;
	private final double amount;
	
	private String paymentReference;
	private String invoiceReference;
	
	private boolean cancelledInvoice = false;
	private String cancelledPaymentReference = null;
	

	Booking(Hotel hotel, Room room, LocalDate arrival, LocalDate departure, String nif, String iban) {
		checkArguments(hotel, room, arrival, departure, nif, iban);

		this.reference = hotel.getCode() + Integer.toString(++Booking.counter);
		this.hotel = hotel;
		this.room = room;
		this.hotelNif = hotel.getNif();
		this.nif = nif;
		this.iban = iban;
		this.arrival = arrival;
		this.departure = departure;
		this.amount = room.getAmount();
	}

	private void checkArguments(Hotel hotel, Room room,LocalDate arrival, LocalDate departure, String buyerNif, String buyerIban) {
		if (hotel == null || room== null || arrival == null || departure == null || buyerNif == null || buyerNif.trim().length() == 0
				|| buyerIban == null || buyerIban.trim().length() == 0) {
			throw new HotelException();
		}

		if (departure.isBefore(arrival)) {
			throw new HotelException();
		}
	}

	public String getReference() {
		return this.reference;
	}
	
	public String getHotelNif() {
		return this.hotelNif;
	}
	

	public String getType() {
		return type;
	}

	public static int getCounter() {
		return counter;
	}

	public Room getRoom() {
		return room;
	}

	public String getNif() {
		return this.nif;
	}

	public String getIban() {
		return this.iban;
	}
	
	public String getCancellation() {
		return this.cancellation;
	}

	public LocalDate getArrival() {
		return this.arrival;
	}

	public LocalDate getDeparture() {
		return this.departure;
	}

	public LocalDate getCancellationDate() {
		return this.cancellationDate;
	}
	
	public double getAmount() {
		return this.amount;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public String getInvoiceReference() {
		return invoiceReference;
	}
	
	public boolean isCancelledInvoice() {
		return this.cancelledInvoice;
	}
	
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public void setInvoiceReference(String invoiceReference) {
		this.invoiceReference = invoiceReference;
	}

	public void setCancelledInvoice(boolean cancelledInvoice) {
		this.cancelledInvoice = cancelledInvoice;
	}

	public String getCancelledPaymentReference() {
		return this.cancelledPaymentReference;
	}

	public void setCancelledPaymentReference(String cancelledPaymentReference) {
		this.cancelledPaymentReference = cancelledPaymentReference;
	}

	boolean conflict(LocalDate arrival, LocalDate departure) {
		if (isCancelled()) {
			return false;
		}

		if (arrival.equals(departure)) {
			return true;
		}

		if (departure.isBefore(arrival)) {
			throw new HotelException();
		}

		if ((arrival.equals(this.arrival) || arrival.isAfter(this.arrival)) && arrival.isBefore(this.departure)) {
			return true;
		}

		if ((departure.equals(this.departure) || departure.isBefore(this.departure))
				&& departure.isAfter(this.arrival)) {
			return true;
		}

		if ((arrival.isBefore(this.arrival) && departure.isAfter(this.departure))) {
			return true;
		}

		return false;
	}

	public String cancel() {
		this.cancellation = "CANCEL" + this.reference;
		this.cancellationDate = new LocalDate();

		this.hotel.getProcessor().submitBooking(this);

		return this.cancellation;
	}

	public boolean isCancelled() {
		return this.cancellation != null;
	}

}
