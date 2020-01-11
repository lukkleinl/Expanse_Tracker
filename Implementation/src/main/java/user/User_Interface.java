package user;

import accounts.Account;
import java.util.Date;
import transactions.Transaction;

public interface User_Interface
{

  void addUser(String userID, String firstname, String lastname, String password);
  void updateUser(User user);
  void deleteUser(User user);

  void addAccount(User user,String accountName, Date buyDate,float limit);
  void addAccount(User user,String accountName, String bankName, float limit,String IBAN);
  void addAccount(User user,String accountName, String bankName, float limit, Date expiryDate);
  void addAccount(User user,String accountName, float limit,String currency);

  void addTransaction(User user, Account account, Transaction transaction);
  void updateTransaction(Transaction transaction);
  void deleteTransaction(Transaction transaction);

  User getUser(String ID);

}
