package ui.main;

import java.util.Date;
import javax.swing.JFrame;

import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import transactions.Deposit;
import transactions.Payout;
import ui.addAccountPages.AddCashAccountPage;
import ui.addAccountPages.AddCreditAccountPage;
import ui.addAccountPages.AddDebitAccountPage;
import ui.addAccountPages.AddStockAccountPage;
import ui.addTransactionPages.AddDepositPage;
import ui.addTransactionPages.AddPayoutPage;
import ui.listPages.AccountListPage;
import ui.listPages.AccountTypePage;
import ui.listPages.AccountTypes;
import ui.listPages.TransactionListPage;
import ui.login.LoginPage;
import ui.login.RegistrationPage;
import ui.TestUser;
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

    JFrame frame = new JFrame(); //Main frame, which components will be updated.
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    loginPage.configureFrame(frame); //starts frame with login page

    boolean foundUser = false;
    while (!foundUser) { //lets the login page opened until someone logged in successfully
      //TODO: check if user exists and if password is correct
      if (loginPage.isLoginWanted()) { //checks if login button got pressed, if yes, check if user id and password
        if (loginPage.getUser().equals("admin") && loginPage.getPassword().equals("admin")) {
          //TODO: user = db.getUser(userid)
          user = TestUser.getTestUser();
          foundUser = true;
        } else {
          loginPage.configureFrame(frame);
          //TODO: display error message
        }
      } else if (loginPage.isRegistrationWanted()) { //check if registration button got pressed, if yes, open registration page

        //OPEN REGISTRATION PAGE COMPONENTS
        registrationPage.configureFrame(frame);

        while (!registrationPage.isRegistrationComplete() && !registrationPage.isBackWanted()); //wait until either registration or back button got pressed
          if (registrationPage.isRegistrationComplete()) { //if registration button got pressed, save userdata
            try {
              Thread.sleep(1000); //wait one second, before login page gets opened, so successful message can be read
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            //TODO: save userdata here
          }
          //OPEN LOGIN PAGE COMPONENTS AGAIN
          loginPage.configureFrame(frame); //after registration is complete or back button got pressed, open login page again
      }
    }

    //DEFINITION OF ALL PAGES

    AccountListPage accountListPage = new AccountListPage(user);
    AccountTypePage accountTypePage = new AccountTypePage();
    AddCashAccountPage add_cashAccountPage = new AddCashAccountPage();
    AddDebitAccountPage add_debitAccountPage = new AddDebitAccountPage();
    AddStockAccountPage add_stockAccountPage = new AddStockAccountPage();
    AddCreditAccountPage add_creditAccountPage = new AddCreditAccountPage();
    AddDepositPage addDepositPage = new AddDepositPage();
    AddPayoutPage addPayoutPage = new AddPayoutPage();
    TransactionListPage transactionListPage;

    //END DEFINITION OF PAGES

    accountListPage.configureFrame(frame); //open main page

    while (true) { //interface is open, until close button (X on top right) gets pressed

      if (accountListPage.isNewAccountWanted()) { //if new account button gets pressed, open new account type page
        accountTypePage.configureFrame(frame);

        while (accountTypePage.getPageWanted() == AccountTypes.NONE && !accountTypePage.isBackWanted()) {
          //wait until either back button got pressed, or account type got selected
        }

        if (!accountTypePage.isBackWanted()) { //if account type got selected (instead of back button pressed)
          switch (accountTypePage.getPageWanted()) { //check, which account type got selected
            case CASH:
              add_cashAccountPage.configureFrame(frame); //open page to add cash account
              while (!add_cashAccountPage.isSubmitted() && !add_cashAccountPage.isBackWanted()) {
                //wait until either back button or submit button got pressed
              }
              if (add_cashAccountPage.isSubmitted()) { //if submit button got pressed, add new account
                user.addAccount(
                    new Cash(add_cashAccountPage.getAccName(), add_cashAccountPage.getLimit(),
                        add_cashAccountPage.getCurrency()));
              } else { //if back button got pressed, go to account type page again
                accountTypePage.configureFrame(frame);
              }
              break;
            case DEBIT:
              add_debitAccountPage.configureFrame(frame); //open page to add debit account
              while (!add_debitAccountPage.isSubmitted() && !add_debitAccountPage.isBackWanted()) {
                //wait until either back button or submit button got pressed
              }
              if (add_debitAccountPage.isSubmitted()) { //if submit button got pressed, add new account
                user.addAccount(new DebitCard(add_debitAccountPage.getAccName(),
                    add_debitAccountPage.getBankName_String(), add_debitAccountPage.getLimit(),
                    add_debitAccountPage.getIBAN()));
              } else { //if back button got pressed, go to account type page again
                accountTypePage.configureFrame(frame);
              }
              break;
            case CREDIT:
              add_creditAccountPage.configureFrame(frame); //open page to add credit account
              while (!add_creditAccountPage.isSubmitted() && !add_creditAccountPage
                  .isBackWanted()) {
                //wait until either back button or submit button got pressed
              }
              if (add_creditAccountPage.isSubmitted()) { //if submit button got pressed, add new account
                user.addAccount(new CreditCard(add_creditAccountPage.getAccName(),
                    add_creditAccountPage.getBankName_String(), add_creditAccountPage.getLimit(),
                    add_creditAccountPage.getExpiry()));
              } else { //if back button got pressed, go to account type page again
                accountTypePage.configureFrame(frame);
              }
              break;
            case STOCKS:
              add_stockAccountPage.configureFrame(frame); //open page to add stock account
              while (!add_stockAccountPage.isSubmitted() && !add_stockAccountPage.isBackWanted()) {
                //wait until either back button or submit button got pressed
              }
              if (add_stockAccountPage.isSubmitted()) { //if submit button got pressed, add new account
                user.addAccount(
                    new Stocks(add_stockAccountPage.getAccName(), add_stockAccountPage.getBuyDate(),
                        add_stockAccountPage.getLimit()));
              } else { //if back button got pressed, go to account type page again
                accountTypePage.configureFrame(frame);
              }
              break;
          }
        } else { //if back button at account type page got pressed, go back to main page
          accountListPage.configureFrame(frame);
        }
      } else if (accountListPage.getSelectedAccount() != null) { //if an account in the list got selected, open transaction list page of selected account
        transactionListPage = new TransactionListPage(accountListPage.getSelectedAccount(), user);
        transactionListPage.configureFrame(frame);

        while (!transactionListPage.isNewDepositWanted() && !transactionListPage.isNewPayoutWanted()
            && !transactionListPage.isBackWanted()) {
          //wait until either back, add payout or add deposit button got pressed
        }

        if (transactionListPage.isNewPayoutWanted()) { //if new pay out button got pressed, open page to add new pay out
          addPayoutPage.configureFrame(frame);
          while (!addPayoutPage.isSubmitted() && !addPayoutPage.isBackWanted()) {
            //wait until either back button or submit button got pressed
          }

          if (addPayoutPage.isSubmitted()) { //if submit button got pressed, add new payout to account of the user
            try {
              Payout payout = new Payout(new Date(), addPayoutPage.getAmount(), addPayoutPage.getCatego(), addPayoutPage.getDescription());
              user.addTransaction(payout, accountListPage.getSelectedAccount());
            } catch (Exception e) {
              System.out.println("ERR:" + e.getMessage()); //TODO BETTER
            }          }

          transactionListPage.configureFrame(frame); // open transaction list page of the account again

        } else if (transactionListPage.isNewDepositWanted()) { //if new deposit button got pressed, open page to add new deposit
          addDepositPage.configureFrame(frame);
          while (!addDepositPage.isSubmitted() && !addDepositPage.isBackWanted()) {
            //wait until either back button or submit button got pressed
          }

          if (addDepositPage.isSubmitted()) { //if submit button got pressed, add new deposit page to account of the user
            try {
              Deposit deposit = new Deposit(new Date(), addDepositPage.getAmount(), addDepositPage.getCatego(), addDepositPage.getDescription());
              user.addTransaction(deposit, accountListPage.getSelectedAccount());
            } catch (Exception e) {
              System.out.println("ERR:" + e.getMessage()); //TODO BETTER
            }          }

          transactionListPage.configureFrame(frame); //open transaction list page of the account again

        } else { //if back button of transaction list page got pressed, open account list page again
          accountListPage.configureFrame(frame);
        }
      }
    }


  }
}
