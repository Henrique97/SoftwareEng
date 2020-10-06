package pt.ulisboa.tecnico.softeng.hotel.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class Room {
	public static enum Type {
		SINGLE, DOUBLE
	}

	private final Hotel hotel;
	private final String number;
	private final Type type;
	private final double amount;
	private final Set<Booking> bookings = new HashSet<>();

	public Room(Hotel hotel, String number, Type type, double amount) {
		checkArguments(hotel, number, type, amount);

		this.hotel = hotel;
		this.number = number;
		this.type = type;
		this.amount = amount;

		this.hotel.addRoom(this);
	}

	private void checkArguments(Hotel hotel, String number, Type type, double amount) {
		if (hotel == null || number == null || number.trim().length() == 0 || type == null) {
			throw new HotelException();
		}

		if (!number.matches("\\d*")) {
			throw new HotelException();
		}
		
		if(amount < 0) {
			throw new HotelException();
		}
			
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public String getNumber() {
		return this.number;
	}

	public Type getType() {
		return this.type;
	}
	
	public double getAmount() {
		return this.amount;
	}

	int getNumberOfBookings() {
		return this.bookings.size();
	}

	boolean isFree(Type type, LocalDate arrival, LocalDate departure) {
		if (!type.equals(this.type)) {
			return false;
		}

		for (Booking booking : this.bookings) {
			if (booking.conflict(arrival, departure)) {
				return false;
			}
		}

		return true;
	}

	public Booking reserve(Type type, LocalDate arrival, LocalDate departure, String buyerNif, String buyerIban) {
		if (type == null || arrival == null || departure == null || buyerNif == null
				|| buyerNif.trim().length() == 0 || buyerIban == null || buyerIban.trim().length() == 0) {
			throw new HotelException();
		}

		if (!isFree(type, arrival, departure)) {
			throw new HotelException();
		}

		Booking booking = new Booking(this.hotel, this, arrival, departure, buyerNif, buyerIban);
		this.bookings.add(booking);

		return booking;
	}

	public Booking getBooking(String reference) {
		for (Booking booking : this.bookings) {
			if (booking.getReference().equals(reference)
					|| (booking.isCancelled() && booking.getCancellation().equals(reference))) {
				return booking;
			}
		}
		return null;
	}

}
