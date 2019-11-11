package ui;

import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import java.util.Date;
import user.User;

public class TestUser {

  public static User getTestUser() {
    User user = new User(1234, "Max", "Mustermann", "123");
    user.addCash(new Cash("Wallet", 0, "Euro"));
    user.addCreditCard(new CreditCard("Visa Creditcard", "Bank Austria", 1500, new Date(2021,1,1)));
    user.addCreditCard(new CreditCard("MasterCard CreditCard", "Austria", 5000, new Date(2022,1,1)));
    user.addDebitCard(new DebitCard("Giro Account", "Bank Austria", 1000, "AT121200001203250544"));
    user.addStocks(new Stocks("Amazon Stocks", new Date(2013, 2, 5), 0));
    return user;
  }
}
