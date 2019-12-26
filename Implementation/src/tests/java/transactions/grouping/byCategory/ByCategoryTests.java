package transactions.grouping.byCategory;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import accounts.Account;
import accounts.Cash;
import accounts.DebitCard;
import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Transaction;
import transactions.TransactionCreator;
import transactions.grouping.TransactionOrganizing;
import transactions.grouping.byAccount.AllAccounts;
import transactions.grouping.byAccount.OneAccount;
import user.User;

class ByCategoryTests {
  private static final ThreadLocalRandom rand = ThreadLocalRandom.current();
  private static final int rounds = 5;
  private static User user;
  private static String[] categories;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    user = new User(1234, "firstname", "lastname", "password");
    user.getCategoryStore().withDefaultCategories();
    addUserAccounts(); // modify this method to add more accounts

    categories = user.getCategories(null).toArray(new String[0]);

    for (int i = 0; i < (rounds * categories.length); i++) {
      user.applyAndSaveTransaction(randomTransaction(i), randomAccount());
    }
  }

  @ParameterizedTest
  @MethodSource("decorationExamples")
  void afterOrganizing_transactionsShouldBeInCorrectContainer(final TransactionOrganizing orga) {
    Map<String, CustomContainer<Transaction>> afterOrganizing = new ByCategory(orga).organize();

    CustomIterator<Transaction> iter = null;

    for (String key : afterOrganizing.keySet()) {
      iter = afterOrganizing.get(key).getIterator();
      while (iter.hasNext()) {
        assertTrue(key.contains(iter.next().getCategory()));
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
  private static List<TransactionOrganizing> decorationExamples() {
    List<TransactionOrganizing> sampleData = new ArrayList<>();
    CustomIterator<Account> iter = user.getAccounts().getIterator();

    while (iter.hasNext()) {
      sampleData.add(new OneAccount(user, iter.next().getAccount_number()));
    }
    sampleData.add(new AllAccounts(user));

    return sampleData;
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


