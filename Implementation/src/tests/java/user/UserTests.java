package user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import accounts.Account;
import accounts.Cash;
import exceptions.SWE_Exception;
import transactions.TransactionCreator;

/**
 *This class combines
 */
class UserTests {
  private User user;
  private final float limit = -100f;
  private final float ok_amount = -limit/2;
  private final float not_ok_amount = -2*limit;
  private final Account acc = new Cash("Cash", limit, "Euro");

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    user = new User(1234,"FirstName","Lastname","password");
    user.getCategoryStore().withDefaultCategories();
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void newUser_shouldNotHaveAccounts() {
    assertEquals(0,user.getAccounts().size());
  }

  @Test
  void addAccount_shouldAdd() {
    user.addAccount(acc);
    assertEquals(1,user.getAccounts().size());
  }

  @Test
  void addedAccount_shouldBeEqual() {
    user.addAccount(acc);
    assertEquals(acc,user.getAccounts().getIterator().next());
  }

  /* ---------- only testing the adding since other functionality is tested seperately ---------- */
  @Test
  void applyAndSaveTransaction_shouldChangeBalanceAndStoreTransaction() throws SWE_Exception {
    user.addAccount(acc);
    assertEquals(0,user.getTransactionStore().accountsWithTransactions());
    user.applyAndSaveTransaction(TransactionCreator.newTransactionWith("FOOD", ok_amount, "", user.getCategoryStore()), acc);
    assertEquals(1,user.getTransactionStore().accountsWithTransactions());
  }

  @Test
  void applyAndSaveTransactionWithTooLargePayout_shouldThrowException() throws SWE_Exception {
    user.addAccount(acc);
    assertThrows(SWE_Exception.class, () -> {
      user.applyAndSaveTransaction(TransactionCreator.newTransactionWith("FOOD", not_ok_amount, "", user.getCategoryStore()), acc);
    });
  }
}






