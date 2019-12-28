package MongoDB_tests;

import MongoDb.MongoDB;
import accounts.Cash;
import accounts.Stocks;
import iteration.CustomContainer;
import java.util.Date;
import java.util.Map;
import transactions.Transaction;
import user.User;

public class Insert_User {
  private User user;


  public static void main(final String[] args) {

    Date date=new Date();
    String depositcategory="SALARY";
    String payoutcategory="FOOD";
    MongoDB mongo=new MongoDB();
    User user=new User(12,"lukas","kleinl", "1234");
    Cash cash = new Cash("Cash",0,"Euro");
    Stocks stock = new Stocks("Stock",date,0);
    user.addAccount(cash);
    user.addAccount(stock);

    // user.deposit(cat,100,"Auszahlun",stock);
    // user.deposit(cat,100,"Auszahlung",stock);
    //user.deposit(cat,100,"Auszahlun",cash);
    //user.deposit(cat,1000,"einzahl",cash);
    // try {
    //user.payOut(pay, 100, "Auszahlun", cash);
    //} catch (LimitException e) {
    //System.out.println(e.getMessage());
  }
  Map<Integer, CustomContainer<Transaction>> map;
  //map=user.getTransactions();
  /*
    CustomIterator<Account> abc=user.getAccounts().getIterator();
    while(abc.hasNext())
    {
      System.out.println(abc.next().getBalance());
    }
    for (Entry e : map.entrySet()) {
      System.out.println(e.getKey());
    }
    System.out.println(map.size());


    mongo.insertUser(user);
   */
}
