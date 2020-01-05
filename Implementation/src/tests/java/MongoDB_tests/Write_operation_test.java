package MongoDB_tests;

import MongoDb.WriteOperation;
import accounts.Account;
import accounts.Cash;
import accounts.DebitCard;
import exceptions.SWE_Exception;
import iteration.CustomIterator;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import transactions.Transaction;
import transactions.TransactionCreator;
import user.User;

public class Write_operation_test {

  private static final int accounts = 3;
  private static final int transactions = 10;
  private static User user;
  private static User user2;
  private static User user3;


  public static void main(final String[] args)  {

    LocalDateTime localDateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String formattedString = zonedDateTime.format(formatter);
    System.out.println(formattedString);


    //user = GroupingTestUser.newTestUserWith(accounts);

    WriteOperation mongo = new WriteOperation();


      user2 = new User(12, "firstname", "lastname", "password");
      user2.getCategoryStore().withDefaultCategories();
      user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
      user2.addAccount(new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE,
        "AT121200001203250544"));


  System.out.println(user2.getAccounts().size());


    user3= new User(1, "firstname", "lastname", "password");
    user3.getCategoryStore().withDefaultCategories();
    user3.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
    user3.addAccount(new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE,
        "AT121200001203250544"));

    user= new User(3, "firstname", "lastname", "password");
    user.getCategoryStore().withDefaultCategories();

    System.out.println(user3.getAccounts().size());

    CustomIterator<Account> acc=user2.getAccounts().getIterator();
    CustomIterator<Account> acc2=user3.getAccounts().getIterator();


    try {

      for (int i = 0; i < transactions; i++) {
        Transaction payout =
            TransactionCreator.newTransaction("FOOD",2000,"avf",user2.getCategoryStore());
            user2.applyAndSaveTransaction(payout, acc.element());
      }
    } catch (Exception e) {
      System.out.println("Fehler beim Hinzufügen der Transaktionen");
    }

    try {

      for (int i = 0; i < transactions; i++) {
        Transaction payout =
            TransactionCreator.newTransaction("FOOD",2000,"avf",user3.getCategoryStore());
        user3.applyAndSaveTransaction(payout, acc2.element());
      }
    } catch (Exception e) {
      System.out.println("Fehler beim Hinzufügen der Transaktionen");
    }

    Transaction payout =
        TransactionCreator.newTransaction("FOOD",2000,"avf",user3.getCategoryStore());
    try {
      user3.applyAndSaveTransaction(payout, acc2.element());
    } catch (SWE_Exception e) {
      e.printStackTrace();
    }

    try
    {
      mongo.insertUser(user3);
      System.out.println("erfolgreich");
    }
    catch (Exception e)
    {
      System.out.println("fehler beim einfügen");
    }

    try
    {
      mongo.insertUser(user2);
      System.out.println("erfolgreich");
    }
    catch (Exception e)
    {
      System.out.println("fehler beim einfügen");
    }

    payout.updateTransaction(zonedDateTime,2,"AD");
    mongo.updateTransaction(payout);

    try
    {
      mongo.insertUser(user);
      System.out.println("erfolgreich");
    }
    catch (Exception e)
    {
      System.out.println("fehler beim einfügen");
    }

    user.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));

    mongo.updateUser(user);
    //mongo.clearDatabase();
    //mongo.deleteUser(user);
    //mongo.deleteTransaction(user2,19);
    //mongo.clearDatabase();
    //mongo.insertUser(user2);

    //mongo.deleteUser(user);
  }

}
