package transactions.grouping.byAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.BeforeAll;
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

class AllAccountsTests {
  private static final ThreadLocalRandom rand = ThreadLocalRandom.current();
  private static final int rounds = 5;
  private static User user;
  private static CustomContainer<Transaction> storedtrans;
  private static Map<String, CustomContainer<Transaction>> afterOrganizing;
  private static String[] categories;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    user = new User(1234, "firstname", "lastname", "password");
    user.getCategoryStore().withDefaultCategories();
    addUserAccounts(); // modify this method to add more accounts

    categories = user.getCategories(null).toArray(new String[0]);

    storedtrans = new CustomList<>();
    afterOrganizing = new HashMap<>();
    Transaction trans = null;
    Account acc = null;

    for (int i = 0; i < (rounds * categories.length); i++) {
      acc = randomAccount();
      trans = randomTransaction(i);

      user.applyAndSaveTransaction(trans, acc);
      storedtrans.add(trans);
    }
  }

  /* ---------------------------------------- Tests ---------------------------------------- */
  @Test
  void afterOrganizing_shouldBeSameNumberOfTransactions() {
    afterOrganizing = new AllAccounts(user).organize();

    int transcount = 0;
    for (String key : afterOrganizing.keySet()) {
      transcount += afterOrganizing.get(key).size();
    }
    assertEquals(storedtrans.size(), transcount);
  }

  @Test
  void afterOrganizing_shouldBeSameTransactions() {
    afterOrganizing = new AllAccounts(user).organize();

    CustomIterator<Transaction> it = storedtrans.getIterator();
    for (String key : afterOrganizing.keySet()) {
      CustomIterator<Transaction> iter = afterOrganizing.get(key).getIterator();

      while (iter.hasNext()) {
        assertTrue(iter.next().equals(it.next()));
      }
    }
  }

  /* ------------------------------ Modify this method to add more accounts ------------------------------ */
  private static void addUserAccounts() {
    user.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user.addAccount(
        new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE, "AT121200001203250544"));
  }

  /* ------------------------------ Helper Methods to keep tests shorter ------------------------------ */
  private static Account randomAccount() {
    CustomIterator<Account> iter = user.getAccounts().getIterator();
    Account acc = null;

    for (int j = 0; j < rand.nextInt(user.getAccounts().size())+1; j++) {
      acc = iter.next();
    }
    return acc;
  }
  private static Transaction randomTransaction(final int i) throws Exception {
    Thread.sleep(10);
    return TransactionCreator.newTransaction(categories[i % rounds], i * 100, "", user.getCategoryStore());
  }
}


