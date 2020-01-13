package gui.list_pages;

import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import iteration.CustomContainer;
import iteration.CustomIterator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import gui.main.AbstractPage;
import user.User;

/**
 * This is the main page of the GUI, which will be displayed after the log in. It contains a table
 * of all accounts, which can be pressed to get more details and transactions of them.
 *
 * @author Patrick Gmasz
 * @author Paul Kraft
 */
public class AccountListPage extends AbstractPage {
  private JLabel welcomeMessage;
  private JTable accountTable;
  private JScrollPane accountTablePane;
  private JButton newAccountButton;
  private JButton deleteButton;
  private JButton updateButton;
  private JButton summaryButton;

  private User user;

  private Account selectedAccount;
  private Account selectedAccountToDeleteOrUpdate;
  private volatile boolean newAccountWanted;
  private volatile boolean deleteWanted;
  private volatile boolean updateWanted;
  private volatile boolean summaryWanted;

  /**
   * Creates a new AccountListPage, which will load all needed components to a list.
   *
   * @param user The user object, who logged in.
   */
  public AccountListPage(User user) {
    this.user = user;
    createComponents();
  }

  /**
   * Returns a 2D-array of all accounts, which is needed for creating the JTable of the accounts.
   *
   * @param user The user object, which contains the accounts.
   * @return A 2D-array of accounts for the JTable, which has account number on first, account type
   *     on second, account name on third and account balance on fourth place.
   */
  private Object[][] getAccounts(User user) {
    CustomContainer<Account> userAccounts = user.getAccounts();

    int listSize = userAccounts == null ? 0 : userAccounts.size();

    Object[][] accounts = new Object[listSize][4];

    if (listSize > 0) {
      CustomIterator<Account> iterator = userAccounts.getIterator();
      int i = 0;

      while (iterator.hasNext()) {
        Account acc = iterator.next();

        accounts[i][0] = acc.getAccount_number();
        accounts[i][1] = getAccountType(acc);
        accounts[i][2] = acc.getName();
        accounts[i][3] = acc.getBalance();
        ++i;
      }
    }

    return accounts;
  }

  /**
   * Returns a String, which represents the account type. Needed for the table creation.
   *
   * @param account The account, which we want to display the type of.
   * @return A string of the account type.
   */
  private String getAccountType(Account account) {
    if (account instanceof Cash) {
      return "Cash";
    } else if (account instanceof Stocks) {
      return "Stock";
    } else if (account instanceof DebitCard) {
      return "Debit Card";
    } else if (account instanceof CreditCard) {
      return "Credit Card";
    } else {
      return null;
    }
  }

  /**
   * If the user double-clicks on a account in the table, a indicator variable will be set to
   * clicked account. This method returns the account, which the user wants to open.
   *
   * @return The account object, which the user wants to open.
   */
  public Account getSelectedAccount() {
    return selectedAccount;
  }

  /**
   * If the user singe-clicks on a account in the table, an indicator variable will be set to
   * clicked account. This method returns the account, which the user wants to delete or update.
   *
   * @return The account object, which the user wants to open.
   */
  public Account getSelectedAccountToDeleteOrUpdate() {
    return selectedAccountToDeleteOrUpdate;
  }

  /**
   * The page has a button, which the user can press if he wants to add a new account. Pressing the
   * button will set a boolean flag, that the button was pressed. This method returns the boolean,
   * it will be true, if the user wants to create a new account.
   *
   * @return The boolean flag, it will be true, if the user pressed the create-new-account button.
   */
  public boolean isNewAccountWanted() {
    return newAccountWanted;
  }

  /**
   * The page has a button, which the user can press if he wants to delete an account. Pressing the
   * button will set a boolean flag, that the button was pressed. This method returns the boolean,
   * it will be true, if the user wants to delete an account.
   *
   * @return The boolean flag, it will be true, if the user pressed the delete button.
   */
  public boolean isDeleteWanted() {
    return deleteWanted;
  }

  /**
   * The page has a button, which the user can press if he wants to update an account. Pressing the
   * button will set a boolean flag, that the button was pressed. This method returns the boolean,
   * it will be true, if the user wants to update an account.
   *
   * @return The boolean flag, it will be true, if the user pressed the update button.
   */
  public boolean isUpdateWanted() {
    return updateWanted;
  }

  /**
   * The page has a button, which the user can press if he wants to view a summary of all accounts.
   * Pressing the button will set a boolean flag, that the button was pressed. This method returns
   * the boolean, it will be true, if the user wants to view the summary.
   *
   * @return The boolean flag, it will be true, if the user pressed the create-new-account button.
   */
  public boolean isSummaryWanted() {
    return summaryWanted;
  }

  /**
   * This method creates all components, such as buttons and text fields, and adds it to a list. It
   * also sets every indicator variables to default and updates the account list of the user.
   */
  @Override
  protected void createComponents() {

    components = new ArrayList<>();
    selectedAccount = null;
    selectedAccountToDeleteOrUpdate = null;
    newAccountWanted = false;
    deleteWanted = false;
    updateWanted = false;
    summaryWanted = false;

    // WELCOME MESSAGE
    welcomeMessage = new JLabel();
    welcomeMessage.setText("Welcome, " + user.getFirstname() + " " + user.getLastname() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    welcomeMessage.setFont(new Font("Serif", Font.BOLD, 28));
    welcomeMessage.setBounds(100, 50, 550, 50);

    // NEW ACCOUNT BUTTON
    newAccountButton = new JButton("CREATE NEW ACCOUNT");
    newAccountButton.setBounds(100, 600, 400, 50);
    newAccountButton.setBorder(new LineBorder(Color.BLACK, 2));
    newAccountButton.setFont(BUTTON_FONT);
    newAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    newAccountButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            newAccountWanted = true;
          }
        });

    // DELETE BUTTON
    deleteButton = new JButton("DELETE");
    deleteButton.setBounds(925, 50, 150, 50);
    deleteButton.setFont(BUTTON_FONT);
    deleteButton.setBorder(new LineBorder(Color.BLACK, 2));
    deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    deleteButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (selectedAccountToDeleteOrUpdate == null) {
              JOptionPane.showMessageDialog(
                  null,
                  "You need to select an account!",
                  "Account Selection Error",
                  JOptionPane.WARNING_MESSAGE);
            } else {
              int input =
                  JOptionPane.showConfirmDialog(
                      null,
                      "Are you sure you want to delete "
                          + selectedAccountToDeleteOrUpdate.getName()
                          + "?",
                      "Confirm",
                      JOptionPane.YES_NO_OPTION);
              if (input == 0) {
                deleteWanted = true;
              }
            }
          }
        });

    // UPDATE BUTTON
    updateButton = new JButton("UPDATE");
    updateButton.setBounds(700, 50, 150, 50);
    updateButton.setFont(BUTTON_FONT);
    updateButton.setBorder(new LineBorder(Color.BLACK, 2));
    updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    updateButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (selectedAccountToDeleteOrUpdate == null) {
              JOptionPane.showMessageDialog(
                  null,
                  "You need to select an account!",
                  "Account Selection Error",
                  JOptionPane.WARNING_MESSAGE);
            } else {
              updateWanted = true;
            }
          }
        });

    // SUMMARY BUTTON
    summaryButton = new JButton("SUMMARY OF ALL ACCOUNTS");
    summaryButton.setBounds(675, 600, 400, 50);
    summaryButton.setFont(BUTTON_FONT);
    summaryButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    summaryButton.setBorder(new LineBorder(Color.BLACK, 2));
    summaryButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            summaryWanted = true;
          }
        });

    // TABLE
    Object[] columnNames = {"Account ID", "Account Type", "Account Name", "Balance"};
    accountTable = new JTable(getAccounts(user), columnNames);
    accountTable.setFont(TEXTFIELD_FONT);
    accountTable.setRowHeight(70);
    accountTable.setDefaultEditor(Object.class, null);

    DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
    tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);

    for (int i = 0; i < 4; ++i) {
      accountTable.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
    }

    // TABLE MOUSE LISTENER
    accountTable.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent mouseEvent) {
            JTable table = (JTable) mouseEvent.getSource();
            Point point = mouseEvent.getPoint();
            int row = table.rowAtPoint(point);
            if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
              CustomContainer<Account> accountList = user.getAccounts();

              CustomIterator<Account> iterator = accountList.getIterator();

              while (iterator.hasNext()) {
                Account acc = iterator.next();
                if (acc.getAccount_number() == (Integer) accountTable.getValueAt(row, 0)) {
                  selectedAccount = acc;
                  break;
                }
              }
            }
            if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
              CustomContainer<Account> accountList = user.getAccounts();

              CustomIterator<Account> iterator = accountList.getIterator();

              while (iterator.hasNext()) {
                Account acc = iterator.next();
                if (acc.getAccount_number() == (Integer) accountTable.getValueAt(row, 0)) {
                  selectedAccountToDeleteOrUpdate = acc;
                  break;
                }
              }
            }
          }
        });

    // SCROLL PANE
    accountTablePane = new JScrollPane(accountTable);
    accountTablePane.setBounds(100, 150, 975, 370);

    components = new ArrayList<>();
    components.add(welcomeMessage);
    components.add(accountTablePane);
    components.add(newAccountButton);
    components.add(deleteButton);
    components.add(updateButton);
    components.add(summaryButton);
  }

  /**
   * Sets the title of the JFrame to Account List.
   *
   * @param frame The JFrame, where to change the title.
   */
  @Override
  protected void resetTitle(JFrame frame) {
    frame.setTitle("Account List");
  }
}
