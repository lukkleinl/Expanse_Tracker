package user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transactions.Transaction;
import transactions.TransactionCreator;
import transactions.categories.CategoryStore;

class TransactionStoreTests {
  private TransactionStore store;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    store = new TransactionStore();
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void newStore_shouldHaveNoMappings() {
    assertEquals(0,store.accountsWithTransactions());
  }

  @Test
  void addTransactionUnderKey_shouldStoreTransaction() {
    assertEquals(0,store.accountsWithTransactions());
    store.addTransactionUnderKey(1, TransactionCreator.newTransaction("FOOD", 50f, "", new CategoryStore().withDefaultCategories()));
    assertEquals(1,store.accountsWithTransactions());
  }

  @Test
  void addingSameTransactionForSameAccountMoreThanOnce_shouldNotAddMoreThanOnce() {
    Transaction trans = TransactionCreator.newTransaction("FOOD", 50f, "", new CategoryStore().withDefaultCategories());
    assertEquals(0,store.accountsWithTransactions());
    store.addTransactionUnderKey(1, trans);
    assertEquals(1,store.accountsWithTransactions());
    store.addTransactionUnderKey(1, trans);
    assertEquals(1,store.accountsWithTransactions());
  }

  @Test
  void addingSameTransactionForDifferentAccountsMoreThanOnce_shouldNotAddMoreThanOnce() {
    Transaction trans = TransactionCreator.newTransaction("FOOD", 50f, "", new CategoryStore().withDefaultCategories());
    assertEquals(0,store.accountsWithTransactions());
    store.addTransactionUnderKey(10, trans);
    assertEquals(1,store.accountsWithTransactions());
    store.addTransactionUnderKey(200, trans);
    assertEquals(1,store.accountsWithTransactions());
  }

}



