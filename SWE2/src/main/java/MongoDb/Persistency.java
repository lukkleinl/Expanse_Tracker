package MongoDb;

import user.User;

public interface Persistency {

  void insertUser(User user);
  void updateUser(User user);
  void deleteUser(User user);
}
