package transactions.grouping.byAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

  @ParameterizedTest
  @MethodSource("userAccounts")
  void afterOrganizing_shouldBeSameTransactions(final Account acc) {
    Map<String, CustomContainer<Transaction>> afterOrganizing =
        new OneAccount(user, acc.getAccount_number()).organize();

    int sum = 0;
    for (String key : afterOrganizing.keySet()) {
      // TODO - size() throws NullPointerException for the 2nd account
      sum += afterOrganizing.get(key).size();
    }
    assertEquals(storedtrans.size(), sum);

    for (String key : afterOrganizing.keySet()) {
      // TODO - getIterator() throws NullPointerException for the 2nd account
      CustomIterator<Transaction> iter = afterOrganizing.get(key).getIterator();
      CustomIterator<Transaction> it = storedtrans.getIterator();

      while (iter.hasNext()) {
        assertTrue(iter.next().equals(it.next()));
      }
    }
  }

  static List<Account> userAccounts() {
    List<Account> list = new ArrayList<>();
    CustomIterator<Account> iter = user.getAccounts().getIterator();

    while (iter.hasNext()) {
      list.add(iter.next());
    }

    return list;
  }
}


