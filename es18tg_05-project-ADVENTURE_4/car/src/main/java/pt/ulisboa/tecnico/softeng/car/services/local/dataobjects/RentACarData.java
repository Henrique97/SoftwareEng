package pt.ulisboa.tecnico.softeng.car.services.local.dataobjects;

import java.util.List;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.softeng.car.domain.Processor;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;

public class RentACarData {
	String code;
	String name;
	String nif;
	String iban;
	Processor processor;
	List<VehicleData> vehicles;

	public RentACarData() {
	}

	public RentACarData(RentACar rentACar) {
		code = rentACar.getCode();
		name = rentACar.getName();
		nif = rentACar.getNif();
		iban = rentACar.getIban();
		processor = rentACar.getProcessor();
		vehicles = rentACar.getVehicleSet().stream().sorted((c1, c2) -> c1.getPlate().compareTo(c2.getPlate()))
				.map(c -> new VehicleData(c)).collect(Collectors.toList());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Processor getProcessor() {
		return processor;
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	public List<VehicleData> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<VehicleData> vehicles) {
		this.vehicles = vehicles;
	}
}
