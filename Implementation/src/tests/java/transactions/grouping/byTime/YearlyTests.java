package transactions.grouping.byTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Transaction;
import transactions.grouping.GroupingTestUser;
import transactions.grouping.TransactionOrganizing;
import user.User;

class YearlyTests {
  private static final int accounts = 2;
  private static final int transactions = 1000;
  private static User user;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    user = GroupingTestUser.newTestUserWith(accounts);

    for (int i = 0; i < transactions; i++) {
      user.applyAndSaveTransaction(GroupingTestUser.newTransaction(i),
          GroupingTestUser.randomAccount());
    }
  }

  @ParameterizedTest
  @MethodSource("transactions.grouping.GroupingTestUser#decorationExamples")
  void afterOrganizing_transactionsShouldBeInCorrectContainer(final TransactionOrganizing orga) {
    Map<String, CustomContainer<Transaction>> afterOrganizing = new Yearly(orga).organize();

    for (String key : afterOrganizing.keySet()) {
      for (CustomIterator<Transaction> iter = afterOrganizing.get(key).getIterator(); iter
          .hasNext(); iter.next()) {
        assertTrue(key.contains(keyCreation(iter.element())));
      }
    }
  }

  private String keyCreation(final Transaction trans) {
    StringBuilder sb = new StringBuilder();
    sb.append(Yearly.mappingborder);
    sb.append(trans.getCreationDate().getYear());
    sb.append(Yearly.mappingborder);
    return sb.toString();
  }
}


