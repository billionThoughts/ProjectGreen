import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

class StakingTest {

	@Test
	public void getInterestAmountTest() {
		int expected, result;
		Staking s = new Staking(1000);
		expected = 10;
		result = s.getInterestAmount();
		assertEquals(expected, result);
	}
	
	@Test
	public void isStakePeriodCompletedTest() {
		boolean expected, result;
		Staking s = new Staking(1000);
		
		LocalDate today = LocalDate.now(ZoneId.of("GMT+3"));
		LocalDate end = today.plusMonths(s.getPeriod());
		if(today.isBefore(end))
			expected = false;
		else expected = true;
		
		result = s.isStakePeriodCompleted();
		assertEquals(expected,result);
	}
	
	@Test
	public void paymentTest() {
		int expected, result;
		Staking s = new Staking(1000);
		int interest=0;
		if(s.isStakePeriodCompleted()) {
			interest = s.getInterestAmount();
		}
		expected = 1000 + interest;
		result = s.payment();
		assertEquals(expected, result);
	}

}
