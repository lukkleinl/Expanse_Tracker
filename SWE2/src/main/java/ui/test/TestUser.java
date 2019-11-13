package ui.test;

import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import java.util.Date;
import transactions.DepositCategory;
import user.User;

public class TestUser {

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
    user.deposit(DepositCategory.SALARY, 10350.35f, "Test", cash);
    return user;
  }
}
