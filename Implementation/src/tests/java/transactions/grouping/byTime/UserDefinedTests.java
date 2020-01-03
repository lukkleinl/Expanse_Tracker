package transactions.grouping.byTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.ZonedDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Transaction;
import transactions.grouping.GroupingTestUser;
import transactions.grouping.GroupingTestUser.ChronologicTimes;
import transactions.grouping.TransactionOrganizing;
import user.User;

class UserDefinedTests {
  private static final int accounts = 2;
  private static final int transactions = 1000;
  private static User user;
  private ZonedDateTime begin;
  private ZonedDateTime end;

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
    this.timeCreation();
    Map<String, CustomContainer<Transaction>> afterOrganizing =
        new UserDefined(orga, this.begin, this.end).organize();

    for (String key : afterOrganizing.keySet()) {
      for (CustomIterator<Transaction> iter = afterOrganizing.get(key).getIterator(); iter
          .hasNext(); iter.next()) {
        assertTrue(key.contains(keyCreation(iter.element())));
        assertTrue(iter.element().getCreationDate().isAfter(this.begin));
        assertTrue(iter.element().getCreationDate().isBefore(this.end));
      }
    }
  }

  // Helper method
  private void timeCreation() {
    GroupingTestUser.ChronologicTimes times = new ChronologicTimes();
    this.begin = times.begin();
    this.end = times.end();
  }

  private String keyCreation(final Transaction trans) {
    StringBuilder sb = new StringBuilder();
    sb.append(UserDefined.mappingborder);
    sb.append(this.begin.getYear() + "-");
    if (this.begin.getMonthValue() < 10) {
      sb.append("0");
    }
    sb.append(this.begin.getMonthValue() + "-");
    if (this.begin.getDayOfMonth() < 10) {
      sb.append("0");
    }
    sb.append(this.begin.getDayOfMonth());
    sb.append("-to-");
    sb.append(this.end.getYear() + "-");
    if (this.end.getMonthValue() < 10) {
      sb.append("0");
    }
    sb.append(this.end.getMonthValue() + "-");
    if (this.end.getDayOfMonth() < 10) {
      sb.append("0");
    }
    sb.append(this.end.getDayOfMonth());
    sb.append(UserDefined.mappingborder);
    return sb.toString();
  }
}


