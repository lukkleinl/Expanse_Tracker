package iteration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import exceptions.SWE_RuntimeException;


class CustomListTests {
  CustomList<Object> list;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    list = new CustomList<>();
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void nextOnNewList_shouldThrowNoElementException() {
    assertThrows(SWE_RuntimeException.class, () -> list.getIterator().element());
    assertThrows(SWE_RuntimeException.class, () -> list.getIterator().next());
  }

  @Test
  void hasNextOnNewList_shouldReturnFalse() {
    assertEquals(false,list.getIterator().hasNext());
  }

  @Test
  void addedElement_shouldBeEqual() {
    Object o = new Object();
    list.add(o);
    assertEquals(o,list.getIterator().element());
    assertEquals(o,list.getIterator().next());
  }

  @Test
  void sizeOfNewList_shouldBeZero() {
    assertEquals(0,list.size());
  }

  @ParameterizedTest
  @ValueSource(strings = {"abc","abcd","abcde","abcdef"})
  void sizeAfterAdding_shouldBeLarger(final String param) {
    for (int i = 0; i < param.length(); i++) {
      list.add(param.charAt(i));
    }
    assertEquals(list.size(),param.length());
  }

  @Test
  void lastElement_shouldBeEqual() {
    int number = 7;
    String s = "text";
    String nums = "" + number;

    list.add(s);
    list.add(s + number);
    list.add(nums);

    Object result = "";
    CustomIterator<Object> iter = list.getIterator();

    while (iter.hasNext()) {
      result = iter.next();
    }

    assertEquals(nums, result);
  }

  @Test
  void accessAfterLastElement_shouldThrowNoElementException() {
    int number = 7;
    String s = "text";
    String nums = "" + number;

    list.add(s);
    list.add(s + number);
    list.add(nums);

    CustomIterator<Object> iter = list.getIterator();

    while (iter.hasNext()) {
      iter.next();
    }

    assertThrows(SWE_RuntimeException.class, () -> iter.element());
    assertThrows(SWE_RuntimeException.class, () -> iter.next());
  }

}
