package ui;

import MongoDb.MongoDB;
import javax.swing.JComponent;
import javax.swing.JFrame;
import user.User;

public class UserInterfaceMain {

  public static void main(String[] args) {

    User user = null;
    //MongoDB db = new MongoDB();


    LoginPage loginPage = new LoginPage();
    RegistrationPage registrationPage = new RegistrationPage();

    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    loginPage.configureFrame(frame);

    // PAUL
/*
    AccountTypePage accp = new AccountTypePage();
    accp.configureFrame(frame);

    boolean test = true;
    while(test){

    }
*/

    // PAUL END



    boolean foundUser = false;

    while(!foundUser) {
      //TODO: check if user exists and if password is correct
      if(loginPage.getUser().equals("admin") && loginPage.getPassword().equals("admin")) {
        //TODO: user = db.getUser(userid)
        foundUser = true;
      }
      else if(loginPage.isRegistrationWanted()) {

        //OPEN REGISTRATION PAGE COMPONENTS
        registrationPage.configureFrame(frame);

        boolean registrationFinished = false;

        while(!registrationFinished) {
          if(registrationPage.isRegistrationComplete()) {
            //TODO: save userdata here
            registrationFinished = true;
            loginPage.registrationFinished();

            try {
              Thread.sleep(1000);
            } catch (Exception e) {
              e.printStackTrace();
            }

            //OPEN LOGIN PAGE COMPONENTS AGAIN
            loginPage.configureFrame(frame);
          }
          try {
            Thread.sleep(1);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
      try {
        Thread.sleep(1);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    AccountTypePage accountTypePage = new AccountTypePage(/*user*/);
    accountTypePage.configureFrame(frame);

  }

}
