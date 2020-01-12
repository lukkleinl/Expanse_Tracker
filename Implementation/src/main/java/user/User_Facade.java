package user;

import Patterns.observing.Database;
import accounts.Account;
import exceptions.SWE_Exception;
import transactions.Transaction;

public class User_Facade implements User_Interface{

  private Database database;

  public User_Facade() throws Exception
  {
    database=new Database();

  }

  @Override
  public void addUser(String userID, String firstname, String lastname, String password) throws Exception
  {
    new User(userID, firstname, lastname, password,database);
  }

  @Override
  public void addAccount(User user,Account acc)
  {
      user.addAccount(acc);
  }

  @Override
  public void updateAccount(User user,Account oldAcc, Account newAcc) {
      user.updateAccount(oldAcc,newAcc);
  }

  @Override
  public void deleteAccount(User user,Account acc)
  {
    user.deleteAccount(acc);
  }

  @Override
  public void applyAndSaveTransaction(User user,Transaction transaction, Account account)
      throws SWE_Exception {
      user.applyAndSaveTransaction(transaction,account);
  }

  @Override
  public void updateTransaction(User user,int accountID, Transaction transaction) {
      user.updateTransaction(accountID,transaction);
  }

  @Override
  public void deleteTransaction(User user,int accountID, Transaction transaction) {
      user.deleteTransaction(accountID,transaction);
  }


  @Override
  public User getUser(String ID)
  {
    User user=database.getUser(ID);
    return user;
  }
}
