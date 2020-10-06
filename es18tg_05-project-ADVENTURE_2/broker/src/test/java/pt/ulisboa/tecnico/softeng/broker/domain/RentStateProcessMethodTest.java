	package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

@RunWith(JMockit.class)
public class RentStateProcessMethodTest {
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private static final String TAX_CONFIRMATION = "TaxConfirmation";
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private Adventure adventure;
	private static final String BUYERNIF = "987654321";
	private static final String PLATE_CAR = "22-33-HZ";
	private static final String RENT_A_CAR_NAME = "Eartz";
	private static final String RENT_A_CAR_NIF = "NIF";
	private static final String RENT_A_CAR_IBAN = "IBAN";
	private static final RentACar RENTER = new RentACar(RENT_A_CAR_NAME, RENT_A_CAR_NIF, RENT_A_CAR_IBAN);
	private static final Set<Vehicle> Cars = new HashSet<>();
	private static final Vehicle CAR = new Car(PLATE_CAR, 10, RENTER ,50);
	
	private Broker broker;

	@Before
	public void setUp() {
		broker = new Broker("BR99", "WeExplorer", "123456689","987854321", "BE68539002347034");
		this.adventure = new Adventure(this.broker, this.begin, this.end, new Client(IBAN, 20, BUYERNIF), AMOUNT);
		Cars.add(CAR);
		this.adventure.setState(State.RENT_STATE);
		this.adventure.setCar(true);
	}

	@Test
	public void success(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{	
				CarInterface.getAllAvailableCars((LocalDate)this.any, (LocalDate)this.any);
				this.result = Cars;
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.PROCESS_PAYMENT, this.adventure.getState());
	}

	@Test
	public void CarException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{	
				CarInterface.getAllAvailableCars((LocalDate)this.any, (LocalDate)this.any);
				this.result = new CarException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}

	@Test
	public void singleRemoteAccessException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{	
				CarInterface.getAllAvailableCars((LocalDate)this.any, (LocalDate)this.any);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.RENT_STATE, this.adventure.getState());
	}

	@Test
	public void maxRemoteAccessException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{	
				CarInterface.getAllAvailableCars((LocalDate)this.any, (LocalDate)this.any);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}

	@Test
	public void maxMinusOneRemoteAccessException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{	
				CarInterface.getAllAvailableCars((LocalDate)this.any, (LocalDate)this.any);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.RENT_STATE, this.adventure.getState());
	}

	@Test
	public void oneRemoteAccessExceptionOneCarException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.getAllAvailableCars((LocalDate)this.any, (LocalDate)this.any);

				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 1) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							throw new CarException();
						}
					}
				};
				this.times = 2;

			}
		};

		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}
	
	@After
	public void tearDown() {
		Broker.brokers.clear();
		Cars.clear();
	}

}