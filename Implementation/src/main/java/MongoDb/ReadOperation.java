package MongoDb;

import accounts.Account;
import com.mongodb.client.MongoCollection;
import iteration.CustomList;
import transactions.Transaction;
import user.User;

public class ReadOperation implements Read_Operation
{

  private MongoCollection collection;

  @Override
  public CustomList<User> getUsers() {
    return null;
  }

  @Override
  public CustomList<User> getUsers(String ID) {
    return null;
  }

  @Override
  public CustomList<Transaction> getTransactions() {
    return null;
  }

  @Override
  public CustomList<Account> getAccounts() {
    return null;
  }
}
