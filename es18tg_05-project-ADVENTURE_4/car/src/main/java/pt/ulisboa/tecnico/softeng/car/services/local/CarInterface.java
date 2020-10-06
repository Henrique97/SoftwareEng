package pt.ulisboa.tecnico.softeng.car.services.local;

import org.joda.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.Motorcycle;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentACarData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.VehicleData;

public class CarInterface {
	public static enum Type {
		CAR, MOTORCYCLE
	}

	@Atomic(mode = TxMode.WRITE)

	public static void createRentACar(RentACarData rentACarData) {
		new RentACar(rentACarData.getName(), rentACarData.getNif(), rentACarData.getIban());
	}

	@Atomic(mode = TxMode.READ)

	public static List<RentACarData> listRentACars() {
		return FenixFramework.getDomainRoot().getRentACarSet().stream()
				.sorted((b1, b2) -> b1.getName().compareTo(b2.getName())).map(b -> new RentACarData(b))
				.collect(Collectors.toList());
	}

	@Atomic(mode = TxMode.WRITE)

	public static void createCar(VehicleData vehicle, String rentACarName) {
		RentACar rentacar = getRentACarByName(rentACarName);
		new Car(vehicle.getPlate(), vehicle.getKilometers(), vehicle.getPrice(), rentacar);
	}

	@Atomic(mode = TxMode.WRITE)

	public static void createMotorcycle(VehicleData vehicle, String rentACarName) {
		RentACar rentacar = getRentACarByName(rentACarName);
		new Motorcycle(vehicle.getPlate(), vehicle.getKilometers(), vehicle.getPrice(), rentacar);
	}

	@Atomic(mode = TxMode.READ)

	public static List<VehicleData> listVehicles(String name) {
		RentACar rentACar = getRentACarByName(name);
		if (rentACar == null)
			throw new CarException("No Rent-a-Car found with name " + name);
		return rentACar.getVehicleSet().stream().sorted((b1, b2) -> b1.getPlate().compareTo(b2.getPlate()))
				.map(b -> new VehicleData(b)).collect(Collectors.toList());
	}

	@Atomic(mode = TxMode.READ)

	public static Set<RentingData> listRentingsByVehicle(String plate) {
		Set<RentACar> rentACarSet = FenixFramework.getDomainRoot().getRentACarSet();
		Set<Renting> rentings = new HashSet<>();
		Set<RentingData> rentingDataSet = new HashSet<>();
		for (RentACar rentACar : rentACarSet) {
			for (Vehicle vehicle : rentACar.getVehicleSet()) {
				if (vehicle.getPlate().equals(plate))
					rentings = vehicle.getRentingSet();
			}
		}
		for (Renting rent : rentings) {
			rentingDataSet.add(new RentingData(rent));
		}
		if (rentingDataSet.isEmpty())
			return null;
		return rentingDataSet;
	}

	@Atomic(mode = TxMode.WRITE)

	public static void doCheckout(int kilometers, String plate, String rentingRef) {
		Set<RentACar> rentACarSet = FenixFramework.getDomainRoot().getRentACarSet();
		for (RentACar rentACar : rentACarSet) {
			for (Vehicle vehicle : rentACar.getVehicleSet()) {
				if (vehicle.getPlate().equals(plate)) {
					vehicle.getRenting(rentingRef).checkout(kilometers);
					return;
				}
			}
		}
		throw new CarException();
	}

	@Atomic(mode = TxMode.WRITE)

	public static void rentVehicle(String plate, String drivingLicense, LocalDate begin, LocalDate end, String buyerNIF,
			String buyerIBAN) {
		Set<RentACar> rentACarSet = FenixFramework.getDomainRoot().getRentACarSet();
		for (RentACar rentACar : rentACarSet) {
			for (Vehicle vehicle : rentACar.getVehicleSet()) {
				if (vehicle.getPlate().equals(plate)) {
					vehicle.rent(drivingLicense, begin, end, buyerNIF, buyerIBAN);
					return;
				}
			}
		}
		throw new CarException();
	}

	public static RentACar getRentACarByName(String name) {
		return FenixFramework.getDomainRoot().getRentACarSet().stream().filter(b -> b.getName().equals(name))
				.findFirst().orElse(null);
	}
	
	
	public static String rentCar(Type vehicleType, String drivingLicense, String nif, String iban, LocalDate begin,
			LocalDate end) {
		if (vehicleType == Type.CAR)
			return RentACar.rent(Car.class, drivingLicense, nif, iban, begin, end);
		else
			return RentACar.rent(Motorcycle.class, drivingLicense, nif, iban, begin, end);
	}

	public static String cancelRenting(String rentingReference) {
		return RentACar.cancelRenting(rentingReference);
	}

	public static RentingData getRentingData(String reference) {
		return RentACar.getRentingData(reference);

	}
}
