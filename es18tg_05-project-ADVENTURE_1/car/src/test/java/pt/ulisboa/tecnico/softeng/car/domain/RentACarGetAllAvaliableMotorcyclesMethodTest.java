package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import org.junit.After;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class RentACarGetAllAvaliableMotorcyclesMethodTest{

	private Motorcycle mc1;
	private Motorcycle mc2;
	private Motorcycle mc3;
	private Motorcycle mc4;
	private Motorcycle mc5; 
	private RentACar rt1;
	private Set<Motorcycle> motoAv = new HashSet<>();

	@Before
	public void setUp() {
		this.rt1 = new RentACar("AlbaCar");
		this.mc1 = new Motorcycle("11-X1-11",0,rt1);
		this.mc2 = new Motorcycle("11-11-12",0,rt1);
		this.mc3 = new Motorcycle("11-11-13",0,rt1);
		this.mc4 = new Motorcycle("11-11-14",0,rt1);
		this.mc5 = new Motorcycle("11-11-15",0,rt1);
		this.mc1.rent("MAX001",new LocalDate(2007,11,03),new LocalDate(2007,11,10));
		this.mc2.rent("MAX002",new LocalDate(2007,12,13),new LocalDate(2007,12,20));
		this.mc3.rent("MAX003",new LocalDate(2017,12,13),new LocalDate(2017,12,20));
		this.mc4.rent("MAX004",new LocalDate(2007,12,13),new LocalDate(2007,12,24));
		this.mc5.rent("MAX005",new LocalDate(2007,12,01),new LocalDate(2007,12,15));
		this.motoAv.add(this.mc3);
		this.motoAv.add(this.mc1); 
	}

	@Test
	public void success() {
		Assert.assertEquals(this.motoAv, rt1.getAllAvaliableMotorcycles(new LocalDate(2007,12,03), new LocalDate(2007,12,20)));
	}
	

	@After
	public void tearDown() {
		rt1.motorcycles.clear();
		rt1.vehicles.clear();
		RentACar.rentACars.clear();
	}



}

