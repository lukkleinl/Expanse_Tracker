package transactions.grouping.byTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Transaction;
import transactions.grouping.GroupingTestUser;
import transactions.grouping.TransactionOrganizing;
import user.User;

class UserDefinedTests {
  private static final ThreadLocalRandom rand = ThreadLocalRandom.current();
  private static final int rounds = 5;
  private static User user;
  private ZonedDateTime begin;
  private ZonedDateTime end;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    user = GroupingTestUser.newTestUser();

    for (int i = 0; i < (rounds * user.getCategories(null).toArray().length); i++) {
      for (int j = 0; j < rounds; j++) {
        user.applyAndSaveTransaction(GroupingTestUser.transactionWithTimeByIandJ(i, j),
            GroupingTestUser.randomAccount());
      }
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
    ZonedDateTime now = ZonedDateTime.now();
    Month month = Month.of(rand.nextInt(1, 13));
    int borderyear = 1970 + ((now.getYear() - 1970) / rand.nextInt(2, now.getYear() - 1970));

    this.begin = ZonedDateTime.of(rand.nextInt(1970, borderyear), month.getValue(),
        rand.nextInt(1, month.maxLength()), 0, 0, 0, 0, ZoneId.of("UTC"));

    this.end = ZonedDateTime.of(rand.nextInt(borderyear, now.getYear()), month.getValue(),
        rand.nextInt(1, month.maxLength()), 23, 59, 59, 0, ZoneId.of("UTC"));
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


