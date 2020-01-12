package Backend_Tests;

import user.User;
import user.User_Facade;

public class Backend_test
{
  public static void main(final String[] args) throws Exception
  {
    User_Facade user_facade=new User_Facade();
    user_facade.addUser("af","a","a","a");
    User user2=user_facade.getUser("af");

    System.out.println(user2.getUserID());
    User user=user_facade.getUser("12945");

  }
}
