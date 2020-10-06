package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.exception.*;

public class RentACar {
	
	public static int counter=0;
	public static Set<RentACar> rentACars = new HashSet<>();
	public Set<Vehicle> vehicles = new HashSet<>();
	public Set<Motorcycle> motorcycles = new HashSet<>();
	public Set<Car> cars = new HashSet<>();
	private String rentACarName;
	private String rentACarCode;
	
	public RentACar(String name) {
		if(name==null || name.equals("") ) {
			throw new CarException();
		}
		for (RentACar rentACar : rentACars)
			if(rentACar.getName().equals(name)) {
				throw new CarException();
			}
		this.rentACarName=name;
		this.rentACarCode = Integer.toString(++RentACar.counter); 
		rentACars.add(this);
		/*Should we add any random factor?*/
	}
	
	public Renting getRenting(String reference){
		if(reference==null ||reference=="")
			throw new CarException();
		Renting rent;
		for (Vehicle vehicle : this.vehicles)
			if((rent=vehicle.getRent(reference))!=null)
				return rent;
		return null;
	}
	
	public RentingData getRentingData(String reference){
		Renting renting = this.getRenting(reference);
		if(renting==null)
			throw new CarException();
		RentingData result = new RentingData(renting.getReference(),renting.getPlate(),renting.getDrivingLicense(), renting.getRentACarCode(), renting.getBegin(), renting.getEnd());
		return result;
	}
	
	public String getName() {
		return this.rentACarName;
	}

	public String getCode() {
		return this.rentACarCode;
	}
	
	public Set<Car> getAllAvaliableCars(LocalDate begin, LocalDate end) {
		Set<Car> avaliableCars = new HashSet<Car>();
		
		for (Car entry: cars)
			if (entry.isFree(begin, end))
				avaliableCars.add(entry);
		
		return avaliableCars;
	}
	
	public Set<Motorcycle> getAllAvaliableMotorcycles(LocalDate begin, LocalDate end) {
		Set<Motorcycle> motoAv = new HashSet<>();
		for(Motorcycle m: this.motorcycles) {
			if(m.isFree(begin, end)) {
				motoAv.add(m);
			}
		}
		return motoAv;
	}
}
