package ui;

import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
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
import user.User;

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
  private boolean newAccountWanted;

  public AccountListPage(User user) {
    this.user = user;

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

  //Returns a string if the account type, needed for table creation.
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

  public Account getSelectedAccount() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return selectedAccount;
  }

  public boolean isNewAccountWanted() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return newAccountWanted;
  }

  public void configureFrame(JFrame frame) {
    newAccountWanted = false;
    selectedAccount = null;

    frame.setVisible(false);
    frame.setTitle("Account Types");
    frame.getContentPane().removeAll();
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    for (JComponent comp : components) {
      frame.add(comp);
    }

    frame.revalidate();
    frame.repaint();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    AccountListPage accountListPage = new AccountListPage(TestUser.getTestUser());
    accountListPage.configureFrame(frame);
  }
}
