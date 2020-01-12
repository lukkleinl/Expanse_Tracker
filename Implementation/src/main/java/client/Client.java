package client;

import Patterns.observing.Database;
import ui.main.UserInterface;
import user.User;

public class Client {
  private User user;


  public static void main(String[] args) {
    // TODO Auto-generated method stub


    try {
    Database localStorage = new Database();
    }catch (Exception e){
      System.out.println(e.getMessage());
    }

      UserInterface userInterface = new UserInterface(null); // localStorage throws Exception atm.
      userInterface.start();
  }
}
