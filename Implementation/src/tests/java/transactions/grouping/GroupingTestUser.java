package transactions.grouping;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import iteration.CustomIterator;
import transactions.Transaction;
import transactions.TransactionCreator;
import user.User;

/**
 * This class is used to avoid code duplication for helper methods in the grouping test classes.
 *
 * @author Michael Watholowitsch
 */
public class GroupingTestUser {
  private static final ThreadLocalRandom rand = ThreadLocalRandom.current();
  private static AtomicInteger trans_id = new AtomicInteger();
  private static User user = null;
  private static String[] categories;
  private static final ChronologicTimes times = new ChronologicTimes();

  public static User newTestUserWith(final int accounts) {
    user = new User(1234, "firstname", "lastname", "password");
    user.getCategoryStore().withDefaultCategories();

    int newAcc = accounts >= 4 ? 4 : accounts;
    newAcc = accounts <= 0 ? 1 : accounts;

    switch (newAcc) {
      case 4:
        user.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
      case 3:
        user.addAccount(new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE,
            "AT121200001203250544"));
      case 2:
        user.addAccount(
            new CreditCard("MasterCard", "Austria", Integer.MIN_VALUE, new Date(2022, 1, 1)));
      case 1:
        user.addAccount(new Stocks("Amazon Stocks", new Date(2013, 2, 5), Integer.MIN_VALUE));
    }

    categories = user.getCategories(null).toArray(new String[0]);

    return user;
  }

  /*
   * ------------------------------ Provide data for parameterized tests
   * ------------------------------
   */
  public static List<TransactionOrganizing> decorationExamples() {
    GroupingBuilder grouper = new GroupingBuilder();
    List<TransactionOrganizing> sampleData = new ArrayList<>();
    CustomIterator<Account> iter = user.getAccounts().getIterator();
    boolean first = true;

    while (iter.hasNext()) {
      if (first) {
        grouper.allAccs(user);
        first = false;
      } else {
        grouper.oneAcc(user, iter.next().getAccount_number());
      }

      sampleData.add(grouper.getDecorationAndResetToRoot());


      sampleData.add(grouper.category().getDecorationAndResetToRoot());

      sampleData.add(grouper.category().daily().getDecorationAndResetToRoot());
      sampleData.add(grouper.category().daily().userdefined(times.begin(), times.end())
          .getDecorationAndResetToRoot());
      sampleData.add(grouper.category().daily().monthly().getDecorationAndResetToRoot());
      sampleData.add(grouper.category().daily().monthly().userdefined(times.begin(), times.end())
          .getDecorationAndResetToRoot());
      sampleData.add(grouper.category().daily().monthly().yearly().getDecorationAndResetToRoot());
      sampleData.add(grouper.category().daily().monthly().yearly()
          .userdefined(times.begin(), times.end()).getDecorationAndResetToRoot());

      sampleData.add(grouper.category().monthly().getDecorationAndResetToRoot());
      sampleData.add(grouper.category().monthly().userdefined(times.begin(), times.end())
          .getDecorationAndResetToRoot());
      sampleData.add(grouper.category().monthly().yearly().getDecorationAndResetToRoot());
      sampleData.add(grouper.category().monthly().yearly().userdefined(times.begin(), times.end())
          .getDecorationAndResetToRoot());

      sampleData.add(grouper.category().yearly().getDecorationAndResetToRoot());
      sampleData.add(grouper.category().yearly().userdefined(times.begin(), times.end())
          .getDecorationAndResetToRoot());


      sampleData.add(grouper.yearly().category().getDecorationAndResetToRoot());
      sampleData.add(grouper.yearly().category().userdefined(times.begin(), times.end())
          .getDecorationAndResetToRoot());
      sampleData.add(grouper.yearly().userdefined(times.begin(), times.end()).category()
          .getDecorationAndResetToRoot());


      sampleData.add(
          grouper.category().userdefined(times.begin(), times.end()).getDecorationAndResetToRoot());
      sampleData.add(
          grouper.userdefined(times.begin(), times.end()).category().getDecorationAndResetToRoot());
    }

    return sampleData;
  }

  public static List<Account> userAccountsAsList() {
    List<Account> list = new ArrayList<>();
    CustomIterator<Account> iter = user.getAccounts().getIterator();

    while (iter.hasNext()) {
      list.add(iter.next());
    }

    return list;
  }

  /*
   * ------------------------------ Helper Methods to keep tests simpler
   * ------------------------------
   */
  public static Account randomAccount() {
    CustomIterator<Account> iter = user.getAccounts().getIterator();
    Account acc = null;

    for (int j = 0; j < (rand.nextInt(user.getAccounts().size()) + 1); j++) {
      acc = iter.next();
    }
    return acc;
  }

  public static Transaction newTransaction(final int i) {
    return TransactionCreator.transactionFromDatabaseData(times.nextTime(),
        categories[i % categories.length], 0.0f, "ID_" + trans_id.incrementAndGet(),
        user.getCategoryStore(), trans_id.get());
  }

  public static class ChronologicTimes {
    private static final ZonedDateTime earliest =
        ZonedDateTime.of(1970, 1, 1, 0, 15, 00, 0, ZoneId.of("UTC"));
    private static AtomicInteger shifts = new AtomicInteger(1);

    public ChronologicTimes() {}

    public ZonedDateTime nextTime() {
      return earliest.plusHours(shifts.incrementAndGet() * 4);
    }

    public ZonedDateTime earliest() {
      return earliest;
    }

    public ZonedDateTime begin() {
      return ZonedDateTime.of(1973, 1, 1, 0, 15, 00, 0, ZoneId.of("UTC"))
          .plusHours(shifts.get() * 4);
    }

    public ZonedDateTime end() {
      return ZonedDateTime.of(1979, 1, 1, 0, 15, 00, 0, ZoneId.of("UTC"))
          .plusHours(shifts.get() * 4);
    }
  }
}


