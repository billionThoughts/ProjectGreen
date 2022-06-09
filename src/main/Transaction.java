package main;
import java.io.*;
import java.time.LocalDate;

public abstract class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int amount;
	protected double APY; //Anual Percentage Yield
	protected LocalDate periodStart;
	protected LocalDate periodEnd;
	
	protected Transaction(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public double getAPY() {
		return APY;
	}
	
	public LocalDate getPeriodEnd() {
		return periodEnd;
	}
	
	public abstract String getStringPeriod();
	
	public String getPeriodStartString() {
		return periodStart.toString();
	}
	
	public String getPeriodEndString() {
		return periodEnd.toString();
	}
	
	public abstract int getInterestAmount();
	
	public int getTotalAmount() {
		return this.amount + this.getInterestAmount();
	}
	
	public abstract int payment();
}