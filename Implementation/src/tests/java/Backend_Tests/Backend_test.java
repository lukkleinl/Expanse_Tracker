package Backend_Tests;

import user.User;
import user.User_Facade;

public class Backend_test
{
  public static void main(final String[] args) throws Exception
  {
    User_Facade user_facade=new User_Facade();
    User user=user_facade.getUser("12945");

  }
}
