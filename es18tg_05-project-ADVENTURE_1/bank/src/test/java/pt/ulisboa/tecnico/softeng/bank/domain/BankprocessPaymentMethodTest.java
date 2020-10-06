package pt.ulisboa.tecnico.softeng.bank.domain;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;



public class BankprocessPaymentMethodTest {
	private static int AMOUNT = 100;
	private Bank bank;
	private String IBAN;
	private Account account;

	@Before
	public void setUp() {
		this.bank = new Bank("Bank1", "BK01");
		Client client = new Client(this.bank, "João");
		this.account = new Account(this.bank, client);
		this.IBAN = this.account.getIBAN();
		this.account.deposit(2*AMOUNT);
	}

	@Test
	public void success() {
		bank.getAccount(this.IBAN).withdraw(AMOUNT);
		assertEquals(bank.getAccount(this.IBAN).getBalance(),AMOUNT);
	}
	
	@Test(expected = BankException.class)
	public void nullReference() {
		Bank.processPayment(null,AMOUNT);
	}
	
	@Test(expected = BankException.class)
	public void notAmountEnough() {
		Bank.processPayment(this.IBAN,AMOUNT*5);
	}
	

	@Test(expected = BankException.class)
	public void emptyReference() {
		Bank.processPayment("",AMOUNT);
	}

	@Test(expected = BankException.class)
	public void referenceNotExists() {
		Bank.processPayment("ABCD",AMOUNT);
	}

	@After
	public void tearDown() {
		Bank.banks.clear();
	}

}
