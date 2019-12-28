package MongoDb;

import user.User;

public interface Persistency {

  void insertUser(User user);
  void updateUser(User user);
  void deleteUser(User user);



  /*    maybe in other class
  void insertAccount(User user);
  void updateAccount(User user);
  void deleteAccount(User user);

  void insertTransaction(User user);
  void updateTransaction(User user);
  void deleteTransaction(User user);*/

}
