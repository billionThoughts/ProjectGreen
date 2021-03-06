package main;
import java.time.LocalDate;
import java.time.ZoneId;

public class Borrowing extends Transaction {
	private static final long serialVersionUID = 1L;
	private int period;

	public Borrowing(int amount, int period) {
		super(amount);
		this.APY = 0.125;
		this.period = period;
		this.periodStart = LocalDate.now(ZoneId.of("GMT+3"));
		this.periodEnd = periodStart.plusMonths(period);
	}
	
	public String getStringPeriod() {
		return (periodStart.toString() + " - " + periodEnd.toString());
	}

	public int getInterestAmount() {
		return (int) (amount*(APY/12)*(period));
	}
	
	public int payment() {
		return getTotalAmount();
	}

	
}
