import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BorrowingTest {

	@Test
	public void getInterestAmountTest() {
		int expected, result;
		Borrowing b = new Borrowing(1000, 1);
		
		expected = 10;
		result = b.getInterestAmount();
		assertEquals(expected, result);
	}

}
