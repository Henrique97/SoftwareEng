package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;

import org.junit.After;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;


public class CarPersistenceTest {
	
	private static final String PLATE_CAR = "22-33-HZ";
	private static final String RENT_A_CAR_NAME = "eartz";
	private static final String RENT_A_CAR_NIF = "NIF";
	private static final String RENT_A_CAR_IBAN = "IBAN";
	private static final String RENT_A_CAR_CODE = "NIF1";
	private static final String DRIVING_LICENSE = "br112233";
	private static final LocalDate begin = LocalDate.parse("2018-01-06");
	private static final LocalDate end = LocalDate.parse("2018-01-07");
	private static final String NIF = "NIF";
	private static final String IBAN_BUYER = "IBAN";

		
	@Test
	public void success() {
		atomicProcess();
		atomicAssert();
	}
	
	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, RENT_A_CAR_NIF, RENT_A_CAR_IBAN);
		
		Car car = new Car(PLATE_CAR, 20000, 20000, rentACar);	
		
		new Renting(DRIVING_LICENSE, begin, end, car, NIF, IBAN_BUYER);
		
	}
	
	@Atomic(mode = TxMode.READ)
	public void atomicAssert() {
		assertEquals(1, FenixFramework.getDomainRoot().getRentACarSet().size());
		
		List<RentACar> rentACars = new ArrayList<>(FenixFramework.getDomainRoot().getRentACarSet());
		RentACar rentACar = rentACars.get(0);
		
		assertEquals(RENT_A_CAR_NAME, rentACar.getName());
		assertEquals(RENT_A_CAR_NIF, rentACar.getNif());
		assertEquals(RENT_A_CAR_IBAN, rentACar.getIban());
		assertEquals(RENT_A_CAR_CODE, rentACar.getCode());
		assertNotNull(rentACar.getProcessor());

		
		assertEquals(1, rentACar.getVehicleSet().size());
		List<Vehicle> vehicles = new ArrayList<>(rentACar.getVehicleSet());
		Vehicle vehicle = vehicles.get(0);
		assertEquals(PLATE_CAR, vehicle.getPlate());
		assertEquals(20000,vehicle.getKilometers());
		assertEquals(20000,vehicle.getPrice(), 0.001);
		
		assertEquals(1, rentACar.getPlateSet().size());
		List<Plate> plates = new ArrayList<>(rentACar.getPlateSet());
		Plate plate = plates.get(0);
		assertEquals(PLATE_CAR, plate.getId());

		assertEquals(1, vehicle.getRentingSet().size());
		List<Renting> rentings = new ArrayList<>(vehicle.getRentingSet());
		Renting renting = rentings.get(0);
		assertNotNull(renting.getReference());
		assertNull(renting.getCancellationReference());
		assertNull(renting.getCancellationDate());
		assertNull(renting.getPaymentReference());
		assertNull(renting.getInvoiceReference());
		assertNull(renting.getCancelledPaymentReference());
		assertFalse(renting.getCancelledInvoice());
		assertEquals(DRIVING_LICENSE, renting.getDrivingLicense());
		assertEquals(NIF, renting.getClientNIF());
		assertEquals(IBAN_BUYER, renting.getClientIBAN());
		assertEquals(begin, renting.getBegin());
		assertEquals(end, renting.getEnd());
	}
	
	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		for (RentACar rentACar : FenixFramework.getDomainRoot().getRentACarSet()) {
			rentACar.delete();
		}
	}
}