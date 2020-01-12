package user;

import Patterns.observing.Database;
import accounts.Account;
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
  public void updateUser(User user)
  {
      database.update(user);
  }

  @Override
  public void deleteTransaction(User user,int transaction_id)
  {
      database.update(user,transaction_id);
  }

  @Override
  public void add_or_updateTransaction(User user, Account acc, Transaction trans)
  {
      database.update(user,acc,trans);
  }

  @Override
  public User getUser(String ID)
  {
    User user=database.getUser(ID);
    return user;
  }
}
