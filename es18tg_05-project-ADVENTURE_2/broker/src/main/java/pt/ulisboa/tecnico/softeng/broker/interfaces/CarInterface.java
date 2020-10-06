package pt.ulisboa.tecnico.softeng.broker.interfaces;

import java.util.Set;
import org.joda.time.LocalDate;
import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;


public class CarInterface {
	public static Set<Vehicle> getAllAvailableMotorcycles(LocalDate begin, LocalDate end) {
		return RentACar.getAllAvailableMotorcycles(begin, end);
	}
	public static Set<Vehicle> getAllAvailableCars(LocalDate begin, LocalDate end) {
		return RentACar.getAllAvailableCars(begin, end);
	}
}