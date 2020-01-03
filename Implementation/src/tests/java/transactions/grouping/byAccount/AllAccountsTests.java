package transactions.grouping.byAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import accounts.Account;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.Transaction;
import transactions.grouping.GroupingTestUser;
import user.User;

class AllAccountsTests {
  private static final int accounts = 2;
  private static final int transactions = 1000;
  private static User user;
  private static CustomContainer<Transaction> storedtrans;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    user = GroupingTestUser.newTestUserWith(accounts);

    storedtrans = new CustomList<>();
    Transaction trans = null;
    Account acc = null;

    for (int i = 0; i < transactions; i++) {
      acc = GroupingTestUser.randomAccount();
      trans = GroupingTestUser.newTransaction(i);

      user.applyAndSaveTransaction(trans, acc);
      storedtrans.add(trans);
    }
  }

  /* ---------------------------------------- Tests ---------------------------------------- */
  @Test
  void afterOrganizing_shouldBeSameNumberOfTransactions() {
    Map<String, CustomContainer<Transaction>> afterOrganizing = new AllAccounts(user).organize();

    int transcount = 0;
    for (String key : afterOrganizing.keySet()) {
      transcount += afterOrganizing.get(key).size();
    }
    assertEquals(storedtrans.size(), transcount);
  }

  @Test
  void afterOrganizing_shouldBeSameTransactions() {
    Map<String, CustomContainer<Transaction>> afterOrganizing = new AllAccounts(user).organize();

    CustomIterator<Transaction> it = storedtrans.getIterator();
    for (String key : afterOrganizing.keySet()) {
      CustomIterator<Transaction> iter = afterOrganizing.get(key).getIterator();

      while (iter.hasNext()) {
        assertTrue(iter.next().equals(it.next()));
      }
    }
  }
}


