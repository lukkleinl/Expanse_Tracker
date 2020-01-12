package user;

import accounts.Account;
import exceptions.SWE_Exception;
import transactions.Transaction;

public interface User_Interface
{

  void addUser(String userID, String firstname, String lastname, String password) throws Exception;
  public void addAccount(User user,final Account acc);

  public void updateAccount(User user,final Account oldAcc, final Account newAcc);

  public void deleteAccount(User user,final Account acc);

  public void applyAndSaveTransaction(User user,final Transaction transaction, final Account account) throws SWE_Exception;

  public void updateTransaction(User user,final int accountID, final Transaction transaction,final float old_amount);

  public void deleteTransaction(User user,final int accountID, final Transaction transaction);

  public void removeTransactionCategory(User user,final String categoryname);

  public void newTransactionCategory(User user,String transactiontype,String transactionname);

  User getUser(String ID);

}
