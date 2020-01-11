package Observer_Tests;

import MongoDb.WriteOperation;
import Patterns.observing.Database;
import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import transactions.Transaction;
import transactions.TransactionCreator;
import transactions.categories.PayoutCategory;
import user.User;

public class Observer_Test {

  private static User user2;
  private static User user3;
  private static final int transactions = 10;

  public static void main(final String[] args) throws Exception
  {

    Date date=new Date();
    WriteOperation write=new WriteOperation();
    //write.clearDatabase();
    Database base=new Database();
    user2 = new User("1294", "firstname", "lastname", "password",base);
    user2.getCategoryStore().withDefaultCategories();
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE,
        "AT121200001203250544"));
    user2.addAccount(new CreditCard("test","test2",Integer.MIN_VALUE,date));

    CustomIterator<Account> acc=user2.getAccounts().getIterator();
    try {

      for (int i = 0; i < transactions; i++) {
        Transaction payout =
            TransactionCreator.newTransaction("FOOD",2000,"avf",user2.getCategoryStore());
        user2.applyAndSaveTransaction(payout, acc.element());
      }
    } catch (Exception e)
    {
      System.out.println("Fehler beim Hinzufügen der Transaktionen");
    }


    user3 = new User("123456", "firstname", "lastname", "password",base);
    user3.getCategoryStore().withDefaultCategories();
    user3.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user3.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user3.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user3.addAccount(new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE,
        "AT121200001203250544"));


    user3.getCategoryStore().addTransactionCategory(new PayoutCategory("AD"));
    Transaction payout=null;
    CustomIterator<Account> acc3=user3.getAccounts().getIterator();
    try {

      for (int i = 0; i < transactions; i++) {
        payout =
            TransactionCreator.newTransaction("AD",2000,"avf",user3.getCategoryStore());
        user3.applyAndSaveTransaction(payout, acc3.element());
      }
    } catch (Exception e)
    {
      System.out.println("Fehler beim Hinzufügen der Transaktionen");
    }

    CustomIterator<User> it=base.getUsers().getIterator();
    while (it.hasNext())
    {
      System.out.println(it.next().getUserID());
    }

    //user3.deleteTransaction(5,payout);

    //base.deleteUser(user2);

    User user=base.getUser("1234567");

    System.out.println(user.getAccounts().size());
    System.out.println(user.getTransactionStore().accountsWithTransactions());

    Map<Integer, CustomContainer<Transaction>> Transactions_map=new HashMap<>();
    Transactions_map=user.getTransactionStore().getTransactions();
    for (Entry e : Transactions_map.entrySet())
    {
      CustomContainer<Object> list = (CustomList<Object>) e.getValue();
      CustomIterator<Object> iterator = list.getIterator();
      while (iterator.hasNext())
      {
        System.out.println(iterator.next().toString());
      }

    }

  }

}
