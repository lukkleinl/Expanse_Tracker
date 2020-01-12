package user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import accounts.Account;
import accounts.Cash;
import exceptions.CustomException;
import transactions.TransactionCreator;

/**
 * This class combines
 */
class UserTests {
  private User user;
  private final float limit = -100f;
  private final float ok_amount = -this.limit / 2;
  private final float not_ok_amount = -2 * this.limit;
  private final Account acc = new Cash("Cash", this.limit, "Euro");

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    this.user = new User("1234", "FirstName", "Lastname", "password");
    this.user.getCategoryStore().withDefaultCategories();
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void newUser_shouldNotHaveAccounts() {
    assertEquals(0, this.user.getAccounts().size());
  }

  @Test
  void addAccount_shouldAdd() {
    this.user.addAccount(this.acc);
    assertEquals(1, this.user.getAccounts().size());
  }

  @Test
  void addedAccount_shouldBeEqual() {
    this.user.addAccount(this.acc);
    assertEquals(this.acc, this.user.getAccounts().getIterator().element());
    assertEquals(this.acc, this.user.getAccounts().getIterator().next());
  }

  @Test
  void applyAndSaveTransaction_shouldChangeBalanceAndStoreTransaction() throws CustomException {
    this.user.addAccount(this.acc);
    assertEquals(0, this.user.getTransactionStore().accountsWithTransactions());
    this.user.applyAndSaveTransaction(
        TransactionCreator.newTransaction("FOOD", this.ok_amount, "", this.user.getCategoryStore()),
        this.acc);
    assertEquals(1, this.user.getTransactionStore().accountsWithTransactions());
  }
}


