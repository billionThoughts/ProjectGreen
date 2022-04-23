import java.io.*;
import java.time.LocalDate;

public abstract class Transaction implements Serializable {
	protected int amount;
	protected double APY;
	protected LocalDate periodStart;
	protected LocalDate periodEnd;
	
	public Transaction(int amount) {
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
	
	public abstract String getPeriodStartString();
	
	public abstract String getPeriodEndString();
	
	public abstract int getTotalAmount();
	
	public abstract int getInterestAmount();
	
	public abstract int payment();
}