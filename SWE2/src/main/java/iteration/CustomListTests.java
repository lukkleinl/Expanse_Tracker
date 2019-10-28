package iteration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomListTests {
	CustomList<Object> list;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		list = new CustomList<>();
	}

	@AfterEach
	void tearDown() throws Exception {
		list = new CustomList<>();
	}

	@Test
	void add_should_contain_added_value() {
		int number = 7;
		list.add(number);
		assertEquals(list.getIterator().next(),number);
	}

}
