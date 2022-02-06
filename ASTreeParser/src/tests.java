import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class tests {

	// Define tests of AstTreeParser
	@Test
	void test() {
		assertEquals(2, calculator(1, 1));
	}
	
	public int calculator(int a, int b) {
		return a + b;
	}

}
