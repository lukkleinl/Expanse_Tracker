package MongoDb;

import iteration.CustomList;
import transactions.Transaction;
import user.User;

public interface Read_Operation {

  CustomList<User> getUsers();
  User getUsers(String ID);
  CustomList<Transaction> getTransactions(String ID);

}
