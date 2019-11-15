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
        if (loginPage.getUser().equals("admin") && loginPage.getPassword().equals("admin")) {
          //TODO: user = db.getUser(userid)
          user = TestUser.getTestUser();
          foundUser = true;
        } else {
          loginPage.configureFrame(frame);
          //TODO: display error message
        }
      } else if (loginPage.isRegistrationWanted()) {

        //OPEN REGISTRATION PAGE COMPONENTS
        registrationPage.configureFrame(frame);

        while (!registrationPage.isRegistrationComplete() && !registrationPage.isBackWanted());
          if (registrationPage.isRegistrationComplete()) {
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            //TODO: save userdata here
          }
          //OPEN LOGIN PAGE COMPONENTS AGAIN
          loginPage.configureFrame(frame);

      }
    }

    AccountListPage accountListPage = new AccountListPage(user);
    AccountTypePage accountTypePage = new AccountTypePage();
    AddCashAccountPage add_cashAccountPage = new AddCashAccountPage();
    AddDebitAccountPage add_debitAccountPage = new AddDebitAccountPage();
    AddStockAccountPage add_stockAccountPage = new AddStockAccountPage();
    AddCreditAccountPage add_creditAccountPage = new AddCreditAccountPage();
    AddDepositPage addDepositPage = new AddDepositPage();
    AddPayoutPage addPayoutPage = new AddPayoutPage();
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
              if (add_cashAccountPage.isSubmitted()) {
                user.addAccount(
                    new Cash(add_cashAccountPage.getAccName(), add_cashAccountPage.getLimit(),
                        add_cashAccountPage.getCurrency()));
              } else {
                accountTypePage.configureFrame(frame);
              }
              break;
            case DEBIT:
              add_debitAccountPage.configureFrame(frame);
              while (!add_debitAccountPage.isSubmitted() && !add_debitAccountPage.isBackWanted()) {
                ;
              }
              if (add_debitAccountPage.isSubmitted()) {
                user.addAccount(new DebitCard(add_debitAccountPage.getAccName(),
                    add_debitAccountPage.getBankName_String(), add_debitAccountPage.getLimit(),
                    add_debitAccountPage.getIBAN()));
              } else {
                accountTypePage.configureFrame(frame);
              }
              break;
            case CREDIT:
              add_creditAccountPage.configureFrame(frame);
              while (!add_creditAccountPage.isSubmitted() && !add_creditAccountPage
                  .isBackWanted()) {
                ;
              }
              if (add_creditAccountPage.isSubmitted()) {
                user.addAccount(new CreditCard(add_creditAccountPage.getAccName(),
                    add_creditAccountPage.getBankName_String(), add_creditAccountPage.getLimit(),
                    add_creditAccountPage.getExpiry()));
              } else {
                accountTypePage.configureFrame(frame);
              }
              break;
            case STOCKS:
              add_stockAccountPage.configureFrame(frame);
              while (!add_stockAccountPage.isSubmitted() && !add_stockAccountPage.isBackWanted()) {
                ;
              }
              if (add_stockAccountPage.isSubmitted()) {
                user.addAccount(
                    new Stocks(add_stockAccountPage.getAccName(), add_stockAccountPage.getBuyDate(),
                        add_stockAccountPage.getLimit()));
              } else {
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
          addPayoutPage.configureFrame(frame);
          while (!addPayoutPage.isSubmitted() && !addPayoutPage.isBackWanted()) {
            ;
          }

          if (addPayoutPage.isSubmitted()) {
            try {
              Payout payout = new Payout(new Date(), addPayoutPage.getAmount(), addPayoutPage.getCatego(), addPayoutPage.getDescription());
              user.addTransaction(payout, accountListPage.getSelectedAccount());
              //user.payOut(addPayoutPage.getCatego(), addPayoutPage.getAmount(), addPayoutPage.getDescription(), accountListPage.getSelectedAccount());
            } catch (Exception e) {
              System.out.println("ERR:" + e.getMessage()); //TODO BETTER
            }          }

          transactionListPage.configureFrame(frame);
        } else if (transactionListPage.isNewDepositWanted()) {
          addDepositPage.configureFrame(frame);
          while (!addDepositPage.isSubmitted() && !addDepositPage.isBackWanted()) {
            ;
          }

          if (addDepositPage.isSubmitted()) {
            try {
              Deposit deposit = new Deposit(new Date(), addDepositPage.getAmount(), addDepositPage.getCatego(), addPayoutPage.getDescription());
              user.addTransaction(deposit, accountListPage.getSelectedAccount());
              //user.deposit(addDepositPage.getCatego(), addDepositPage.getAmount(), addDepositPage.getDescription(), accountListPage.getSelectedAccount());
            } catch (Exception e) {
              System.out.println("ERR:" + e.getMessage()); //TODO BETTER
            }          }

          transactionListPage.configureFrame(frame);
        } else {
          accountListPage.configureFrame(frame);
        }
      }
    }


  }
}

