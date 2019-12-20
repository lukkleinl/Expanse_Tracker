package transactions.grouping.byCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import accounts.Account;
import accounts.Cash;
import accounts.DebitCard;
import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Transaction;
import transactions.TransactionCreator;
import transactions.grouping.byAccount.AllAccounts;
import user.User;

class ByCategoryTests {
  private static final int limit = Integer.MIN_VALUE;
  private static User user;
  private static Account cash;
  private static Account debit;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    user = new User(1234, "firstname", "lastname", "password");
    user.getCategoryStore().withDefaultCategories();
    cash = new Cash("Wallet", limit, "Euro");
    debit = new DebitCard("Giro Account", "Bank Austria", limit, "AT121200001203250544");
    user.addAccount(cash);
    user.addAccount(debit);

    try {
      String[] categories = user.getCategories(null).toArray(new String[0]);

      final int rounds = 5;

      for (int i = 0; i < (rounds * categories.length); i++) {
        Transaction transcash = TransactionCreator.newTransactionWith(categories[i % rounds],
            i * 100, "", user.getCategoryStore());
        user.applyAndSaveTransaction(transcash, cash);

        Thread.sleep(10);

        Transaction transdebit = TransactionCreator.newTransactionWith(
            categories[categories.length - 1 - (i % rounds)], i * 200, "", user.getCategoryStore());
        user.applyAndSaveTransaction(transdebit, debit);

        Thread.sleep(10);

        // System.out.println("i: " + i + ", " + transcash);
        // System.out.println("i: " + i + ", " + transdebit);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void afterOrganizing_shouldBeSameTransactions() {
    Map<String, CustomContainer<Transaction>> afterOrganizing =
        new ByCategory(new AllAccounts(user)).organize();

    CustomIterator<Transaction> iter = null;
    for (String category : afterOrganizing.keySet()) {
      System.out.println("Category: " + category.toUpperCase());
      iter = afterOrganizing.get(category).getIterator();
      while (iter.hasNext()) {
        assertEquals(category, iter.next().getCategory());
      }
    }
  }
}


