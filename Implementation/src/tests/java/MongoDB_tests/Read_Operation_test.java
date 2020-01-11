package MongoDB_tests;

import MongoDb.ReadOperation;
import MongoDb.WriteOperation;
import accounts.Account;
import accounts.Cash;
import accounts.DebitCard;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import java.util.Map;
import java.util.Map.Entry;
import transactions.Transaction;
import transactions.TransactionCreator;
import user.User;

public class Read_Operation_test {

  private static User user2;
  private static User user3;
  private static final int transactions = 10;

  public static void main(final String[] args)
  {
    WriteOperation write=new WriteOperation();
    ReadOperation read=new ReadOperation();

    CustomList<User> users=read.getUsers();

    CustomIterator<User> iter=users.getIterator();
    while(iter.hasNext())
    {
      Map<Integer, CustomContainer<Transaction>> trans_map =iter.element().getTransactionStore().getTransactions();
      for (Entry e : trans_map.entrySet())
      {
        CustomContainer<Object> list = (CustomList<Object>) e.getValue();
        CustomIterator<Object> iterator = list.getIterator();
        Integer account_number = (Integer) e.getKey();

        while (iterator.hasNext())
        {
          System.out.println("Transaction" + account_number + " "+ iterator.element());
          iterator.next();
        }
      }

      iter.next();
    }
    WriteOperation writeOperation=new WriteOperation();

    user2 = new User("12", "firstname", "lastname", "password");
    user2.getCategoryStore().withDefaultCategories();
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE,
        "AT121200001203250544"));

    CustomIterator<Account> acc=user2.getAccounts().getIterator();
    //CustomIterator<Account> acc2=user3.getAccounts().getIterator();


    try {

      for (int i = 0; i < transactions; i++) {
        Transaction payout =
            TransactionCreator.newTransaction("FOOD",2000,"avf",user2.getCategoryStore());
        user2.applyAndSaveTransaction(payout, acc.element());
      }
    } catch (Exception e) {
      System.out.println("Fehler beim Hinzufügen der Transaktionen");
    }


    System.out.println(users.size());

    /*user2 = new User("12", "firstname", "lastname", "password");
    user2.getCategoryStore().withDefaultCategories();
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE,
        "AT121200001203250544"));


    CustomIterator<Account> acc=user2.getAccounts().getIterator();
    try {

      for (int i = 0; i < transactions; i++) {
        Transaction payout =
            TransactionCreator.newTransaction("FOOD",2000,"avf",user2.getCategoryStore());
        user2.applyAndSaveTransaction(payout, acc.element());
      }
    } catch (Exception e) {
      System.out.println("Fehler beim Hinzufügen der Transaktionen");
    }


    write.clearDatabase();

    write.insertUser(user2);
    User user=null;
    user=read.getUsers("12");

    write.clearDatabase();

    if(user!=null)
      write.insertUser(user);

    user2 = new User("123", "firstnam1e", "lastname", "password");
    user2.getCategoryStore().withDefaultCategories();
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE,
        "AT121200001203250544"));

    user2.getCategoryStore().addTransactionCategory(new PayoutCategory("agsbs"));

    write.insertUser(user2);

    user3 = new User("1234", "firstname2", "lastname", "password");
    user3.getCategoryStore().withDefaultCategories();
    user3.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user3.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user3.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user3.addAccount(new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE,
        "AT121200001203250544"));

    CustomIterator<Account> acc2=user3.getAccounts().getIterator();
    try {

      for (int i = 0; i < transactions; i++) {
        Transaction payout =
            TransactionCreator.newTransaction("EDUCATION",2000,"avf",user3.getCategoryStore());
        user3.applyAndSaveTransaction(payout, acc2.element());
      }
    } catch (Exception e) {
      System.out.println("Fehler beim Hinzufügen der Transaktionen");
    }

    write.insertUser(user3);

    CustomList<User> list=read.getUsers();
    Map<Integer, CustomContainer<Transaction>> map=new HashMap<>();
    map=read.getTransactions(user3);


    write.clearDatabase();

    CustomIterator iter=list.getIterator();
    while (iter.hasNext())
    {
      User user9=(User) iter.next();
      write.insertUser(user9);
    }
    User user5=read.getUsers("12");

    write.clearDatabase();

    user5.getCategoryStore().addTransactionCategory(new DepositCategory("ad"));
    CustomIterator<Account> acc3=user5.getAccounts().getIterator();

    try {

      for (int i = 0; i < transactions; i++) {
        Transaction payout =
            TransactionCreator.newTransaction("AD",2000,"avf",user5.getCategoryStore());
        user5.applyAndSaveTransaction(payout, acc3.element());
      }
    } catch (Exception e) {
      System.out.println("Fehler beim Hinzufügen der Transaktionen");
    }
    write.insertUser(user5);

    User a=read.getUsers(user.getUserID());*/

  }
}
