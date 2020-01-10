package MongoDb;

import accounts.Account;
import iteration.CustomList;
import transactions.Transaction;
import user.User;

public interface Read_Operation {

  User getUsers();
  CustomList<User> getUsers(String ID);
  CustomList<Transaction> getTransactions();
  CustomList<Account> getAccounts();

}
