package pt.ulisboa.tecnico.softeng.car.services.local.dataobjects;

import java.util.List;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

public class VehicleData {
	private String plate;
	private int kilometers;
	private double price;
	private RentACar rentACar;
	private String rentACarName;
	private List<RentingData> renting;
    private String type;
    
	public VehicleData() {
	}

	public VehicleData(Vehicle vehicle) {
		this.plate = vehicle.getPlate();
		this.kilometers = vehicle.getKilometers();
		this.price = vehicle.getPrice();
		this.rentACar = vehicle.getRentACar();
		this.rentACarName = vehicle.getRentACar().getName();
		this.renting = vehicle.getRentingSet().stream()
				.sorted((c1, c2) -> c1.getReference().compareTo(c2.getReference())).map(c -> new RentingData(c))
				.collect(Collectors.toList());
		
		this.type = vehicle.getClass().getName();
		this.type = this.type.substring(this.type.lastIndexOf(".") + 1);
		}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public int getKilometers() {
		return kilometers;
	}

	public void setKilometers(int kilometers) {
		this.kilometers = kilometers;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public RentACar getRentACar() {
		return rentACar;
	}

	public void setRentACar(RentACar rentACar) {
		this.rentACar = rentACar;
	}

	public List<RentingData> getRenting() {
		return renting;
	}

	public void setRenting(List<RentingData> renting) {
		this.renting = renting;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRentACarName() {
		return rentACarName;
	}

	public void setRentACarName(String rentACarName) {
		this.rentACarName = rentACarName;
	}
}
