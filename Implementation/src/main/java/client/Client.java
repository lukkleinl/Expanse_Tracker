package client;


import gui.main.UserInterface;
import patterns.observing.Database;
import user.User;

public class Client {
  private User user;


  public static void main(String[] args) {
    // TODO Auto-generated method stub


    try {
    Database localStorage = new Database();
    }catch (Exception e){
      System.out.println(e.getMessage() +"Client");
    }

      UserInterface userInterface = new UserInterface();
      userInterface.start();
  }
}
