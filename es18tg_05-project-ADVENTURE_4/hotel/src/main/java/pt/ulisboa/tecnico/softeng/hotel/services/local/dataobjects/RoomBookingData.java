package pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import pt.ulisboa.tecnico.softeng.hotel.domain.Booking;

public class RoomBookingData {
	private String reference;
	private String cancellation;
	private String hotelName;
	private String hotelCode;
	private String roomNumber;
	private String roomType;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate arrival;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate departure;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate cancellationDate;
	private double price;
	private String paymentReference;
	private String invoiceReference;
	private String buyerNif;
	private String buyerIban;

	public RoomBookingData() {
	}

	public RoomBookingData(Booking booking) {
		this.reference = booking.getReference();
		this.cancellation = booking.getCancellation();
		this.hotelName = booking.getRoom().getHotel().getName();
		this.hotelCode = booking.getRoom().getHotel().getCode();
		this.roomNumber = booking.getRoom().getNumber();
		this.roomType = booking.getRoom().getType().name();
		this.arrival = booking.getArrival();
		this.departure = booking.getDeparture();
		this.cancellationDate = booking.getCancellationDate();
		this.price = booking.getPrice();
		this.paymentReference = booking.getPaymentReference();
		this.invoiceReference = booking.getInvoiceReference();
		this.buyerNif = booking.getBuyerNif();
		this.buyerIban = booking.getBuyerIban();
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getCancellation() {
		return cancellation;
	}

	public void setCancellation(String cancellation) {
		this.cancellation = cancellation;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public LocalDate getArrival() {
		return arrival;
	}

	public void setArrival(LocalDate arrival) {
		this.arrival = arrival;
	}

	public LocalDate getDeparture() {
		return departure;
	}

	public void setDeparture(LocalDate departure) {
		this.departure = departure;
	}

	public LocalDate getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(LocalDate cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public String getBuyerNif() {
		return buyerNif;
	}

	public void setBuyerNif(String buyerNif) {
		this.buyerNif = buyerNif;
	}

	public String getBuyerIban() {
		return buyerIban;
	}

	public void setBuyerIban(String buyerIban) {
		this.buyerIban = buyerIban;
	}
}
