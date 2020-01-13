package mongoDB;

import iteration.CustomContainer;
import iteration.CustomList;
import java.util.Map;
import transactions.Transaction;
import user.User;

/**
 * Defines the methods to get Users and Transactions from the database
 *
 * @author Lukas Kleinl
 */
public interface Read_Operation {

  CustomList<User> getUsers();
  User getUsers(String ID);
  Map<Integer, CustomContainer<Transaction>> getTransactions(User user);

}
