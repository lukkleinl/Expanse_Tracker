package MongoDb;

import iteration.CustomContainer;
import iteration.CustomList;
import java.util.Map;
import transactions.Transaction;
import user.User;

public interface Read_Operation {

  CustomList<User> getUsers();
  User getUsers(String ID);
  Map<Integer, CustomContainer<Transaction>> getTransactions(User user);

}
