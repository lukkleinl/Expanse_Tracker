package MongoDb;

import user.User;

public interface Persistency {

  void insert(User user);
  void deleteUser();
}
