import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Borrowing extends Transaction {
	private int period;

	public Borrowing(int amount, int period) {
		super(amount);
		this.APY = 0.125;
		this.period = period;
		this.periodStart = LocalDate.now(ZoneId.of("GMT+3"));
		this.periodEnd = periodStart.plusMonths(period);
	}
	
	public String getPeriodStartString() {
		return periodStart.toString();
	}
	
	public String getPeriodEndString() {
		return periodEnd.toString();
	}
	
	public String getStringPeriod() {
		return (periodStart.toString() + " - " + periodEnd.toString());
	}

	public int getInterestAmount() {
		return (int) (amount*(APY/12)*(period));
	}

	public int getTotalAmount() {
		return amount + this.getInterestAmount();
	}
	
	public int payment() {
		return getTotalAmount();
	}

	
}
