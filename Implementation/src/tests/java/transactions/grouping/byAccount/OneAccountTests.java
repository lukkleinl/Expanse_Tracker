package transactions.grouping.byAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import accounts.Account;
import accounts.Cash;
import accounts.DebitCard;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.Transaction;
import transactions.TransactionCreator;
import user.User;

class OneAccountTests {
  private final int limit = Integer.MIN_VALUE;
  private User user;
  private Account cash;
  private Account debit;
  private CustomContainer<Transaction> storedcash;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    user = new User(1234, "firstname", "lastname", "password");
    user.getCategoryStore().withDefaultCategories();
    cash = new Cash("Wallet", limit, "Euro");
    debit = new DebitCard("Giro Account", "Bank Austria", limit, "AT121200001203250544");
    user.addAccount(cash);
    user.addAccount(debit);

    storedcash = new CustomList<>();
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void afterOrganizing_shouldBeSameTransactions() {
    String[] categories = user.getCategories(null).toArray(new String[0]);

    final int rounds = 5;

    try {
      for (int i = 0; i < 5*categories.length; i++) {
        Transaction transcash = TransactionCreator.newTransactionWith(categories[i%rounds], i*100, "", user.getCategoryStore());
        Transaction transdebit = TransactionCreator.newTransactionWith(categories[categories.length - 1 - i%rounds], i*200, "", user.getCategoryStore());
        user.applyAndSaveTransaction(transcash, cash);
        storedcash.add(transcash);
        user.applyAndSaveTransaction(transdebit, debit);
        System.out.println("i: " + i + ",  " + transcash);
        System.out.println("i: " + i + ",  " + transdebit);
      }
    } catch (Exception e) {
      e.printStackTrace();
      assertThrows(Exception.class, () -> { });
    }

    CustomContainer<Transaction> afterOrganizing = new OneAccount(user.getTransactionStore(),cash.getAccount_number()).organize();

    assertEquals(storedcash.size(),afterOrganizing.size());

    CustomIterator<Transaction> iter = afterOrganizing.getIterator();
    CustomIterator<Transaction> it = storedcash.getIterator();

    while (iter.hasNext()) {
      assertTrue(iter.next().equals(it.next()));
    }
  }
}






