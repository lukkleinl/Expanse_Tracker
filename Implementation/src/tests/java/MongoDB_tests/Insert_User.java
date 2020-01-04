package MongoDB_tests;

import MongoDb.WriteOperation;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import transactions.grouping.GroupingTestUser;
import user.User;

public class Insert_User {

  private static final int accounts = 3;
  private static final int transactions = 10;
  private static User user;
  private static User user2;


  public static void main(final String[] args) {

    LocalDateTime localDateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String formattedString = zonedDateTime.format(formatter);
    System.out.println(formattedString);

    user = GroupingTestUser.newTestUserWith(accounts);
    //user2 = GroupingTestUser.newTestUserWith(accounts);

    WriteOperation mongo = new WriteOperation();

    try {
      for (int i = 0; i < transactions; i++) {
        user.applyAndSaveTransaction(GroupingTestUser.newTransaction(i),
            GroupingTestUser.randomAccount());
      }
    } catch (Exception e) {
      System.out.println("Fehler beim Hinzufügen der Transaktionen");
    }



      user2 = new User(12345, "firstname", "lastname", "password");
      user2.getCategoryStore().withDefaultCategories();

      int newAcc = accounts >= 4 ? 4 : accounts;
      newAcc = accounts <= 0 ? 1 : accounts;

      switch (newAcc) {
        case 4:
          user2.addAccount(new Cash("Wallet", Integer.MIN_VALUE, "Euro"));
        case 3:
          user2.addAccount(new DebitCard("Giro Account", "Bank Austria", Integer.MIN_VALUE,
              "AT121200001203250544"));
        case 2:
          user2.addAccount(
              new CreditCard("MasterCard", "Austria", Integer.MIN_VALUE, new Date(2022, 1, 1)));
        case 1:
          user2.addAccount(new Stocks("Amazon Stocks", new Date(2013, 2, 5), Integer.MIN_VALUE));
      }

    try {
      for (int i = 0; i < transactions; i++) {
        user2.applyAndSaveTransaction(GroupingTestUser.newTransaction(i),
            GroupingTestUser.randomAccount());
      }
    } catch (Exception e) {
      System.out.println("Fehler beim Hinzufügen der Transaktionen");
    }
     // mongo.deleteUser(user2);

    mongo.clearDatabase();
    try
    {
      mongo.insertUser(user);
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

    mongo.deleteUser(user);
    mongo.deleteTransaction(user2,19);
    //mongo.clearDatabase();
    //mongo.insertUser(user2);

    //mongo.deleteUser(user);
  }

}
