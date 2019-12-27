package transactions.grouping.byTime;

import static org.junit.jupiter.api.Assertions.*;
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
  private String keystructure;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    user = GroupingTestUser.newTestUser();

    for (int i = 0; i < rounds * user.getCategories(null).toArray().length; i++) {
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
        new UserDefined(orga, begin, end).organize();

    for (String key : afterOrganizing.keySet()) {
      for (CustomIterator<Transaction> iter = afterOrganizing.get(key).getIterator(); iter
          .hasNext(); iter.next()) {
        assertTrue(key.contains(keystructure));
        assertTrue(iter.element().getCreationDate().isAfter(begin));
        assertTrue(iter.element().getCreationDate().isBefore(end));
      }
    }
  }

  // Helper method
  private void timeCreation() {
    ZonedDateTime now = ZonedDateTime.now();
    Month month = Month.of(rand.nextInt(1, 13));
    int borderyear = 1970 + ((now.getYear() - 1970) / rand.nextInt(2,now.getYear() - 1970));

    begin = ZonedDateTime.of(rand.nextInt(1970, borderyear),
        month.getValue(), rand.nextInt(1, month.maxLength()), 0, 0, 0, 0, ZoneId.of("UTC"));

    end = ZonedDateTime.of(rand.nextInt(borderyear, now.getYear()),
        month.getValue(), rand.nextInt(1, month.maxLength()), 23, 59, 59, 0, ZoneId.of("UTC"));

    keystructure = UserDefined.mappingborder + begin.getYear() + "-" + begin.getMonthValue() + "-"
        + begin.getDayOfMonth() + "-to-" + end.getYear() + "-" + end.getMonthValue() + "-"
        + end.getDayOfMonth() + UserDefined.mappingborder;
  }
}


