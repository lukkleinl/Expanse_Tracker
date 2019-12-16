package transactioncategory_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reworked.transactions.categories.CategoryStore;
import reworked.transactions.categories.DepositCategory;
import reworked.transactions.categories.PayoutCategory;
import reworked.transactions.categories.TransactionCategoryFunctionality;

class CategoryStoreTests {
  private int defaultsize = 5;
  private int defaultdepo = 2;
  private int defaultpay = 3;
  private final String defaultdepocat = "SALARY";
  private final String defaultpaycat = "FOOD";
  private final String dummydepocat = "DummyDepo";
  private final String dummypaycat = "DummyPay";
  private CategoryStore store;
  private TransactionCategoryFunctionality depo;
  private TransactionCategoryFunctionality pay;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    store = new CategoryStore();
    depo = new DepositCategory(dummydepocat);
    pay = new PayoutCategory(dummypaycat);
    defaultsize = store.getCategories(null).size();
    defaultdepo = store.getCategories(depo).size();
    defaultpay = store.getCategories(pay).size();
  }

  @AfterEach
  void tearDown() throws Exception {}

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
    store.addTransactionCategory(new DepositCategory(defaultdepocat));
    assertEquals(defaultsize,store.getCategories(null).size());
    assertEquals(defaultdepo,store.getCategories(new DepositCategory()).size());

    assertEquals(defaultsize,store.getCategories(null).size());
    assertEquals(defaultpay,store.getCategories(new PayoutCategory()).size());
    store.addTransactionCategory(new PayoutCategory(defaultpaycat));
    assertEquals(defaultsize,store.getCategories(null).size());
    assertEquals(defaultpay,store.getCategories(new PayoutCategory()).size());
  }


  @ParameterizedTest
  @ValueSource(strings = {defaultdepocat,defaultpaycat})
  void categorySupportedOnDefaultCategory_shouldBeTrue(final String category) {
    assertTrue(store.categorySupported(category));
  }

  @ParameterizedTest
  @ValueSource(strings = {dummydepocat,dummypaycat})
  void categorySupportedOnUnsupportedCategory_shouldBeFalse(final String category) {
    assertFalse(store.categorySupported(category));
  }

  @Test
  void categorySupportedOnNewlyAddedCategory_shouldBeTrue() {
    store.addTransactionCategory(depo);
    assertTrue(store.categorySupported(dummydepocat));

    store.addTransactionCategory(pay);
    assertTrue(store.categorySupported(dummypaycat));
  }

  @ParameterizedTest
  @ValueSource(strings = {dummydepocat,dummypaycat})
  void removeCategoryOnUnsupportedCategory_shouldNotChangeNumberOfCategories(final String category) {
    store.removeCategory(category);

    assertEquals(defaultsize,store.getCategories(null).size());
    assertEquals(defaultdepo,store.getCategories(new DepositCategory()).size());
    assertEquals(defaultpay,store.getCategories(new PayoutCategory()).size());
  }

  @Test
  void removeCategoryOnDefaultCategory_shouldRemove() {
    store.removeCategory(defaultdepocat);

    assertNotEquals(defaultsize,store.getCategories(null).size());
    assertNotEquals(defaultdepo,store.getCategories(new DepositCategory()).size());
    assertEquals(defaultpay,store.getCategories(new PayoutCategory()).size());

    store.removeCategory(defaultpaycat);

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
}







