package ui_tests;

import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import java.util.Date;
import transactions.Deposit;
import transactions.DepositCategory;
import user.User;

/**
 * This class is in here for SUPD.
 * Its method returns a test user object.
 * The user object is needed for the GUI.
 * THIS CLASS WILL BE GONE IN FINAL SUBMISSION
 *
 * @author Patrick Gmasz
 */
public class TestUser {

  /**
   * Returns a test user object.
   *
   *
   * @return The test user object.
   */
  public static User getTestUser() {
    User user = new User(1234, "Max", "Mustermann", "123");
    Cash cash = new Cash("Wallet", 0, "Euro");
    user.addAccount(cash);
    user.addAccount(
        new CreditCard("Visa Creditcard", "Bank Austria", 1500, new Date(2021, 1, 1)));
    user.addAccount(
        new CreditCard("MasterCard CreditCard", "Austria", 5000, new Date(2022, 1, 1)));
    user.addAccount(new DebitCard("Giro Account", "Bank Austria", 1000, "AT121200001203250544"));
    user.addAccount(new Stocks("Amazon Stocks", new Date(2013, 2, 5), 0));
    Deposit deposit = new Deposit(new Date(), 10350.35f, DepositCategory.SALARY, "Test");
    user.addTransaction(deposit, cash);
    return user;
  }
}