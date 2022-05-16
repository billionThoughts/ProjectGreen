import java.time.LocalDate;
import java.time.ZoneId;

public class Staking extends Transaction {
	private int period;

	public Staking(int amount) {
		super(amount);
		this.APY = 0.12;
		this.period = 1;
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
		double interest = APY*period/12;
		return (int) (amount*interest);
	}
	
	public int getTotalAmount() {
		return amount + this.getInterestAmount();
	}
	
	public boolean isStakePeriodCompleted() {
		if(periodEnd.equals(LocalDate.now(ZoneId.of("GMT+3")))){
			return true;
		}
		return false;
	}
	
	public int payment() {
		int amountToPay = 0;
		LocalDate today = LocalDate.now(ZoneId.of("GMT+3"));
		if(today.compareTo(periodEnd) >= 0) {
			amountToPay = getTotalAmount();
		}
		else amountToPay = amount;
		return amountToPay;
	}
}
