package MongoDb;

import iteration.CustomList;
import user.User;

public interface Read_Operation {

  CustomList<User> getUsers();
  User getUsers(String ID);
  void getTransactions(User user);

}
