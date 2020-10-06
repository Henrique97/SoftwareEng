package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;



public class RentACarGetAllAvaliableCarsMethodTest {
	private RentACar _rentACar;
	private Car _car1;
	private Car _car2;
	private Car _car3;

	private LocalDate _begin;
	private LocalDate _end;
	
	
	@Before
	public void setUp() {
		_rentACar = new RentACar("Europcar");
		_car1 = new Car("63-PB-82", 65000, _rentACar);
		_car2 = new Car("00-QL-29", 65000, _rentACar);
		_car3 = new Car("65-BT-27", 21000, _rentACar);

		_begin = new LocalDate(2018, 01, 19);
		_end = new LocalDate(2018, 01, 21);
				
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void success() {
		Set<Car> allCars = _rentACar.cars;
		Set<Car> twoCars =  (Set<Car>)(((HashSet<Car>)allCars).clone());
		Set<Car> oneCar =  (Set<Car>)(((HashSet<Car>)allCars).clone());
		Set<Car> empty = new HashSet<Car>();
		twoCars.remove(_car1);
		oneCar.remove(_car1);
		oneCar.remove(_car2);
		
		_car1.rent("L334138012", _begin, _end);
	
		Assert.assertEquals(allCars, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 17), new LocalDate(2018, 01, 18)));
		Assert.assertEquals(twoCars, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 18), new LocalDate(2018, 01, 19)));
		Assert.assertEquals(twoCars, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 19), new LocalDate(2018, 01, 20)));
		Assert.assertEquals(twoCars, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 20), new LocalDate(2018, 01, 21)));
		Assert.assertEquals(twoCars, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 21), new LocalDate(2018, 01, 22)));
		Assert.assertEquals(allCars, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 22), new LocalDate(2018, 01, 23)));
		
		_car2.rent("L334138012", _begin, _end);
		
		Assert.assertEquals(allCars, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 17), new LocalDate(2018, 01, 18)));
		Assert.assertEquals(oneCar, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 18), new LocalDate(2018, 01, 19)));
		Assert.assertEquals(oneCar, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 19), new LocalDate(2018, 01, 20)));
		Assert.assertEquals(oneCar, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 20), new LocalDate(2018, 01, 21)));
		Assert.assertEquals(oneCar, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 21), new LocalDate(2018, 01, 22)));
		Assert.assertEquals(allCars, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 22), new LocalDate(2018, 01, 23)));

		_car3.rent("L334138012", _begin, _end);
		
		Assert.assertEquals(allCars, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 17), new LocalDate(2018, 01, 18)));
		Assert.assertEquals(empty, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 18), new LocalDate(2018, 01, 19)));
		Assert.assertEquals(empty, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 19), new LocalDate(2018, 01, 20)));
		Assert.assertEquals(empty, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 20), new LocalDate(2018, 01, 21)));
		Assert.assertEquals(empty, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 21), new LocalDate(2018, 01, 22)));
		Assert.assertEquals(allCars, _rentACar.getAllAvaliableCars(new LocalDate(2018, 01, 22), new LocalDate(2018, 01, 23)));

		
	}
	
	@After
	public void tearDown() {
		RentACar.rentACars.clear();		
	}
}
