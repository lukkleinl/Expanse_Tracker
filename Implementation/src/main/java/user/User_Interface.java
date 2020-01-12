package user;

import accounts.Account;
import transactions.Transaction;

public interface User_Interface
{

  void addUser(String userID, String firstname, String lastname, String password) throws Exception;
  void updateUser(User user);
  void deleteTransaction(User user,int transaction_id);
  void add_or_updateTransaction(User user, Account acc, Transaction trans);
  User getUser(String ID);

}
