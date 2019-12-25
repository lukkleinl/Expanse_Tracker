package transactions.grouping.byCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
  private static User user;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    user = new User(1234, "firstname", "lastname", "password");
    user.getCategoryStore().withDefaultCategories();
    Account testAcc_one = new Cash("Wallet", Integer.MIN_VALUE, "Euro");
    Account testAcc_two =
        new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE, "AT121200001203250544");
    user.addAccount(testAcc_one);
    user.addAccount(testAcc_two);

    try {
      String[] categories = user.getCategories(null).toArray(new String[0]);

      final int rounds = 5;

      for (int i = 0; i < (rounds * categories.length); i++) {
        Transaction trans_testAcc_one = TransactionCreator
            .newTransactionWith(categories[i % rounds], i * 100, "", user.getCategoryStore());
        user.applyAndSaveTransaction(trans_testAcc_one, testAcc_one);

        Thread.sleep(10);

        Transaction trans_testAcc_two = TransactionCreator.newTransactionWith(
            categories[categories.length - 1 - (i % rounds)], i * 200, "", user.getCategoryStore());
        user.applyAndSaveTransaction(trans_testAcc_two, testAcc_two);

        Thread.sleep(10);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @ParameterizedTest
  @MethodSource("decorationExamples")
  void afterOrganizing_shouldBeSameTransactions(final TransactionOrganizing orga) {
    Map<String, CustomContainer<Transaction>> afterOrganizing = new ByCategory(orga).organize();

    CustomIterator<Transaction> iter = null;

    for (String category : afterOrganizing.keySet()) {
      iter = afterOrganizing.get(category).getIterator();
      while (iter.hasNext()) {
        assertEquals(category, iter.next().getCategory());
      }
    }
  }

  // add more test cases here
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
}


