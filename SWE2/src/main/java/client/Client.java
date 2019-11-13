package client;

import static transactions.DepositCategory.SALARY;
import static transactions.PayoutCategory.FOOD;

import MongoDb.MongoDB;
import accounts.Account;
import accounts.Cash;
import accounts.Stocks;
import exceptions.LimitException;
import iteration.CustomContainer;
import iteration.CustomIterator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import transactions.DepositCategory;
import transactions.PayoutCategory;
import transactions.Transaction;
import ui.main.UserInterface;
import user.User;

public class Client {
  private User user;


  public static void main(String[] args) {
    // TODO Auto-generated method stub

    //FOR SUPD
    UserInterface userInterface = new UserInterface();
    userInterface.start();


    /*
    Date date=new Date();
    double x=22;
    DepositCategory cat=SALARY;
    PayoutCategory pay=FOOD;
    MongoDB mongo=new MongoDB();
    User user=new User(12,"lukas","kleinl", "1234");
    Cash cash = new Cash("Cash",0,"Euro");
    Stocks stock = new Stocks("Stock",date,0);
    user.addAccount(cash);
    user.addAccount(stock);

    user.deposit(cat,100,"Auszahlun",stock);
    user.deposit(cat,100,"Auszahlung",stock);
    //user.deposit(cat,100,"Auszahlun",cash);
    user.deposit(cat,1000,"einzahl",cash);
    try {
      user.payOut(pay, 100, "Auszahlun", cash);
    } catch (LimitException e) {
      System.out.println(e.getMessage());
    }
    Map<Integer, CustomContainer<Transaction>> map=new HashMap<>();
    map=user.getTransactions();

    CustomIterator<Account> abc=user.getAccounts().getIterator();
    while(abc.hasNext())
    {
      System.out.println(abc.next().getBalance());
    }
    for (Entry e : map.entrySet()) {
      e.getValue();
      System.out.println(e.getKey());
    }
    System.out.println(map.size());


    mongo.insert(user);
    */
  }
}
