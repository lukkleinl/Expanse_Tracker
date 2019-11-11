package ui;

import accounts.Account;
import accounts.Cash;
import java.util.Observable;

import javax.swing.JFrame;
import user.User;

public class UserInterface {
  private User user;

  public void start() {
    //MongoDB db = new MongoDB();

    LoginPage loginPage = new LoginPage();
    RegistrationPage registrationPage = new RegistrationPage();

    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    loginPage.configureFrame(frame);

    boolean foundUser = false;

    while(!foundUser) {
      //TODO: check if user exists and if password is correct
      if(loginPage.getUser().equals("admin") && loginPage.getPassword().equals("admin")) {
        //TODO: user = db.getUser(userid)
        user = TestUser.getTestUser();
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

            //OPEN LOGIN PAGE COMPONENTS AGAIN
            loginPage.configureFrame(frame);
          }
        }
      }
    }

    AccountListPage accountListPage = new AccountListPage(user);
    AccountTypePage accountTypePage = new AccountTypePage();
    Add_CashAccountPage add_cashAccountPage = new Add_CashAccountPage();
    Add_DebitAccountPage add_debitAccountPage = new Add_DebitAccountPage();
    Add_StockAccountPage add_stockAccountPage = new Add_StockAccountPage();
    Add_CreditAccountPage add_creditAccountPage = new Add_CreditAccountPage();

    accountListPage.configureFrame(frame);

    while (true) {

      if(accountListPage.isNewAccountWanted()) {
        accountTypePage.configureFrame(frame);

        while(accountTypePage.getPageWanted() == AccountTypes.NONE);

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
      else if(accountListPage.getSelectedAccount() instanceof Cash) {
      }
    }


    /*
    AccountTypePage accountTypePage = new AccountTypePage();
    accountTypePage.configureFrame(frame);

    while(true) {

      while (accountTypePage.getPageWanted() == AccountTypes.NONE);

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
    */

  }
}
