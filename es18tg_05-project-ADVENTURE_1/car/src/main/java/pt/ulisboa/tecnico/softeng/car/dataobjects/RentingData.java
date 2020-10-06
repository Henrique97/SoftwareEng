package pt.ulisboa.tecnico.softeng.car.dataobjects;


import org.joda.time.LocalDate;



public class RentingData {
	
	private String reference; 
	private String plate;
	private String drivingLicense;
	private String rentACarCode; 
	private LocalDate begin; 
	private LocalDate end;
	
	public RentingData(String reference, String plate, String drivingLicense, String rentACarCode, LocalDate begin, LocalDate end) {
		this.reference=reference;
		this.plate=plate;
		this.drivingLicense=drivingLicense;
		this.rentACarCode= rentACarCode; 
		this.begin=begin;
		this.end=end;
	}

	public String getReference() {
		return reference;
	}

	public String getPlate() {
		return plate;
	}

	public String getDrivingLicense() {
		return drivingLicense;
	}

	public String getRentACarCode() {
		return rentACarCode;
	}

	public LocalDate getBegin() {
		return begin;
	}

	public LocalDate getEnd() {
		return end;
	}
	
	public RentingData getRentingData(String reference){
		return this;
	}
}
