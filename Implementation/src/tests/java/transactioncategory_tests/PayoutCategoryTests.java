package transactioncategory_tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reworked.transactions.categories.PayoutCategory;
import reworked.transactions.categories.TransactionCategoryFunctionality;

class PayoutCategoryTests {
  private final String newcat = "RENT";
  private TransactionCategoryFunctionality stratNullMember;
  private TransactionCategoryFunctionality stratNonNullMember;
  private Map<String,Set<String>> categor;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    stratNullMember = new PayoutCategory();
    stratNonNullMember = new PayoutCategory(newcat);
    categor = new HashMap<>();
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void privateFieldEqualNull_shouldNotAddNewEntryInMap() {
    assertTrue(categor.isEmpty());
    stratNullMember.addCategory(categor);
    assertTrue(categor.isEmpty());
  }

  @Test
  void privateFieldNotEqualNull_shouldAddNewEntryInMap() {
    assertTrue(categor.isEmpty());
    stratNonNullMember.addCategory(categor);
    assertFalse(categor.isEmpty());
  }

  @Test
  void gettingUnsupportedCategory_shouldReturnEmptyList() {
    assertTrue(stratNonNullMember.getCategories(null).isEmpty());
    assertTrue(stratNullMember.getCategories(null).isEmpty());

    assertTrue(stratNonNullMember.getCategories(categor).isEmpty());
    assertTrue(stratNullMember.getCategories(categor).isEmpty());
  }

  @Test
  void addOnPrivateNonNullField_shouldBeEqual() {
    stratNonNullMember.addCategory(categor);
    assertTrue(categor.get(PayoutCategory.CATEGORY).contains(newcat));
  }

  @Test
  void gettingAfterAdding_shouldReturnNonEmptyList() {
    assertTrue(stratNonNullMember.getCategories(null).isEmpty());
    assertTrue(stratNullMember.getCategories(null).isEmpty());
    assertTrue(stratNonNullMember.getCategories(categor).isEmpty());
    assertTrue(stratNullMember.getCategories(categor).isEmpty());

    stratNullMember.addCategory(categor);

    assertTrue(stratNonNullMember.getCategories(null).isEmpty());
    assertTrue(stratNullMember.getCategories(null).isEmpty());
    assertTrue(stratNonNullMember.getCategories(categor).isEmpty());
    assertTrue(stratNullMember.getCategories(categor).isEmpty());

    stratNonNullMember.addCategory(categor);

    assertTrue(stratNonNullMember.getCategories(null).isEmpty());
    assertTrue(stratNullMember.getCategories(null).isEmpty());
    assertFalse(stratNonNullMember.getCategories(categor).isEmpty());
    assertFalse(stratNullMember.getCategories(categor).isEmpty());
  }

}
