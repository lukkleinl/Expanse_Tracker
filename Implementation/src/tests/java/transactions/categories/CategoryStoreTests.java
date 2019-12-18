package transactions.categories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import transactions.categories.CategoryStore;
import transactions.categories.DepositCategory;
import transactions.categories.PayoutCategory;
import transactions.categories.TransactionCategoryFunctionality;

class CategoryStoreTests {
  private final String supporteddepo = "SALARY";
  private final String supportedpay = "FOOD";
  private final String unsupported = "UnsupportedCategory";
  private final String dummydepocat = "DummyDepo";
  private final String dummypaycat = "DummyPay";

  private CategoryStore store;
  private TransactionCategoryFunctionality depo;
  private TransactionCategoryFunctionality pay;
  private int defaultsize;
  private int defaultdepo;
  private int defaultpay;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    store = new CategoryStore().withDefaultCategories();
    depo = new DepositCategory(dummydepocat);
    pay = new PayoutCategory(dummypaycat);
    defaultsize = store.getCategories(null).size();
    defaultdepo = store.getCategories(depo).size();
    defaultpay = store.getCategories(pay).size();
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void getCategoriesWithoutAddedValues_shouldReturnEmptySet() {
    assertTrue(new CategoryStore().getCategories(null).isEmpty());
  }

  @Test
  void addWithNullStrategy_shouldNotAdd() {
    assertEquals(defaultsize,store.getCategories(null).size());
    store.addTransactionCategory(null);
    assertEquals(defaultsize,store.getCategories(null).size());
  }

  @Test
  void addWithNonEmptyDepositStrategy_shouldAdd() {
    assertEquals(defaultdepo,store.getCategories(depo).size());
    store.addTransactionCategory(depo);
    assertNotEquals(defaultdepo,store.getCategories(depo).size());
  }

  @Test
  void addWithEmptyDepositStrategy_shouldNotAdd() {
    assertEquals(defaultdepo,store.getCategories(new DepositCategory()).size());
    store.addTransactionCategory(new DepositCategory());
    assertEquals(defaultdepo,store.getCategories(new DepositCategory()).size());
  }

  @Test
  void addWithNonEmptyPayoutStrategy_shouldAdd() {
    assertEquals(defaultpay,store.getCategories(pay).size());
    store.addTransactionCategory(pay);
    assertNotEquals(defaultpay,store.getCategories(pay).size());
  }

  @Test
  void addWithEmptyPayoutStrategy_shouldNotAdd() {
    assertEquals(defaultpay,store.getCategories(new PayoutCategory()).size());
    store.addTransactionCategory(new PayoutCategory());
    assertEquals(defaultpay,store.getCategories(new PayoutCategory()).size());
  }

  @Test
  void addingExistingCategory_shouldNotChangeNumberOfCategories() {
    assertEquals(defaultsize,store.getCategories(null).size());
    assertEquals(defaultdepo,store.getCategories(new DepositCategory()).size());
    store.addTransactionCategory(new DepositCategory(supporteddepo));
    assertEquals(defaultsize,store.getCategories(null).size());
    assertEquals(defaultdepo,store.getCategories(new DepositCategory()).size());

    assertEquals(defaultsize,store.getCategories(null).size());
    assertEquals(defaultpay,store.getCategories(new PayoutCategory()).size());
    store.addTransactionCategory(new PayoutCategory(supportedpay));
    assertEquals(defaultsize,store.getCategories(null).size());
    assertEquals(defaultpay,store.getCategories(new PayoutCategory()).size());
  }


  @ParameterizedTest
  @ValueSource(strings = {supporteddepo,supportedpay})
  void categorySupportedOnDefaultCategory_shouldBeTrue(final String category) {
    assertTrue(store.categorySupported(category));
  }

  @Test
  void categorySupportedOnUnsupportedCategory_shouldBeFalse() {
    assertFalse(store.categorySupported(unsupported));
  }

  @Test
  void categorySupportedOnNewlyAddedCategory_shouldBeTrue() {
    store.addTransactionCategory(depo);
    assertTrue(store.categorySupported(dummydepocat));

    store.addTransactionCategory(pay);
    assertTrue(store.categorySupported(dummypaycat));
  }

  @Test
  void removeCategoryOnUnsupportedCategory_shouldNotChangeNumberOfCategories() {
    store.removeCategory(unsupported);

    assertEquals(defaultsize,store.getCategories(null).size());
    assertEquals(defaultdepo,store.getCategories(new DepositCategory()).size());
    assertEquals(defaultpay,store.getCategories(new PayoutCategory()).size());
  }

  @Test
  void removeCategoryOnDefaultCategory_shouldRemove() {
    store.removeCategory(supporteddepo);

    assertNotEquals(defaultsize,store.getCategories(null).size());
    assertNotEquals(defaultdepo,store.getCategories(new DepositCategory()).size());
    assertEquals(defaultpay,store.getCategories(new PayoutCategory()).size());

    store.removeCategory(supportedpay);

    assertNotEquals(defaultsize,store.getCategories(null).size());
    assertNotEquals(defaultdepo,store.getCategories(new DepositCategory()).size());
    assertNotEquals(defaultpay,store.getCategories(new PayoutCategory()).size());
  }

  @Test
  void beforeAddedAndAfterRemoved_shouldHaveSameSize() {
    int before = store.getCategories(null).size();

    store.addTransactionCategory(depo);
    assertNotEquals(before,store.getCategories(null).size());

    store.removeCategory(dummydepocat);
    assertEquals(before,store.getCategories(null).size());
  }

  @Test
  void keyOfUnsupportedCategory_shouldBeEmpty() {
    assertTrue(store.keyOfCategory(null).isEmpty());
    assertTrue(store.keyOfCategory(unsupported).isEmpty());
  }
  @Test
  void keyOfSupportedCategory_shouldBePresentAndNotEmpty() {
    assertFalse(store.keyOfCategory(supporteddepo).isEmpty());
    assertFalse(store.keyOfCategory(supportedpay).isEmpty());
  }
  @Test
  void keyOfCategory_shouldBeEqual() {
    assertEquals(DepositCategory.CATEGORY,store.keyOfCategory(supporteddepo));
    assertEquals(PayoutCategory.CATEGORY,store.keyOfCategory(supportedpay));
  }
}








