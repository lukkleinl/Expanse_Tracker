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
  public void addAccount(Account acc) {

  }

  @Override
  public void updateAccount(Account oldAcc, Account newAcc) {

  }

  @Override
  public void deleteAccount(Account acc) {

  }

  @Override
  public void applyAndSaveTransaction(Transaction transaction, Account account)
      throws SWE_Exception {

  }

  @Override
  public void updateTransaction(int accountID, Transaction transaction) {

  }

  @Override
  public void deleteTransaction(int accountID, Transaction transaction) {

  }


  @Override
  public User getUser(String ID)
  {
    User user=database.getUser(ID);
    return user;
  }
}
