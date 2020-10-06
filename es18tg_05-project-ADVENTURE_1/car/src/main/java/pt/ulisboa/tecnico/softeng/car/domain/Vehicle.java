package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

import java.util.Map;
import java.util.TreeMap;

public class Vehicle {
	private String _plate;
	private int _kilometers;
	private RentACar _rentACar;
	private TreeMap<String, Renting> _rentings;
	
	public Vehicle(String plate, int kilometers,RentACar rentACar) {
		if(plate==null || plate== "" || rentACar == null || kilometers <0 || !isPlateUnique(plate) || !isPlateFormat(plate)) {
			throw new CarException();
		}
		_rentings = new TreeMap<String, Renting>();
		rentACar.vehicles.add(this);
		_plate = plate.toUpperCase();
		_kilometers = kilometers;
		_rentACar = rentACar;
	}
	
	public boolean isPlateUnique(String plate) {
		for(RentACar rt: RentACar.rentACars) {
			for(Vehicle v: rt.vehicles) {
				if(plate.equals(v.getPlate())) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isPlateFormat(String plate) {
		boolean r1 = plate.matches("\\w{2}-\\w{2}-\\w{2}");
		return r1 && (plate.indexOf('_') == -1);
	}

	public boolean isFree(LocalDate begin, LocalDate end) {
		for(Map.Entry<String, Renting> entry: _rentings.entrySet()) {
			if(entry.getValue().conflict(begin, end)) 
				return false;
		}
		return true;
	}
	public String getPlate() {
		return _plate;
	}
	
	public String rent(String drivingLicense, LocalDate begin, LocalDate end) throws CarException{
		Renting rent;
		if (this.isFree(begin, end) == false) throw new CarException("The car is not free in the specified dates. Please change the dates.");
		
		rent = new Renting(drivingLicense, begin, end, this);
		_rentings.put(rent.getReference(), rent);

		return rent.getReference();
	}
	public Renting getRent(String reference) {
		return (Renting) _rentings.get(reference);
	}
	public int getKilometers() {
		return _kilometers;
	}
	public RentACar getRentACar() {
		return _rentACar;
	}
	public void add(Renting renting) {
		_rentings.put(renting.getReference(),renting);
	}
	public void setKilometers(int kilometers) {
		if (kilometers < 0) throw new CarException("Numero de kilometers a tentar submeter é negativo");
		
		this._kilometers = kilometers;
	}
}
