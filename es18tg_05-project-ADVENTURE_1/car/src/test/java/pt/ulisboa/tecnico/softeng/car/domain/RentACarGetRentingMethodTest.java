package pt.ulisboa.tecnico.softeng.car.domain;


import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.*;

public class RentACarGetRentingMethodTest {
	
	private final String referenceWrong = "referencia errada";
	private RentACar rentACar;
	private final String strBegin = "2007-12-03";
	private final String strEnd = "2007-12-03";
	private final String licencePlate = "32-ND-42";
	private final String drivingLicense = "L22222222";
	private final int kilometer = 30000;
	private final LocalDate begin = LocalDate.parse(strBegin);
	private final LocalDate end = LocalDate.parse(strEnd);
	private Vehicle vehicle;
	private Renting renting; 

	@Before
	public void setUp() {
		this.rentACar = new RentACar("Rent your car");
		this.vehicle = new Car(this.licencePlate,this.kilometer,this.rentACar);
		this.renting = new Renting(drivingLicense, begin, end, this.vehicle);
	}
	
	@Test
	public void hasRenting() {
		Renting rentingA = this.rentACar.getRenting(renting.getReference());

		/*Reference is unique, so you only to the check if not null*/
		Assert.assertNotNull(rentingA);
	}

	@Test(expected = CarException.class)
	public void nullRef() {
		this.rentACar.getRenting(null);
	}

	@Test(expected = CarException.class)
	public void emptyRef() {
		this.rentACar.getRenting("");
	}

	@Test
	public void unknownRef() {
		Renting renting = this.rentACar.getRenting(referenceWrong);
		Assert.assertNull(renting);
	}

	@After
	public void tearDown() {
		rentACar.vehicles.clear();
		RentACar.rentACars.clear();
	}

}
