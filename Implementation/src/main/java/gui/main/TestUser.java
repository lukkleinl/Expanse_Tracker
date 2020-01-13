package gui.main;

import java.util.Date;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import transactions.Transaction;
import transactions.TransactionCreator;
import user.User;

/**
 * Testuser Class
 *
 */
public class TestUser {

  /**
   * Returns a test user with pre defined accounts and transactions.
   *
   * @return returns a User Object.
   */
  public static User getTestUser() {
    User user = new User("1234", "Max", "Mustermann", "123");
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
    return user;
  }
}
