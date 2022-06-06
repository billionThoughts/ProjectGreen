package test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.Borrowing;
import main.Lending;
import main.Staking;
import main.Ticket;
import main.Transaction;
import main.UserAccount;

class UserAccountTest {
	
	@Test
	public void isTicketAffordableTest1() {
		boolean expected, result;
		Ticket t = new Ticket("TestTicket", 1);
		UserAccount u = new UserAccount("Test", "Test", "Test", "Test", "Test");
		u.setTokens(2);
		
		expected = true;
		result = u.isTicketAffordable(t);
		assertEquals(expected, result);
	}

	@Test
	public void isTicketAffordableTest2() {
		boolean expected, result;
		Ticket t = new Ticket("TestTicket", 5);
		UserAccount u = new UserAccount("Test", "Test", "Test", "Test", "Test");
		u.setTokens(2);

		expected = false;
		result = u.isTicketAffordable(t);
		assertEquals(expected, result);
	}
	
	@Test
	public void donateTokensTest1() {
		boolean expected, result;
		UserAccount u = new UserAccount("Test", "Test", "Test", "Test", "Test");
		u.setTokens(5);
		
		expected = true;
		result = u.donateTokens(5);
		assertEquals(expected, result);
	}
	
	@Test
	public void donateTokensTest2() {
		int expected, result;
		UserAccount u = new UserAccount("Test", "Test", "Test", "Test", "Test");
		u.setTokens(5);
		
		expected = 0;
		u.donateTokens(5);
		result = u.getTokens();
		assertEquals(expected, result);
	}
	
	@Test
	public void donateTokensTest3() {
		boolean expected, result;
		UserAccount u = new UserAccount("Test", "Test", "Test", "Test", "Test");
		u.setTokens(5);
		
		expected = false;
		result = u.donateTokens(10);
		assertEquals(expected, result);
	}
	
	@Test
	public void removeTransactionTest1() {
		ArrayList<Transaction> expected, result;
		
		Transaction t1 = new Staking(1000);
		Transaction t2 = new Borrowing(1000, 3);
		Transaction t3 = new Lending(1000);
		
		UserAccount u = new UserAccount("Test", "Test", "Test", "Test", "Test");
		u.addTransaction(t1);
		u.addTransaction(t2);
		u.addTransaction(t3);
		u.removeTransaction(new Lending(1000));
		
		expected = new ArrayList<>();
		expected.add(t1);
		expected.add(t2);
		result = u.getTransactions();
		assertEquals(expected, result);
	}
	
	@Test
	public void removeTransactionTest2() {
		ArrayList<Transaction> expected, result;
		
		Transaction t1 = new Staking(1000);
		Transaction t2 = new Borrowing(1000, 3);
		Transaction t3 = new Lending(1000);
		
		UserAccount u = new UserAccount("Test", "Test", "Test", "Test", "Test");
		u.addTransaction(t1);
		u.addTransaction(t2);
		u.addTransaction(t3);
		u.removeTransaction(new Lending(1500));
		
		expected = new ArrayList<>();
		expected.add(t1);
		expected.add(t2);
		expected.add(t3);
		result = u.getTransactions();
		assertEquals(expected, result);
	}
}
