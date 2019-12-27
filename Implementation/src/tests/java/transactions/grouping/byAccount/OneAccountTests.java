package transactions.grouping.byAccount;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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
  private static final ThreadLocalRandom rand = ThreadLocalRandom.current();
  private static final int rounds = 5;
  private static User user;
  private static Map<String,CustomContainer<Transaction>> storedtrans;
  private static Map<String,CustomContainer<Transaction>> afterOrganizing;
  private static String[] categories;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    user = new User(1234, "firstname", "lastname", "password");
    user.getCategoryStore().withDefaultCategories();
    addUserAccounts(); // modify this method to add more accounts

    categories = user.getCategories(null).toArray(new String[0]);

    storedtrans = new HashMap<>();
    afterOrganizing = new HashMap<>();
    Transaction trans = null;
    Account acc = null;

    for (int i = 0; i < (rounds * categories.length); i++) {
      acc = randomAccount();
      trans = randomTransaction(i);

      user.applyAndSaveTransaction(trans, acc);
      for (Integer key : user.getTransactionStore().getTransactions().keySet()) {
        storedtrans.putIfAbsent(Integer.toString(key), new CustomList<>());
      }
      storedtrans.get(keyOfTransaction(trans)).add(trans);
    }
  }

  /* ---------------------------------------- Tests ---------------------------------------- */

  @ParameterizedTest
  @MethodSource("userAccountsAsList")
  void afterOrganizing_shouldBeSameNumberOfTransactions(final Account acc) throws Exception {
    afterOrganizing = new OneAccount(user, acc.getAccount_number()).organize();

    int sum_storedtrans = 0;
    int sum_organized = 0;
    for (String key : afterOrganizing.keySet()) {
      if (mappingsPresent(key)) {
        sum_organized += afterOrganizing.get(key).size();
        sum_storedtrans += afterOrganizing.get(key).size();
      }
    }
    assertEquals(sum_storedtrans, sum_organized);
  }

  @ParameterizedTest
  @MethodSource("userAccountsAsList")
  void afterOrganizing_shouldBeSameTransactions(final Account acc) throws Exception {
    afterOrganizing = new OneAccount(user, acc.getAccount_number()).organize();

    for (String key : afterOrganizing.keySet()) {
      if (mappingsPresent(key)) {
        CustomIterator<Transaction> iter = afterOrganizing.get(key).getIterator();
        CustomIterator<Transaction> it = storedtrans.get(key).getIterator();

        while (iter.hasNext()) {
          assertTrue(iter.next().equals(it.next()));
        }
      }
    }
  }

  /* ------------------------------ Modify this method to add more accounts ------------------------------ */
  private static void addUserAccounts() {
    user.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user.addAccount(
        new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE, "AT121200001203250544"));
  }

  /* ------------------------------ Provide data for parameterized tests ------------------------------ */
  @SuppressWarnings("unused")
  private static List<Account> userAccountsAsList() {
    List<Account> list = new ArrayList<>();
    CustomIterator<Account> iter = user.getAccounts().getIterator();

    while (iter.hasNext()) {
      list.add(iter.next());
    }

    return list;
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
  private static String keyOfTransaction(final Transaction trans) {
    for (Entry<Integer, CustomContainer<Transaction>> entry : user.getTransactionStore().getTransactions().entrySet()) {
      for (CustomIterator<Transaction> it = entry.getValue().getIterator(); it.hasNext(); ) {
        if (it.next().equals(trans))
          return Integer.toString(entry.getKey());
      }
    }
    return null;
  }
  private static Transaction randomTransaction(final int i) throws Exception {
    Thread.sleep(10);
    return TransactionCreator.newTransaction(categories[i % rounds], i * 100, "", user.getCategoryStore());
  }
  private static boolean mappingsPresent(final String key) {
    return (afterOrganizing.get(key) != null) && (storedtrans.get(key) != null);
  }
}







