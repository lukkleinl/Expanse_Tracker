package iteration_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exceptions.NoElementException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import swe_IteratorPattern.CustomIterator;
import swe_IteratorPattern.CustomList;


class CustomListTests {
  CustomList<Object> list;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    this.list = new CustomList<>();
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void nextOnNewList_shouldThrowNoElementException() {
    assertThrows(NoElementException.class, () -> this.list.getIterator().next());
  }

  @Test
  void hasNextOnNewList_shouldReturnFalse() {
    assertEquals(false,this.list.getIterator().hasNext());
  }

  @Test
  void addedElement_shouldBeEqual() {
    Object o = new Object();
    this.list.add(o);
    assertEquals(o,this.list.getIterator().next());
  }

  @Test
  void sizeOfNewList_shouldBeZero() {
    assertEquals(0,this.list.size());
  }

  @ParameterizedTest
  @ValueSource(strings = {"abc","abcd","abcde","abcdef"})
  void sizeAfterAdding_shouldBeLarger(String param) {
    for (int i = 0; i < param.length(); i++) {
      this.list.add(param.charAt(i));
    }
    assertEquals(this.list.size(),param.length());
  }

  @Test
  void lastElement_shouldBeEqual() {
    int number = 7;
    String s = "text";
    String nums = "" + number;

    this.list.add(s);
    this.list.add(s + number);
    this.list.add(nums);

    Object result = "";
    CustomIterator<Object> iter = this.list.getIterator();

    while (iter.hasNext()) {
      result = iter.next();
    }

    assertEquals(nums, result);
  }

  @Test
  void nextAfterLastElement_shouldThrowNoElementException() {
    int number = 7;
    String s = "text";
    String nums = "" + number;

    this.list.add(s);
    this.list.add(s + number);
    this.list.add(nums);

    CustomIterator<Object> iter = this.list.getIterator();

    while (iter.hasNext()) {
      iter.next();
    }

    assertThrows(NoElementException.class, () -> iter.next());
  }

}
