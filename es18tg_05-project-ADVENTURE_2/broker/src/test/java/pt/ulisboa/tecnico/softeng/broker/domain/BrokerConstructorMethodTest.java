package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class BrokerConstructorMethodTest {

	@Test
	public void success() {
		Broker broker = new Broker("BR01", "WeExplore", "123456789","987654321", "BE68539007547034");
		Assert.assertEquals("BR01", broker.getCode());
		Assert.assertEquals("WeExplore", broker.getName());
		Assert.assertEquals("123456789", broker.getSellerNIF());
		Assert.assertEquals("987654321", broker.getBuyerNIF());
		Assert.assertEquals(0, broker.getNumberOfAdventures());
		Assert.assertTrue(Broker.brokers.contains(broker));
	}

	@Test
	public void nullCode() {
		try {
			new Broker(null, "WeExplore","123456789","987654321","BE68539007547034");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptyCode() {
		try {
			new Broker("", "WeExplore","123456789","987654321","BE68539007547034");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankCode() {
		try {
			new Broker("  ", "WeExplore","123456789","987654321","BE68539007547034");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void uniqueCode() {
		Broker broker = new Broker("BR01", "WeExplore","123456789","987654321","BE68539007547034");

		try {
			new Broker("BR01", "WeExploreX","123456789","987654321","BE68539007547034");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(1, Broker.brokers.size());
			Assert.assertTrue(Broker.brokers.contains(broker));
		}
	}
	
	@Test
	public void differentCode() {
		Broker broker1 = new Broker("TG01", "ABC","123456789","987654321","BE68539007547034");
		Broker broker2 = new Broker("TG02", "ABC","123456709","987654320","BE68539007547034");
		
		Assert.assertEquals(2, Broker.brokers.size());
		Assert.assertTrue(Broker.brokers.contains(broker1));
		Assert.assertTrue(Broker.brokers.contains(broker2));
	}

	@Test
	public void nullName() {
		try {
			new Broker("BR01", null,"123456789","987654321","BE68539007547034");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}
	
	@Test
	public void nullBuyerNIF() {
		try {
			new Broker("BR01", "ABC","123456789",null,"BE68539007547034");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}
	
	@Test
	public void nullSellerNIF() {
		try {
			new Broker("BR01", "ABC",null,"987654321","BE68539007547034");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}
	
	@Test(expected = BrokerException.class)
	public void uniqueNIF() {
		new Broker("TG01", "ABC","123456789","987654321","BE68539007547034");
		new Broker("TG02", "ABD","123456789","987654321","BE68539007547034");
	}

	@Test(expected = BrokerException.class)
	public void uniqueSellerNIF() {
		new Broker("TG01", "ABC","123456789","987654321","BE68539007547034");
		new Broker("TG02", "ABD","123456789","947654321","BE68539007547034");
	}
	
	@Test(expected = BrokerException.class)
	public void uniqueBuyerNIF() {
		new Broker("TG01", "ABC","123456789","987654321","BE68539007547034");
		new Broker("TG02", "ABD","123456788","987654321","BE68539007547034");
	}
	

	@Test
	public void emptyName() {
		try {
			new Broker("BR01", "","123456789","987654321","BE68539007547034");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankName() {
		try {
			new Broker("BR01", "    ","123456789","987654321","BE68539007547034");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@After
	public void tearDown() {
		Broker.brokers.clear();
	}

}
