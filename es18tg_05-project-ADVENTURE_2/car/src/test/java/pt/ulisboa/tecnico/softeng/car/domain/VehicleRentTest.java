package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.car.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;

@RunWith(JMockit.class)
public class VehicleRentTest {
	private static final String PLATE_CAR = "22-33-HZ";
	private static final String RENT_A_CAR_NAME = "Eartz";
	private static final String RENT_A_CAR_NIF = "NIF";
	private static final String RENT_A_CAR_IBAN = "IBAN";
	private static final String DRIVING_LICENSE = "lx1423";
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-09");
	private static final String RENTING_NIF = "987654321";
	private static final String RENTING_IBAN = "IBAN2";
	private RentACar rentACar;
	
	private Car car;

	@Before
	public void setUp() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, RENT_A_CAR_NIF, RENT_A_CAR_IBAN);
		this.car = new Car(PLATE_CAR, 10, rentACar,50);
	}

	@Test(expected = CarException.class)
	public void doubleRent() {
		car.rent(DRIVING_LICENSE, date1, date2, RENTING_NIF, RENTING_IBAN);
		car.rent(DRIVING_LICENSE, date1, date2, RENTING_NIF, RENTING_IBAN);
	}

	@Test(expected = CarException.class)
	public void beginIsNull() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, RENT_A_CAR_NIF, RENT_A_CAR_IBAN);
		Vehicle car = new Car(PLATE_CAR, 10, rentACar,50);
		car.rent(DRIVING_LICENSE, null, date2, RENTING_NIF, RENTING_IBAN);
	}

	@Test(expected = CarException.class)
	public void endIsNull() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, RENT_A_CAR_NIF, RENT_A_CAR_IBAN);
		Vehicle car = new Car(PLATE_CAR, 10, rentACar,50);
		car.rent(DRIVING_LICENSE, date1, null, RENTING_NIF, RENTING_IBAN);
	}
	
	 @Test
	public void rentVehicle(@Mocked final TaxInterface taxInterface, @Mocked final BankInterface bankInterface) {
			new Expectations() {
				{
					BankInterface.processPayment(this.anyString, this.anyDouble);

					TaxInterface.submitInvoice((InvoiceData) this.any);
				}
			};
			
			Renting renting = car.rent(DRIVING_LICENSE, date1, date2, RENTING_NIF, RENTING_IBAN);

			Assert.assertTrue(renting.getReference() != null);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
