package transactions.grouping.byAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import accounts.Account;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.Transaction;
import transactions.grouping.GroupingTestUser;
import user.User;

class OneAccountTests {
  private static final int accounts = 2;
  private static final int transactions = 1000;
  private static User user;
  private static Map<String, CustomContainer<Transaction>> storedtrans;
  private static Map<String, CustomContainer<Transaction>> afterOrganizing;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    user = GroupingTestUser.newTestUserWith(accounts);

    storedtrans = new HashMap<>();
    afterOrganizing = new HashMap<>();
    Transaction trans = null;
    Account acc = null;

    for (int i = 0; i < transactions; i++) {
      acc = GroupingTestUser.randomAccount();
      trans = GroupingTestUser.newTransaction(i);

      user.applyAndSaveTransaction(trans, acc);
      for (Integer key : user.getTransactionStore().getTransactions().keySet()) {
        storedtrans.putIfAbsent(Integer.toString(key), new CustomList<>());
      }
      storedtrans.get(keyOfTransaction(trans)).add(trans);
    }
  }

  /* ---------------------------------------- Tests ---------------------------------------- */
  @ParameterizedTest
  @MethodSource("transactions.grouping.GroupingTestUser#userAccountsAsList")
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
  @MethodSource("transactions.grouping.GroupingTestUser#userAccountsAsList")
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

  /*
   * ------------------------------ Helper Methods to keep tests shorter
   * ------------------------------
   */
  private static String keyOfTransaction(final Transaction trans) {
    for (Entry<Integer, CustomContainer<Transaction>> entry : user.getTransactionStore()
        .getTransactions().entrySet()) {
      for (CustomIterator<Transaction> it = entry.getValue().getIterator(); it.hasNext();) {
        if (it.next().equals(trans))
          return Integer.toString(entry.getKey());
      }
    }
    return null;
  }

  private static boolean mappingsPresent(final String key) {
    return (afterOrganizing.get(key) != null) && (storedtrans.get(key) != null);
  }
}


