package transactions.grouping.byAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
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

class AllAccountsTests {
  private static final int rounds = 5;
  private static User user;
  private static CustomContainer<Transaction> storedtrans;
  private static String[] categories;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    user = new User(1234, "firstname", "lastname", "password");
    user.getCategoryStore().withDefaultCategories();

    user.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user.addAccount(
        new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE, "AT121200001203250544"));

    categories = user.getCategories(null).toArray(new String[0]);
  }

  @BeforeEach
  void setUp() throws Exception {
    storedtrans = new CustomList<>();
    CustomIterator<Account> iter = null;

    int accpos = 0;

    for (int i = 0; i < (rounds * categories.length); i++) {
      iter = user.getAccounts().getIterator();
      accpos = ThreadLocalRandom.current().nextInt(user.getAccounts().size()) - 1;

      for (int j = 0; j < accpos; j++) {
        iter.next();
      }

      Transaction trans = TransactionCreator.newTransactionWith(categories[i % rounds], i * 100, "",
          user.getCategoryStore());

      user.applyAndSaveTransaction(trans, iter.next());
      storedtrans.add(trans);
    }
  }

  @Test
  void afterOrganizing_shouldBeSameTransactions() {
    Map<String, CustomContainer<Transaction>> afterOrganizing = new AllAccounts(user).organize();

    int transcount = 0;
    for (String key : afterOrganizing.keySet()) {
      // TODO - size() throws NoClassDefFoundError for some reason
      transcount += afterOrganizing.get(key).size();
    }
    assertEquals(storedtrans.size(), transcount);

    for (String key : afterOrganizing.keySet()) {
      // TODO - getIterator() throws NoClassDefFoundError for some reason
      CustomIterator<Transaction> iter = afterOrganizing.get(key).getIterator();
      CustomIterator<Transaction> it = storedtrans.getIterator();

      while (iter.hasNext()) {
        assertTrue(iter.next().equals(it.next()));
      }
    }
  }
}


