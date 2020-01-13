package gui.main;

import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import patterns.observing.Database;
import transactions.Transaction;
import transactions.TransactionCreator;
import user.User;

/**
 * Testuser Class
 *
 */
public class TestUser {

  private static final ChronologicTimes times = new ChronologicTimes();
  /**
   * Returns a test user with pre defined accounts and transactions.
   *
   * @return returns a User Object.
   */
  public static User getTestUser(String id,String firstname, String lastname,String password,
      Database base) throws Exception {
    User user = new User(id, firstname, lastname, password, base);

    Cash cash = new Cash("Wallet", 0, "Euro");
    user.addAccount(cash);
    user.addAccount(new CreditCard("Visa Creditcard", "Bank Austria", 1500, new Date(2021, 1, 1)));
    user.addAccount(new CreditCard("MasterCard CreditCard", "Austria", 5000, new Date(2022, 1, 1)));
    user.addAccount(new DebitCard("Giro Account", "Bank Austria", 1000, "AT121200001203250544"));
    user.addAccount(new Stocks("Amazon Stocks", new Date(2013, 2, 5), 0));

    Transaction trans1 =
        TransactionCreator.newTransaction("SALARY", 10350.35f, "Test", user.getCategoryStore());
    Transaction trans2 =
        TransactionCreator.newTransaction("DIVIDEND", 30240.00f, "test2", user.getCategoryStore());
    Transaction trans3 =
        TransactionCreator.newTransaction("SALARY", 200.00f, "test3", user.getCategoryStore());
    Transaction trans4 =
        TransactionCreator.newTransaction("DIVIDEND", 10.00f, "test4", user.getCategoryStore());
    Transaction trans5 =
        TransactionCreator.newTransaction("FOOD", 340.00f, "food1", user.getCategoryStore());
    Transaction trans6 =
        TransactionCreator.newTransaction("FOOD", 20.00f, "food2", user.getCategoryStore());
    Transaction trans7 =
        TransactionCreator.newTransaction("FOOD", 930.00f, "food3", user.getCategoryStore());
    Transaction trans8 =
        TransactionCreator.newTransaction("FOOD", 10.00f, "food4", user.getCategoryStore());

    try {
      user.applyAndSaveTransaction(trans1, cash);
      user.applyAndSaveTransaction(trans2, cash);
      user.applyAndSaveTransaction(trans3, cash);
      user.applyAndSaveTransaction(trans4, cash);
      user.applyAndSaveTransaction(trans5, cash);
      user.applyAndSaveTransaction(trans6, cash);
      user.applyAndSaveTransaction(trans7, cash);
      user.applyAndSaveTransaction(trans8, cash);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    ArrayList<String> categories = new ArrayList<>();
    categories.add("FOOD");
    categories.add("EDUCATION");
    categories.add("TRANSPORTATION");
    for(int i=9;i<40;i++)
    {
      String cat = categories.get(i%3);
      Transaction transaction= TransactionCreator.transactionFromDatabaseData(times.nextTime(),cat, 10.00f, "food4", user.getCategoryStore(),i);
      user.applyAndSaveTransaction(transaction,cash);
    }
    return user;
  }

  public static class ChronologicTimes {
    private static final ZonedDateTime earliest =
        ZonedDateTime.of(2019, 1, 1, 0, 15, 00, 0, ZoneId.of("UTC"));
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
