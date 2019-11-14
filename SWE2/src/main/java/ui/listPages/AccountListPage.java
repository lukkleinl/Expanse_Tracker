package ui.listPages;

import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import iteration.CustomContainer;
import iteration.CustomIterator;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import ui.main.InterfacePage;
import user.User;

/**
 * This is the main page of the GUI, which will be displayed after the log in.
 * It contains a table of all accounts, which can be pressed to get more details and transactions of them.
 *
 * @author Patrick Gmasz
 */
public class AccountListPage implements InterfacePage {

  private final int FRAME_WIDTH = 1200;
  private final int FRAME_HEIGHT = 800;

  private ArrayList<JComponent> components;

  private JLabel welcomeMessage;
  private JTable accountTable;
  private JScrollPane accountTablePane;
  private JButton newAccountButton;

  private User user;

  private Account selectedAccount;
  private volatile boolean newAccountWanted;

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
   * @return A 2D-array of accounts for the JTable, which has account number on first, account type on second, account name on third and account balance on fourth place.
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
   * Returns a String, which represents the account type.
   * Needed for the table creation.
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
   * If the user double-clicks on a account in the table, a indicator variable will be set to clicked account.
   * This method returns the account, which the user wants to open.
   *
   * @return The account object, which the user wants to open.
   */
  public Account getSelectedAccount() {
    return selectedAccount;
  }

  /**
   * The page has a button, which the user can press if he wants to add a new account.
   * Pressing the button will set a boolean flag, that the button was pressed.
   * This method returns the boolean, it will be true, if the user wants to create a new account.
   *
   * @return The boolean flag, it will be true, if the user pressed the create-new-account button.
   */
  public boolean isNewAccountWanted() {
    return newAccountWanted;
  }

  /**
   * This method updates the given JFrame with every components, e.g. buttons and tables, and also sets indicators to default.
   *
   * @param frame The JFrame, which components will be updated
   */
  public void configureFrame(JFrame frame) {
    createComponents();

    //frame.setVisible(false);
    frame.setTitle("Account Types");
    frame.getContentPane().removeAll();
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    for (JComponent comp : components) {
      frame.add(comp);
    }

    frame.revalidate();
    frame.repaint();
    //frame.setVisible(true);
  }

  /**
   * This method creates all components, such as buttons and text fields, and adds it to a list.
   * It also sets every indicator variables to default and updates the account list of the user.
   */
  private void createComponents() {

    selectedAccount = null;
    newAccountWanted = false;

    //WELCOME MESSAGE
    welcomeMessage = new JLabel();
    welcomeMessage.setText("Welcome, " + user.getFirstname() + " " + user.getLastname() + "!");
    welcomeMessage.setFont(new Font("Serif", Font.BOLD, 28));
    welcomeMessage.setBounds(100, 50, 1000, 50);

    //NEW ACCOUNT BUTTON
    newAccountButton = new JButton("CREATE NEW ACCOUNT");
    newAccountButton.setBounds(400, 600, 400, 50);
    newAccountButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        newAccountWanted = true;
      }
    });

    //TABLE
    Object[] columnNames = {"Nr.", "Account Type", "Account Name", "Balance"};
    accountTable = new JTable(getAccounts(user), columnNames);
    accountTable.setRowHeight(70);
    accountTable.setDefaultEditor(Object.class, null);

    DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
    tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);

    for (int i = 0; i < 4; ++i) {
      accountTable.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
    }

    //TABLE MOUSE LISTENER
    accountTable.addMouseListener(new MouseAdapter() {
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
              System.out.println(acc.getAccount_number() + " selected");
              break;
            }
          }
        }
      }
    });

    //SCROLL PANE
    accountTablePane = new JScrollPane(accountTable);
    accountTablePane.setBounds(100, 150, 975, 370);

    components = new ArrayList<>();
    components.add(welcomeMessage);
    components.add(accountTablePane);
    components.add(newAccountButton);
  }
}
