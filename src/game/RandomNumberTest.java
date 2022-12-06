package game;


import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

class RandomNumberTest {

	RandomNumber r = new RandomNumber();
	@Test
	void getRandomNumber() {
		assertTrue("in the range", r.getRandomNumber(1, 20) > 0 || r.getRandomNumber(1, 20) < 20 );
	}

}
