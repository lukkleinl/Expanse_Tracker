package MongoDb;

import accounts.Account;
import transactions.Transaction;
import user.User;

public interface Write_Operation {

  void insertUser(User user);
  void insertTransaction(User user,Account acc, Transaction trans);
  void updateUser(User user);
  void updateTransaction(User user, Transaction trans);
  void deleteTransaction(User user, int trans_id);
  void deleteUser(User user);
  void clearDatabase();



  /*    maybe in other class
  void insertAccount(User user);
  void updateAccount(User user);
  void deleteAccount(User user);

  void insertTransaction(User user);
  void updateTransaction(User user);
  void deleteTransaction(User user);*/

}
