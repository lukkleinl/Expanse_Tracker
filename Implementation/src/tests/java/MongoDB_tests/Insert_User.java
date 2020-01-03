package MongoDB_tests;

import MongoDb.WriteOperation;
import accounts.Account;
import iteration.CustomContainer;
import iteration.CustomIterator;
import java.util.Map;
import java.util.Map.Entry;
import transactions.Transaction;
import transactions.grouping.GroupingTestUser;
import user.TransactionStore;
import user.User;

public class Insert_User {

  private static final int accounts = 2;
  private static final int transactions = 1000;
  private static User user;


  public static void main(final String[] args) {

    user = GroupingTestUser.newTestUserWith(accounts);

    WriteOperation mongo = new WriteOperation();

    try {
      for (int i = 0; i < transactions; i++) {
        user.applyAndSaveTransaction(GroupingTestUser.newTransaction(i),
            GroupingTestUser.randomAccount());
      }
    } catch (Exception e) {
      System.out.println("Fehler beim Hinzufügen der Transaktionen");
    }

    Map<Integer, CustomContainer<Transaction>> map;
    TransactionStore trans = user.getTransactionStore();
    map = trans.getTransactions();

    CustomIterator<Account> abc = user.getAccounts().getIterator();
    while (abc.hasNext()) {
      System.out.println(abc.next().getBalance());
    }
    System.out.println("get balance vorbei");
    for (Entry e : map.entrySet()) {
      //System.out.println(e.getKey());
      //System.out.println(e.getValue());
    }


    //System.out.println(map.size());

    mongo.insertUser(user);
  }

}
