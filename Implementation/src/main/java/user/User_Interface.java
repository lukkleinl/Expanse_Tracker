package user;

import accounts.Account;
import exceptions.SWE_Exception;
import transactions.Transaction;

public interface User_Interface
{

  void addUser(String userID, String firstname, String lastname, String password) throws Exception;
  public void addAccount(final Account acc);

  public void updateAccount(final Account oldAcc, final Account newAcc);

  public void deleteAccount(final Account acc);

  public void applyAndSaveTransaction(final Transaction transaction, final Account account) throws SWE_Exception;

  public void updateTransaction(final int accountID, final Transaction transaction);

  public void deleteTransaction(final int accountID, final Transaction transaction);
  User getUser(String ID);

}
