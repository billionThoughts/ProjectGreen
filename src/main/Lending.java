package main;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Lending extends Transaction {
	
	public Lending(int amount) {
		super(amount);
		this.APY = 0.192;
		this.periodStart = LocalDate.now(ZoneId.of("GMT+3"));
		this.periodEnd = LocalDate.now(ZoneId.of("GMT+3"));
	}
	
	//Assigns today's date to periodEnd
	public void setPeriodEnd() {
		this.periodEnd = LocalDate.now(ZoneId.of("GMT+3"));
	}
	
	public String getStringPeriod() {
		this.setPeriodEnd();
		return (periodStart.toString() + " - " + periodEnd.toString());
	}

	/*
	 * Calculates the real period between the date when the transaction happened and today
	 *  and returns the interest based on the real period
	 */
	public int getInterestAmount() {
		int realPeriod = (int) ChronoUnit.MONTHS.between(periodStart, periodEnd);
		return (int) (amount*(APY/12)*realPeriod);
	}
	
	public int payment() {
		this.setPeriodEnd();
		return this.getTotalAmount();
	}
}