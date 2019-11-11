package ui;

import java.util.Observable;

import javax.swing.JFrame;
import user.User;

public class UserInterface {
  private User user;

  public void start() {
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

    while(true) {

      while (accountTypePage.getPageWanted() == AccountTypes.NONE) {
        try {
          Thread.sleep(1);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      switch (accountTypePage.getPageWanted()) {
        case CASH:
          //TODO: new CashViewPage -> cashViewPage.configureFrame()
          System.out.println("Cash view should open here");
          break;
        case DEBIT:
          //TODO: new DebitViewPage -> debitViewPage.configureFrame()
          System.out.println("Debit view should open here");
          break;
        case CREDIT:
          //TODO: new CreditViewPage -> creditViewPage.configureFrame()
          System.out.println("Credit view should open here");
          break;
        case STOCKS:
          //TODO: new StockViewPage -> stockViewPage.configureFrame()
          System.out.println("Stock view should open here");
          break;
      }
      accountTypePage.resetPageWanted();
    }

  }
}
