package transactions.grouping.byTime;

import static org.junit.jupiter.api.Assertions.*;
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

class DailyTests {
  private static final int rounds = 5;
  private static User user;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    user = GroupingTestUser.newTestUser();

    for (int i = 0; i < rounds * user.getCategories(null).toArray().length; i++) {
      for (int j = 0; j < rounds; j++) {
        user.applyAndSaveTransaction(GroupingTestUser.transactionWithTimeByIandJ(i,j), GroupingTestUser.randomAccount());
      }
    }
  }

  @ParameterizedTest
  @MethodSource("transactions.grouping.GroupingTestUser#decorationExamples")
  void afterOrganizing_transactionsShouldBeInCorrectContainer(final TransactionOrganizing orga) {
    Map<String, CustomContainer<Transaction>> afterOrganizing = new Daily(orga).organize();

    for (String key : afterOrganizing.keySet()) {
      for (CustomIterator<Transaction> iter = afterOrganizing.get(key).getIterator(); iter.hasNext(); iter.next()) {
        assertTrue(key.contains(Daily.mappingborder + iter.element().getCreationDate().getDayOfMonth() + Daily.mappingborder));
      }
    }
  }
}




