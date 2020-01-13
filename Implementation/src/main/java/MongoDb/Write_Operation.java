package MongoDb;

import accounts.Account;
import transactions.Transaction;
import user.User;


/**
 *
 * Interface for all CUD operations within the database
 *
 */

public interface Write_Operation {

  void insertUser(User user);
  void insertTransaction(User user,Account acc, Transaction trans);
  void updateUser(User user);
  void updateTransaction(Transaction trans);
  void deleteTransaction(User user, int trans_id);
  void deleteUser(User user);
  void clearDatabase();

}
