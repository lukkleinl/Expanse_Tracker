import static org.junit.jupiter.api.Assertions.*;

import iteration.CustomList;
import java.util.Iterator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomListTests {
  CustomList<String> slist;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    slist = new CustomList<>();
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void nextOnNewList_shouldReturnNull() {
    assertEquals(slist.getIterator().hasNext(), false);
  }

  @Test
  void hasNextOnNewList_shouldReturnFalse() {
    assertEquals(slist.getIterator().next(), null);
  }

  @Test
  void lastElement_shouldBeEqual() {
    int number = 7;
    String s = "text";
    String nums = "" + number;

    slist.add(s);
    slist.add(s + number);
    slist.add(nums);

    String result = "";
    Iterator<String> iter = slist.getIterator();

    while (iter.hasNext()) {
      result = iter.next();
    }

    assertEquals(nums, result);
  }

  @Test
  void afterLastElement_shouldNextBeNull() {
    int number = 7;
    String s = "text";
    String nums = "" + number;

    slist.add(s);
    slist.add(s + number);
    slist.add(nums);

    Iterator<String> iter = slist.getIterator();

    while (iter.hasNext()) {
      iter.next();
    }

    assertEquals(null, iter.next());
  }
}
