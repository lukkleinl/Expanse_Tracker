package gui.main;

import gui.addAccountPages.AddCashAccountPage;
import gui.addAccountPages.AddCreditAccountPage;
import gui.addAccountPages.AddDebitAccountPage;
import gui.addAccountPages.AddStockAccountPage;
import gui.addTransactionPages.AddDepositPage;
import gui.addTransactionPages.AddPayoutPage;
import gui.graphical_pages.GroupingPage;
import gui.list_pages.AccountListPage;
import gui.list_pages.AccountTypePage;
import gui.list_pages.AccountTypes;
import gui.list_pages.TransactionListPage;
import gui.login_pages.LoginPage;
import gui.login_pages.RegistrationPage;
import patterns.observing.Database;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.TransactionCreator;
import user.User;

/**
 * This class is contains the whole procedure of the GUI.
 *
 * @author Patrick Gmasz
 * @author Paul Kraft
 */
public class UserInterface {

  private User user;
  private Database data;

  /**
   * Returns a new UserInterface instance.
   */
  public UserInterface(){
    try
    {
      data=new Database();
      if (data.getUser("test") == null) {
        TestUser.getTestUser("test", "Max", "Mustermann", "test", data);
      }
    }catch (Exception e){
      System.out.println(e.getMessage() +" while creating user Facade in UserInterface Constructor!");
    }
  }

  /** This method starts the GUI and starts the whole procedure. */
  public void start() {
    JFrame frame = new JFrame(); // Main frame, which components will be updated.
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    LoginPage loginPage = new LoginPage();
    RegistrationPage registrationPage = new RegistrationPage();

    loginPage.configureFrame(frame); // starts frame with login page

    boolean foundUser = false;
    while (!foundUser) { // lets the login page opened until someone logged in successfully
      if (loginPage
          .isLoginWanted()) { // checks if login button got pressed, if yes, check if user id and password


        user=data.getUser(loginPage.getUser());
        if(user!=null && user.getPassword().equals(loginPage.getPassword())){
        //if (loginPage.getUser().equals("admin") && loginPage.getPassword().equals("admin")) {
          //user = TestUser.getTestUser();
          foundUser = true;
        } else {
          loginPage.configureFrame(frame);
        }
      } else if (loginPage
          .isRegistrationWanted()) { // check if registration button got pressed, if yes, open
                                     // registration page

        // OPEN REGISTRATION PAGE COMPONENTS
        registrationPage.configureFrame(frame);

        while (!registrationPage.isRegistrationComplete() && !registrationPage.isBackWanted()) {
          // wait until either registration or back button got pressed
        }
        if (registrationPage
            .isRegistrationComplete()) { // if registration button got pressed, save userdata
          try
          {
            if(data.getUser(registrationPage.getUser())==null)
            {
              new User(registrationPage.getUser(),
                       registrationPage.getFirstname(),
                       registrationPage.getLastname(),
                       registrationPage.getPassword(),data);
              JOptionPane.showMessageDialog(null, "User successfully added", "Info",JOptionPane.INFORMATION_MESSAGE);
            }
            else
              JOptionPane.showMessageDialog(null, "User already exists", "Error",JOptionPane.ERROR_MESSAGE);

          }catch (Exception e){ System.out.println(e +"while adding user with user Facade in UserInterface 101"); }
        }
        // OPEN LOGIN PAGE COMPONENTS AGAIN
        loginPage.configureFrame(
            frame); // after registration is complete or back button got pressed, open login page
                    // again
      }
    }

    // DEFINITION OF ALL PAGES

    AccountListPage accountListPage = new AccountListPage(user);
    AccountTypePage accountTypePage = new AccountTypePage(user);
    AddCashAccountPage add_cashAccountPage = new AddCashAccountPage(user);
    AddDebitAccountPage add_debitAccountPage = new AddDebitAccountPage(user);
    AddStockAccountPage add_stockAccountPage = new AddStockAccountPage(user);
    AddCreditAccountPage add_creditAccountPage = new AddCreditAccountPage(user);
    AddDepositPage addDepositPage = new AddDepositPage(user);
    AddPayoutPage addPayoutPage = new AddPayoutPage(user);
    TransactionListPage transactionListPage;
    GroupingPage groupingPage = new GroupingPage(user);

    // END DEFINITION OF PAGES

    accountListPage.configureFrame(frame); // open main page

    while (true) { // interface is open, until close button (X on top right) gets pressed

      while (!accountListPage.isDeleteWanted()
          && !accountListPage.isUpdateWanted()
          && !accountListPage.isNewAccountWanted()
          && !accountListPage.isSummaryWanted()
          && (accountListPage.getSelectedAccount() == null)) {}

      if (accountListPage
          .isNewAccountWanted()) { // if new account button gets pressed, open new account type page
        accountTypePage.configureFrame(frame);

        while (accountTypePage.getPageWanted() == AccountTypes.NONE
            && !accountTypePage.isBackWanted()) {
          // wait until either back button got pressed, or account type got selected
        }

        if (!accountTypePage
            .isBackWanted()) { // if account type got selected (instead of back button pressed)
          switch (accountTypePage.getPageWanted()) { // check, which account type got selected
            case CASH:
              add_cashAccountPage.configureFrame(frame); // open page to add cash account
              while (!add_cashAccountPage.isSubmitted() && !add_cashAccountPage.isBackWanted()) {
                // wait until either back button or submit button got pressed
              }
              if (add_cashAccountPage
                  .isSubmitted()) { // if submit button got pressed, add new account
                user.addAccount(
                    new Cash(
                        add_cashAccountPage.getAccountName(),
                        add_cashAccountPage.getLimit(),
                        add_cashAccountPage.getCurrency()));
              } else { // if back button got pressed, go to account type page again
                accountTypePage.configureFrame(frame);
              }
              break;
            case DEBIT:
              add_debitAccountPage.configureFrame(frame); // open page to add debit account
              while (!add_debitAccountPage.isSubmitted() && !add_debitAccountPage.isBackWanted()) {
                // wait until either back button or submit button got pressed
              }
              if (add_debitAccountPage
                  .isSubmitted()) { // if submit button got pressed, add new account
                user.addAccount(
                    new DebitCard(
                        add_debitAccountPage.getAccountName(),
                        add_debitAccountPage.getBankName(),
                        add_debitAccountPage.getLimit(),
                        add_debitAccountPage.getIBAN()));
              } else { // if back button got pressed, go to account type page again
                accountTypePage.configureFrame(frame);
              }
              break;
            case CREDIT:
              add_creditAccountPage.configureFrame(frame); // open page to add credit account
              while (!add_creditAccountPage.isSubmitted()
                  && !add_creditAccountPage.isBackWanted()) {
                // wait until either back button or submit button got pressed
              }
              if (add_creditAccountPage
                  .isSubmitted()) { // if submit button got pressed, add new account
                user.addAccount(
                    new CreditCard(
                        add_creditAccountPage.getAccountName(),
                        add_creditAccountPage.getBankName(),
                        add_creditAccountPage.getLimit(),
                        add_creditAccountPage.getExpiryDate()));
              } else { // if back button got pressed, go to account type page again
                accountTypePage.configureFrame(frame);
              }
              break;
            case STOCKS:
              add_stockAccountPage.configureFrame(frame); // open page to add stock account
              while (!add_stockAccountPage.isSubmitted() && !add_stockAccountPage.isBackWanted()) {
                // wait until either back button or submit button got pressed
              }
              if (add_stockAccountPage
                  .isSubmitted()) { // if submit button got pressed, add new account
                user.addAccount(
                    new Stocks(
                        add_stockAccountPage.getAccName(),
                        add_stockAccountPage.getBuyDate(),
                        add_stockAccountPage.getLimit()));
              } else { // if back button got pressed, go to account type page again
                accountTypePage.configureFrame(frame);
              }
              break;
          }
          accountListPage.configureFrame(frame);
        } else { // if back button at account type page got pressed, go back to main page
          accountListPage.configureFrame(frame);
        }
      } else if (accountListPage.getSelectedAccount()
          != null) { // if an account in the list got selected, open transaction list page of
                     // selected account
        transactionListPage = new TransactionListPage(accountListPage.getSelectedAccount(), user);
        transactionListPage.configureFrame(frame);

        while (!transactionListPage.isNewDepositWanted()
            && !transactionListPage.isNewPayoutWanted()
            && !transactionListPage.isBackWanted()
            && !transactionListPage.isDeleteWanted()
            && !transactionListPage.isUpdateWanted()) {
          // wait until either back, add payout, add deposit, delete wanted or update button got pressed
        }

        if (transactionListPage
            .isNewPayoutWanted()) { // if new pay out button got pressed, open page to add new pay
                                    // out
          addPayoutPage.configureFrame(frame);
          while (!addPayoutPage.isSubmitted() && !addPayoutPage.isBackWanted()) {
            while (!addPayoutPage.isSubmitted()
                && !addPayoutPage.isBackWanted()
                && !addPayoutPage.isRefreshWanted()) {
              // wait until either back button, submit button got pressed or refresh is wanted (Needed after adding a new transaction)
            }

            if (addPayoutPage
                .isSubmitted()) { // if submit button got pressed, add new payout to account of
              // the user
                Transaction payout =
                    TransactionCreator.newTransaction(
                        addPayoutPage.getCategory(),
                        addPayoutPage.getAmount(),
                        addPayoutPage.getDescription(),
                        user.getCategoryStore());
                user.applyAndSaveTransaction(payout, accountListPage.getSelectedAccount());

                if(accountListPage.getSelectedAccount().limitExceeded()) {
                  JOptionPane.showMessageDialog(null, "Balance is lower as the limit, after this transaction!", "Limit Warning",JOptionPane.WARNING_MESSAGE);
                }

            } else if (addPayoutPage.isBackWanted()) {
              transactionListPage.configureFrame(
                  frame); // open transaction list page of the account again
            } else { // refresh add payout page.
              addPayoutPage.configureFrame(frame);
            }
          }
        } else if (transactionListPage
            .isNewDepositWanted()) { // if new deposit button got pressed, open page to add new
                                     // deposit
          addDepositPage.configureFrame(frame);
          while (!addDepositPage.isSubmitted() && !addDepositPage.isBackWanted()) {
            while (!addDepositPage.isSubmitted()
                && !addDepositPage.isBackWanted()
                && !addDepositPage.isRefreshWanted()) {
              // wait until either back button, submit button got pressed or refresh is wanted (Needed after adding a new transaction)
            }

            if (addDepositPage
                .isSubmitted()) { // if submit button got pressed, add new deposit page to account
              // of the user
                Transaction deposit =
                    TransactionCreator.newTransaction(
                        addDepositPage.getCategory(),
                        addDepositPage.getAmount(),
                        addDepositPage.getDescription(),
                        user.getCategoryStore());
                user.applyAndSaveTransaction(deposit, accountListPage.getSelectedAccount());

                if(accountListPage.getSelectedAccount().limitExceeded()) {
                  JOptionPane.showMessageDialog(null, "Balance is lower as the limit, after this transaction!", "Limit Warning",JOptionPane.WARNING_MESSAGE);
                }

            } else if (addDepositPage.isBackWanted()) {
              transactionListPage.configureFrame(
                  frame); // open transaction list page of the account again
            } else {
              addDepositPage.configureFrame(frame);
            }
          }
        } else if (transactionListPage.isDeleteWanted()) { //if delete button got pressed, delete selected transaction
          user.deleteTransaction(
              accountListPage.getSelectedAccount().getAccount_number(),
              transactionListPage.getSelectedTransactionToDeleteOrUpdate());
        } else if (transactionListPage.isUpdateWanted()) { //if update button got pressed, open addPayoutPage with selected transaction
          if (transactionListPage.getSelectedTransactionToDeleteOrUpdate() instanceof Payout) { //Check, if wanted to update a Payout
            addPayoutPage.configureFrame(frame);
            addPayoutPage.updateFields(
                (Payout) transactionListPage.getSelectedTransactionToDeleteOrUpdate());
            while (!addPayoutPage.isSubmitted() && !addPayoutPage.isBackWanted()) {
              while (!addPayoutPage.isSubmitted()
                  && !addPayoutPage.isBackWanted()
                  && !addPayoutPage.isRefreshWanted()) {
                // wait until either back button, submit button got pressed or refresh is wanted (Needed after adding a new transaction)
              }

              if (addPayoutPage
                  .isSubmitted()) { // if submit button got pressed, add new payout to account of
                Payout transaction =
                    (Payout) transactionListPage.getSelectedTransactionToDeleteOrUpdate();
                float old_amount=transaction.getAmount();
                transaction.updatePayout(
                    transaction.getCreationDate(),
                    addPayoutPage.getAmount(),
                    addPayoutPage.getCategory(),
                    addPayoutPage.getDescription());
                user.updateTransaction(
                    accountListPage.getSelectedAccount().getAccount_number(),transaction, old_amount);

                if(accountListPage.getSelectedAccount().limitExceeded()) {
                  JOptionPane.showMessageDialog(null, "Balance is lower as the limit, after this transaction!", "Limit Warning",JOptionPane.WARNING_MESSAGE);
                }

              } else if (addPayoutPage.isBackWanted()) {
                transactionListPage.configureFrame(
                    frame); // open transaction list page of the account again
              } else { // refresh add payout page.
                addPayoutPage.configureFrame(frame);
              }
            }
          } else { //if transaction to update was no payout, it was a deposit
            addDepositPage.configureFrame(frame);
            addDepositPage.updateFields(
                (Deposit) transactionListPage.getSelectedTransactionToDeleteOrUpdate());
            while (!addDepositPage.isSubmitted() && !addDepositPage.isBackWanted()) {
              while (!addDepositPage.isSubmitted()
                  && !addDepositPage.isBackWanted()
                  && !addDepositPage.isRefreshWanted()) {
                // wait until either back button, submit button got pressed or refresh is wanted (Needed after adding a new transaction)
              }

              if (addDepositPage
                  .isSubmitted()) { // if submit button got pressed, add new deposit page to account
                Deposit transaction =
                    (Deposit) transactionListPage.getSelectedTransactionToDeleteOrUpdate();
                float old_amount=transaction.getAmount();
                transaction.updateDeposit(
                    transaction.getCreationDate(),
                    addDepositPage.getAmount(),
                    addDepositPage.getCategory(),
                    addDepositPage.getDescription());
                user.updateTransaction(
                    accountListPage.getSelectedAccount().getAccount_number(), transaction,old_amount);

                if(accountListPage.getSelectedAccount().limitExceeded()) {
                  JOptionPane.showMessageDialog(null, "Balance is lower as the limit, after this transaction!", "Limit Warning",JOptionPane.WARNING_MESSAGE);
                }

              } else if (addDepositPage.isBackWanted()) {
                transactionListPage.configureFrame(
                    frame); // open transaction list page of the account again
              } else {
                addDepositPage.configureFrame(frame);
              }
            }
          }
        } else { // if back button of transaction list page got pressed, open account list page
                 // again
          accountListPage.configureFrame(frame);
        }
      } else if (accountListPage.isUpdateWanted()) { //if update account button got pressed, check the type of the selected account
        if (accountListPage.getSelectedAccountToDeleteOrUpdate() instanceof Cash) {
          add_cashAccountPage.configureFrame(frame);
          add_cashAccountPage.updateFields(
              (Cash) accountListPage.getSelectedAccountToDeleteOrUpdate());
          while (!add_cashAccountPage.isSubmitted() && !add_cashAccountPage.isBackWanted()) {
            // wait until either back button or submit button got pressed
          }
          if (add_cashAccountPage.isSubmitted()) { // if submit button got pressed, update account
            Cash account =
                new Cash(
                    add_cashAccountPage.getAccountName(),
                    add_cashAccountPage.getLimit(),
                    add_cashAccountPage.getCurrency());
            user.updateAccount(accountListPage.getSelectedAccountToDeleteOrUpdate(), account);
            accountListPage.configureFrame(frame);
          } else { // if back button got pressed, go to account type page again
            accountListPage.configureFrame(frame);
          }
        } else if (accountListPage.getSelectedAccountToDeleteOrUpdate() instanceof CreditCard) {
          add_creditAccountPage.configureFrame(frame);
          add_creditAccountPage.updateFields(
              (CreditCard) accountListPage.getSelectedAccountToDeleteOrUpdate());
          while (!add_creditAccountPage.isSubmitted() && !add_creditAccountPage.isBackWanted()) {
            // wait until either back button or submit button got pressed
          }
          if (add_creditAccountPage.isSubmitted()) { // if submit button got pressed, update account
            CreditCard account =
                new CreditCard(
                    add_creditAccountPage.getAccountName(),
                    add_creditAccountPage.getBankName(),
                    add_creditAccountPage.getLimit(),
                    add_creditAccountPage.getExpiryDate());
            user.updateAccount(accountListPage.getSelectedAccountToDeleteOrUpdate(), account);
            accountListPage.configureFrame(frame);
          } else { // if back button got pressed, go to account list page again
            accountListPage.configureFrame(frame);
          }
        } else if (accountListPage.getSelectedAccountToDeleteOrUpdate() instanceof DebitCard) {
          add_debitAccountPage.configureFrame(frame); // open page to add debit account
          add_debitAccountPage.updateFields(
              (DebitCard) accountListPage.getSelectedAccountToDeleteOrUpdate());
          while (!add_debitAccountPage.isSubmitted() && !add_debitAccountPage.isBackWanted()) {
            // wait until either back button or submit button got pressed
          }
          if (add_debitAccountPage.isSubmitted()) { // if submit button got pressed, add new account
            DebitCard account =
                new DebitCard(
                    add_debitAccountPage.getAccountName(),
                    add_debitAccountPage.getBankName(),
                    add_debitAccountPage.getLimit(),
                    add_debitAccountPage.getIBAN());
            user.updateAccount(accountListPage.getSelectedAccountToDeleteOrUpdate(), account);
            accountListPage.configureFrame(frame);
          } else { // if back button got pressed, go to account type page again
            accountListPage.configureFrame(frame);
          }
        } else {
          add_stockAccountPage.configureFrame(frame); // open page to add stock account
          add_stockAccountPage.updateFields(
              (Stocks) accountListPage.getSelectedAccountToDeleteOrUpdate());
          while (!add_stockAccountPage.isSubmitted() && !add_stockAccountPage.isBackWanted()) {
            // wait until either back button or submit button got pressed
          }
          if (add_stockAccountPage.isSubmitted()) { // if submit button got pressed, add new account
            Stocks account =
                new Stocks(
                    add_stockAccountPage.getAccName(),
                    add_stockAccountPage.getBuyDate(),
                    add_stockAccountPage.getLimit());
            user.updateAccount(accountListPage.getSelectedAccountToDeleteOrUpdate(), account);
            accountListPage.configureFrame(frame);
          } else { // if back button got pressed, go to account type page again
            accountListPage.configureFrame(frame);
          }
        }
      } else if (accountListPage.isDeleteWanted()) { //if delete button got pressed, delete pressed account
        user.deleteAccount(accountListPage.getSelectedAccountToDeleteOrUpdate());
        accountListPage.configureFrame(frame);
      } else { //only last option was, that grouping page button got pressed.
        groupingPage.configureFrame(frame);
        while (!groupingPage.isBackWanted() && !groupingPage.isRefreshWanted()) {}

        if (groupingPage.isRefreshWanted()) {
          groupingPage.configureFrame(frame);
        } else {
          accountListPage.configureFrame(frame);
        }
      }
    }
  }
}
