package pt.ulisboa.tecnico.softeng.car.domain;

public class Plate extends Plate_Base {
	public Plate(String id) {
		setId(id);
	}
	
	public void delete() {
		setRentACar(null);
		deleteDomainObject();
	}
	
	public static boolean plateExists(RentACar rentACar, String plateToSearch) {
		for (Plate plate : rentACar.getPlateSet())
			if (plate.getId().equals(plateToSearch))
				return true;
		
		return false;
	}
}
