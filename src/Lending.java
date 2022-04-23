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
	
	public void setPeriodEnd() {
		this.periodEnd = LocalDate.now(ZoneId.of("GMT+3"));
	}
	
	public String getPeriodStartString() {
		return periodStart.toString();
	}
	
	public String getPeriodEndString() {
		this.setPeriodEnd();
		return periodEnd.toString();
	}
	
	public String getStringPeriod() {
		this.setPeriodEnd();
		return (periodStart.toString() + " - " + periodEnd.toString());
	}

	public int getTotalAmount() {
		return this.amount + this.getInterestAmount();
	}

	public int getInterestAmount() {
		int realPeriod = (int) ChronoUnit.MONTHS.between(periodStart, periodEnd);
		return (int) (amount*(APY/12)*realPeriod);
	}

	public int payment() {
		this.setPeriodEnd();
		return this.getTotalAmount();
	}
}