package ui;

import javax.swing.JFrame;

import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import user.User;

/**
 * This class is contains the whole procedure of the GUI.
 *
 * @author Patrick Gmasz
 */
public class UserInterface {

  private User user;
  //MongoDB db = new MongoDB();

  /**
   * This method starts the GUI and starts the whole procedure.
   */
  public void start() {
    LoginPage loginPage = new LoginPage();
    RegistrationPage registrationPage = new RegistrationPage();

    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    loginPage.configureFrame(frame);

    boolean foundUser = false;

    while (!foundUser) {
      //TODO: check if user exists and if password is correct
      if (loginPage.isLoginWanted()) {
        if(loginPage.getUser().equals("admin") && loginPage.getPassword().equals("admin")) {
          //TODO: user = db.getUser(userid)
          user = TestUser.getTestUser();
          foundUser = true;
        }
        else {
          loginPage.configureFrame(frame);
          //TODO: display error message
        }
      } else if (loginPage.isRegistrationWanted()) {

        //OPEN REGISTRATION PAGE COMPONENTS
        registrationPage.configureFrame(frame);

        boolean registrationFinished = false;

        while (!registrationFinished) {
          if (registrationPage.isRegistrationComplete()) {
            //TODO: save userdata here
            registrationFinished = true;

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
              while (!add_cashAccountPage.isSubmitted() && !add_cashAccountPage.isBackWanted()) {
                ;
              }
              if(add_cashAccountPage.isSubmitted()) {
                user.addAccount(new Cash(add_cashAccountPage.getAccName(),add_cashAccountPage.getLimit(),add_cashAccountPage.getCurrency()));
              }
              else {
                accountTypePage.configureFrame(frame);
              }
              break;
            case DEBIT:
              add_debitAccountPage.configureFrame(frame);
              while (!add_debitAccountPage.isSubmitted() && !add_debitAccountPage.isBackWanted()) {
                ;
              }
              if(add_debitAccountPage.isSubmitted()) {
                user.addAccount(new DebitCard(add_debitAccountPage.getAccName(),add_debitAccountPage.getBankName_String(),add_debitAccountPage.getLimit(),add_debitAccountPage.getIBAN()));
              }
              else {
                accountTypePage.configureFrame(frame);
              }
              break;
            case CREDIT:
              add_creditAccountPage.configureFrame(frame);
              while (!add_creditAccountPage.isSubmitted() && !add_creditAccountPage.isBackWanted()) {
                ;
              }
              if(add_creditAccountPage.isSubmitted()) {
                user.addAccount(new CreditCard(add_creditAccountPage.getAccName(),add_creditAccountPage.getBankName_String(),add_creditAccountPage.getLimit(),add_creditAccountPage.getExpiry()));
              }
              else {
                accountTypePage.configureFrame(frame);
              }
              break;
            case STOCKS:
              add_stockAccountPage.configureFrame(frame);
              while (!add_stockAccountPage.isSubmitted() && !add_stockAccountPage.isBackWanted()) {
                ;
              }
              if(add_stockAccountPage.isSubmitted()) {
                user.addAccount(new Stocks(add_stockAccountPage.getAccName(),add_stockAccountPage.getBuyDate(),add_stockAccountPage.getLimit()));
              }
              else {
                accountTypePage.configureFrame(frame);
              }
              break;
          }
          //accountListPage.configureFrame(frame);
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
