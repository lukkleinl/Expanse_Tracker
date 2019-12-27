package transactions.grouping;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import accounts.Account;
import accounts.Cash;
import accounts.DebitCard;
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

  public static User newTestUser() {
    user = new User(1234, "firstname", "lastname", "password");
    user.getCategoryStore().withDefaultCategories();

    user.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user.addAccount(
        new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE, "AT121200001203250544"));

    categories = user.getCategories(null).toArray(new String[0]);

    return user;
  }

  /* ------------------------------ Provide data for parameterized tests ------------------------------ */
  public static List<TransactionOrganizing> decorationExamples() {
    GroupingBuilder grouper = new GroupingBuilder();
    List<TransactionOrganizing> sampleData = new ArrayList<>();
    CustomIterator<Account> iter = user.getAccounts().getIterator();
    boolean first = true;

    while (iter.hasNext()) {
      if (first) {
        grouper.allAccs(user);
        first = false;
      }
      else {
        grouper.oneAcc(user,iter.next().getAccount_number());
      }

      sampleData.add(grouper.getDecorationAndResetToRoot());


      sampleData.add(grouper.category().getDecorationAndResetToRoot());

      sampleData.add(grouper.category().daily().getDecorationAndResetToRoot());
      sampleData.add(grouper.category().daily().userdefined(timeByIandJ(1,1),timeByIandJ(2,2)).getDecorationAndResetToRoot());
      sampleData.add(grouper.category().daily().monthly().getDecorationAndResetToRoot());
      sampleData.add(grouper.category().daily().monthly().userdefined(timeByIandJ(1,1),timeByIandJ(2,2)).getDecorationAndResetToRoot());
      sampleData.add(grouper.category().daily().monthly().yearly().getDecorationAndResetToRoot());
      sampleData.add(grouper.category().daily().monthly().yearly().userdefined(timeByIandJ(1,1),timeByIandJ(2,2)).getDecorationAndResetToRoot());

      sampleData.add(grouper.category().monthly().getDecorationAndResetToRoot());
      sampleData.add(grouper.category().monthly().userdefined(timeByIandJ(1,1),timeByIandJ(2,2)).getDecorationAndResetToRoot());
      sampleData.add(grouper.category().monthly().yearly().getDecorationAndResetToRoot());
      sampleData.add(grouper.category().monthly().yearly().userdefined(timeByIandJ(1,1),timeByIandJ(2,2)).getDecorationAndResetToRoot());

      sampleData.add(grouper.category().yearly().getDecorationAndResetToRoot());
      sampleData.add(grouper.category().yearly().userdefined(timeByIandJ(1,1),timeByIandJ(2,2)).getDecorationAndResetToRoot());


      sampleData.add(grouper.yearly().category().getDecorationAndResetToRoot());
      sampleData.add(grouper.yearly().category().userdefined(timeByIandJ(1,1),timeByIandJ(2,2)).getDecorationAndResetToRoot());
      sampleData.add(grouper.yearly().userdefined(timeByIandJ(1,1),timeByIandJ(2,2)).category().getDecorationAndResetToRoot());


      sampleData.add(grouper.category().userdefined(timeByIandJ(1,1),timeByIandJ(2,2)).getDecorationAndResetToRoot());
      sampleData.add(grouper.userdefined(timeByIandJ(1,1),timeByIandJ(2,2)).category().getDecorationAndResetToRoot());
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

  /* ------------------------------ Helper Methods to keep tests simpler ------------------------------ */
  public static Account randomAccount() {
    CustomIterator<Account> iter = user.getAccounts().getIterator();
    Account acc = null;

    for (int j = 0; j < rand.nextInt(user.getAccounts().size())+1; j++) {
      acc = iter.next();
    }
    return acc;
  }
  public static Transaction newTransaction(final int i) throws Exception {
    Thread.sleep(10);
    return TransactionCreator.newTransaction(categories[i % categories.length], i * 100, "", user.getCategoryStore());
  }
  public static Transaction transactionWithTimeByIandJ(final int i, final int j) throws Exception {
    return TransactionCreator.transactionFromDatabaseData(timeByIandJ(i,j),categories[i % categories.length], i * 100, "", user.getCategoryStore(),trans_id.incrementAndGet());
  }
  private static ZonedDateTime timeByIandJ(final int i, final int j) {
    return ZonedDateTime.of(1970+i, ((7*i+j)%12)+1, ((4*j+i)%28)+1, (3*i+j)%24, (5*j+i)%60, 00, 0, ZoneId.of("UTC"));
  }
  public int numberOfCategories() {
    return categories.length;
  }
}





