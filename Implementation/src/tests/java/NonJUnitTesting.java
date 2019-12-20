import java.time.ZoneId;
import java.time.ZonedDateTime;
import accounts.Account;
import accounts.Cash;
import accounts.DebitCard;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.TransactionCreator;
import transactions.categories.CategoryStore;
import transactions.categories.DepositCategory;
import transactions.categories.PayoutCategory;
import transactions.grouping.byAccount.AllAccounts;
import user.User;

public class NonJUnitTesting {

  public static void main(final String[] args) {
    yearly();
  }

  private static void yearly() {
    User user = new User(1234, "firstname", "lastname", "password");
    user.getCategoryStore().withDefaultCategories();
    Account cash = new Cash("Wallet", Integer.MIN_VALUE, "Euro");
    Account debit =
        new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE, "AT121200001203250544");
    user.addAccount(cash);
    user.addAccount(debit);

    try {
      String[] categories = user.getCategories(null).toArray(new String[0]);

      final int rounds = 5;

      user.applyAndSaveTransaction(TransactionCreator.newTransactionWith(
          ZonedDateTime.of(2017, 12, 14, 7, 18, 47, 91, ZoneId.of("UTC")), categories[0], 7, "",
          user.getCategoryStore(), 2000), cash);

      user.applyAndSaveTransaction(TransactionCreator.newTransactionWith(
          ZonedDateTime.of(2014, 12, 14, 7, 18, 47, 91, ZoneId.of("UTC")), categories[0], 7, "",
          user.getCategoryStore(), 2000), debit);

      for (int i = 0; i < (rounds * categories.length); i++) {
        Transaction transcash = TransactionCreator.newTransactionWith(categories[i % rounds],
            i * 100, "", user.getCategoryStore());
        user.applyAndSaveTransaction(transcash, cash);

        Thread.sleep(10);

        Transaction transdebit = TransactionCreator.newTransactionWith(
            categories[categories.length - 1 - (i % rounds)], i * 200, "", user.getCategoryStore());
        user.applyAndSaveTransaction(transdebit, debit);

        Thread.sleep(10);

        // System.out.println("i: " + i + ", " + transcash);
        // System.out.println("i: " + i + ", " + transdebit);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(new AllAccounts(user).earliest());
  }

  private static void timeTesting() {
    Transaction t = TransactionCreator.newTransactionWith("FOOD", 50, "McD", new CategoryStore());
    System.out.println("Stored Creation Date: " + t.getCreationDate());
    System.out.println("Stored Creation Date after formatting: " + t.getFormattedCreationDate());
    System.out.println("Is Deposit: " + (t instanceof Deposit));
    System.out.println("Is Payout: " + (t instanceof Payout));
  }

  private static void storeTesting() {
    CategoryStore cs = new CategoryStore();
    System.out.println(cs.getCategories(null));
    cs.addTransactionCategory(new PayoutCategory("Sprit"));
    cs.addTransactionCategory(new PayoutCategory("Coiffeur"));
    cs.addTransactionCategory(new DepositCategory("Pocketmoney"));
    cs.addTransactionCategory(new PayoutCategory("IceSkating"));
    cs.addTransactionCategory(new PayoutCategory("Oktoberfest"));
    System.out.println(cs.getCategories(null));
  }
}
