package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.exception.*;

public class RentACarGetRentingDataMethodTest {
	private final String referenceWrong = "referencia errada";
	private RentACar rentACar;
	private Renting rent1;
	private final String strBegin = "2007-12-03";
	private final String strEnd = "2007-12-03";
	private final String licencePlate = "32-ND-42";
	private final String drivingLicense = "L22222222";
	private final int kilometers = 30000;
	private final LocalDate begin = LocalDate.parse(strBegin);
	private final LocalDate end = LocalDate.parse(strEnd);
	private Vehicle vehicle;
	
	@Before
	public void setUp() {
		this.rentACar = new RentACar("Rent your car");
		this.vehicle=new Car(this.licencePlate,this.kilometers,this.rentACar);
		this.rent1 = new Renting(this.drivingLicense, this.begin, this.end, this.vehicle );
	}
	
	@Test
	public void hasRenting() {
		RentingData rentingData = this.rentACar.getRentingData(rent1.getReference());

		/*Reference is unique, so you only to the check if not null*/
		Assert.assertNotNull(rentingData);
	}

	@Test(expected = CarException.class)
	public void nullRef() {
		this.rentACar.getRentingData(null);
	}

	@Test(expected = CarException.class)
	public void emptyRef() {
		this.rentACar.getRentingData("");
	}

	@Test(expected = CarException.class)
	public void unknownRef() {
		this.rentACar.getRentingData(referenceWrong);
	}

	@After
	public void tearDown() {
		rentACar.vehicles.clear();
		RentACar.rentACars.clear();
	}

}