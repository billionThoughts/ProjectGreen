import java.time.LocalDate;
import java.time.ZoneId;

public class Staking extends Transaction {
	private int period; //duration of period in months, default is 1

	public Staking(int amount) {
		super(amount);
		this.APY = 0.12;
		this.period = 1;
		this.periodStart = LocalDate.now(ZoneId.of("GMT+3"));
		this.periodEnd = periodStart.plusMonths(period);
	}
	
	public int getPeriod() {
		return period;
	}
	
	public String getStringPeriod() {
		return (periodStart.toString() + " - " + periodEnd.toString());
	}
	
	public int getInterestAmount() {
		double interest = APY*period/12;
		return (int) (amount*interest);
	}
	
	//Checks if period is completed
	public boolean isStakePeriodCompleted() {
		if(periodEnd.equals(LocalDate.now(ZoneId.of("GMT+3")))){
			return true;
		}
		return false;
	}
	
	//Checks if period is completed and returns the counterpart amount to be paid.
	public int payment() {
		if(isStakePeriodCompleted()) {
			return getTotalAmount();
		}
		else return amount;
	}

}
