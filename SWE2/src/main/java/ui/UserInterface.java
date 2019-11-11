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
    //frame.setLocationRelativeTo(null);

    loginPage.configureFrame(frame);

    boolean foundUser = false;

    while (!foundUser) {
      //TODO: check if user exists and if password is correct
      if (loginPage.getUser().equals("admin") && loginPage.getPassword().equals("admin")) {
        //TODO: user = db.getUser(userid)
        user = TestUser.getTestUser();
        foundUser = true;
      } else if (loginPage.isRegistrationWanted()) {

        //OPEN REGISTRATION PAGE COMPONENTS
        registrationPage.configureFrame(frame);

        boolean registrationFinished = false;

        while (!registrationFinished) {
          if (registrationPage.isRegistrationComplete()) {
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
    TransactionListPage transactionListPage;

    accountListPage.configureFrame(frame);

    while (true) {

      if (accountListPage.isNewAccountWanted()) {
        accountTypePage.configureFrame(frame);

        while (accountTypePage.getPageWanted() == AccountTypes.NONE && !accountTypePage
            .isBackWanted()) {
          ;
        }

        if (!accountTypePage.isBackWanted()) {
          switch (accountTypePage.getPageWanted()) {
            case CASH:
              add_cashAccountPage.configureFrame(frame);
              while (!add_cashAccountPage.isSubmitted()) {
                ;
              }
              break;
            case DEBIT:
              add_debitAccountPage.configureFrame(frame);
              while (!add_debitAccountPage.isSubmitted()) {
                ;
              }
              break;
            case CREDIT:
              add_creditAccountPage.configureFrame(frame);
              while (!add_creditAccountPage.isSubmitted()) {
                ;
              }
              break;
            case STOCKS:
              add_stockAccountPage.configureFrame(frame);
              while (!add_stockAccountPage.isSubmitted()) {
                ;
              }
              break;
          }
          accountListPage.configureFrame(frame);
        } else {
          accountListPage.configureFrame(frame);
        }
      } else if (accountListPage.getSelectedAccount() != null) {
        transactionListPage = new TransactionListPage(accountListPage.getSelectedAccount(), user);
        transactionListPage.configureFrame(frame);

        while (!transactionListPage.isNewDepositWanted() && !transactionListPage.isNewPayoutWanted()
            && !transactionListPage.isBackWanted()) {
          ;
        }

        if (transactionListPage.isNewPayoutWanted()) {
          //open new Payout Page, for tests now add  cash account
          add_cashAccountPage.configureFrame(frame);
          while (!add_cashAccountPage.isSubmitted()) {
            ;
          }
        } else if (transactionListPage.isNewDepositWanted()) {
          //open new Deposit Page, for tests now add a credit account
          add_creditAccountPage.configureFrame(frame);
          while (!add_creditAccountPage.isSubmitted()) {
            ;
          }
        } else {
          accountListPage.configureFrame(frame);
        }
      }
    }


  }
}
