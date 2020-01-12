package user;

import Patterns.observing.Database;
import accounts.Account;
import exceptions.SWE_Exception;
import transactions.Transaction;
import transactions.categories.DepositCategory;
import transactions.categories.PayoutCategory;

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
  public void updateTransaction(User user,int accountID, Transaction transaction,float old_amount) {
      user.updateTransaction(accountID,transaction,old_amount);
  }

  @Override
  public void deleteTransaction(User user,int accountID, Transaction transaction) {
      user.deleteTransaction(accountID,transaction);
  }

  @Override
  public void removeTransactionCategory(User user,String categoryname) {
      user.removeTransactionCategory(categoryname);
  }

  @Override
  public void newTransactionCategory(User user,String categorytype,String categoryname) {
    if(categorytype.toUpperCase().equals("PAYOUT"))
        user.newTransactionCategory(new PayoutCategory(categoryname));
    else if(categorytype.toUpperCase().equals("DEPOSIT"))
        user.newTransactionCategory(new DepositCategory(categoryname));
    else
      assert true : "Should not reach this argument";
  }

  @Override
  public User getUser(String ID)
  {
    User user=database.getUser(ID);
    return user;
  }
}
