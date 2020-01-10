package MongoDB_tests;

import MongoDb.ReadOperation;
import MongoDb.WriteOperation;
import accounts.Account;
import accounts.Cash;
import accounts.DebitCard;
import iteration.CustomIterator;
import transactions.Transaction;
import transactions.TransactionCreator;
import user.User;

public class Read_Operation_test {

  private static User user2;
  private static final int transactions = 10;

  public static void main(final String[] args)
  {
    WriteOperation write=new WriteOperation();
    ReadOperation read=new ReadOperation();


    user2 = new User("12", "firstname", "lastname", "password");
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
    User user=read.getUsers();

    write.clearDatabase();

    write.insertUser(user);

    //write.insertUser(user);

  }
}
