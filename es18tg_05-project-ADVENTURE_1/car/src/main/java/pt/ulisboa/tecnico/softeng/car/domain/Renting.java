package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class Renting {
	private static String _nextReference = "1";
	private String _reference;
	private String _drivingLicense;
	private LocalDate _begin;
	private LocalDate _end;
	private Vehicle _vehicle;
	private int _kilometers = 0;
	
	public Renting(String drivingLicense, LocalDate begin, LocalDate end, Vehicle vehicle ) {
		if (!isDrivingLicense(drivingLicense)) {
			throw new CarException();
		}
		_reference = Renting.getNextReference();
		_drivingLicense = drivingLicense.toUpperCase();
		_begin = begin;
		_end = end;
		_vehicle = vehicle;
		vehicle.add(this);
	}
	
	public String getReference() {
		return _reference;
	}
	public String getPlate() {
		return _vehicle.getPlate();
	}
	
	public String getRentACarCode() {
		return _vehicle.getRentACar().getCode();
	}
	
	public String getDrivingLicense() {
		return _drivingLicense;
	}
	public LocalDate getBegin() {
		return _begin;
	}
	public LocalDate getEnd() {
		return _end;
	}
	public boolean isDrivingLicense(String dl) {
		return dl.matches("[a-zA-Z]+\\d+");
	}
	public static String getNextReference() {
		int ref = Integer.parseInt(_nextReference);
		_nextReference = Integer.toString(ref+1);
		return Integer.toString(ref);
	}
	public boolean conflict(LocalDate begin, LocalDate end) {
		if ((this.getBegin().compareTo(end) < 0 && this.getEnd().compareTo(begin) > 0 )|| 
			(this.getEnd().compareTo(begin) >= 0 && this.getBegin().compareTo(end) <= 0 )) {		
			return true;
		}
			
		return false;
	}
	public void checkout(int kilometers) {
		if (kilometers <0 ) {
			throw new CarException();
		}
		this._kilometers = this._kilometers + kilometers;
		int presentKilometers = this._vehicle.getKilometers();
		this._vehicle.setKilometers(kilometers+ presentKilometers);
		
	}
	
}
