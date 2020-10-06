package pt.ulisboa.tecnico.softeng.activity.domain;

import org.joda.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;

public class ActivityProviderReserveActivityMethodTest {
	private static final int MIN_AGE = 25;
	private static final int MAX_AGE = 80;
	private static final int CAPACITY = 25;
	
	private ActivityProvider _provider1;
	
	private Activity _activity1;
	private Activity _activity2;
	private Activity _activity3;
	
	
	@Before
	public void setUp() {
		_provider1 = new ActivityProvider("123456", "NomeTest1");
		
		_activity1 = new Activity(_provider1, "Activity11", MIN_AGE, MAX_AGE, CAPACITY);
		_activity2 = new Activity(_provider1, "Activity12", MIN_AGE, MAX_AGE, CAPACITY);
		_activity3 = new Activity(_provider1, "Activity13", MIN_AGE, MAX_AGE, CAPACITY);
		
		new ActivityOffer(_activity1, new LocalDate(2018,01,15), new LocalDate(2018,01,16));
		new ActivityOffer(_activity2, new LocalDate(2018,01,17), new LocalDate(2018,01,18));
		new ActivityOffer(_activity3, new LocalDate(2018,01,15), new LocalDate(2018,01,18));

	}
	
	
	@Test
	public void sucess() {
		ActivityProvider.reserveActivity(new LocalDate(2018,01,15), new LocalDate(2018,01,16), 40);
	}
	
	
	@Test(expected = ActivityException.class)
	public void noActivity() {
		ActivityProvider.reserveActivity(new LocalDate(2018,02,17), new LocalDate(2018,02,18), 40);
	}
	
	
	@After
	public void tearDown() {
		ActivityProvider.providers = new HashSet<>();
	}
	
}
