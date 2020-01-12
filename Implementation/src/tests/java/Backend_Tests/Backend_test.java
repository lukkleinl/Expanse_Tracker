package Backend_Tests;

import MongoDb.WriteOperation;
import accounts.Account;
import accounts.Cash;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import transactions.Transaction;
import transactions.TransactionCreator;
import user.User;
import user.User_Facade;

public class Backend_test
{
  public static void main(final String[] args) throws Exception
  {
    WriteOperation write=new WriteOperation();
    write.clearDatabase();
    User_Facade user_facade=new User_Facade();
    user_facade.addUser("af","a","a","a");
    User user2=user_facade.getUser("af");

    System.out.println(user2.getUserID());
    User user=user_facade.getUser("12945");
    if(user==null)
    {
      System.out.println("asmlmb");
    }

    Account cash=new Cash("Wallet", Integer.MIN_VALUE, "Euro");
    user2.addAccount(cash);
    Transaction payout = TransactionCreator.newTransaction("FOOD",2000,"avf",user2.getCategoryStore());
    user2.applyAndSaveTransaction(payout,cash);

    LocalDateTime localDateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
    payout.updateTransaction(zonedDateTime,200,"avafb");

    user_facade.updateTransaction(user2,cash.getAccount_number(),payout);

  }
}
