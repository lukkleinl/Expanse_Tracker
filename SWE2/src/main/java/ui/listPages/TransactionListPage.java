package ui.listPages;

import accounts.Account;
import accounts.BankAccount;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import swe_IteratorPattern.CustomContainer;
import swe_IteratorPattern.CustomIterator;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import ui.main.InterfacePage;
import user.User;

/**
 * This Page Displays the Transactions of 1 Account of 1 User and has 2 Buttons to start a new Deposit. Implements the Interface 'InterfacePage'
 * @author Paul Kraft
 */

public class TransactionListPage implements InterfacePage {

  public final static int FRAME_WIDTH = 1200;
  public final static int FRAME_HEIGHT = 800;

  public final static int SHIFT_LEFT = 0;
  public final static int OFFSET_Y = 50; // THE AMT OF PIXELS THE TEXT FIELDS ARE OFFSET TO THEIR CORRESPONDING JPANELS!

  private ArrayList<JComponent> components;
  private JLabel IntroText;
  private String IntroTextMessage;

  private JLabel AccountName;
  private JLabel Balance;
  private JLabel Limit;

  private JLabel CustomLabel2;
  private JLabel CustomLabel3;
  private JButton BackButton;
  private JButton newDepositButton;
  private JButton newPayoutButton;

  private JTable transactionTable;
  private JScrollPane scrollPane;

  private volatile boolean newDepositWanted;
  private volatile boolean newPayoutWanted;
  private volatile boolean backWanted;

  private Account acc;
  private User user;

  /**
   * Creates a new TransactionListPage, which will load all needed components to a list.
   * @param acc who's transactions should be displayed.
   * @param user User who belongs to that account.
   */
  public TransactionListPage(Account acc, User user) {
    this.acc = acc;
    this.user = user;
    createComponents();
  }

  /**
   * @return current boolean value of neDepositWanted( whether the User wants to do a Deposit or not! )
   */
  public boolean isNewDepositWanted() {
    return newDepositWanted;
  }
  /**
   * @return current boolean value of newPayoutWanted( whether the User wants to do a Payout or not! )
   */
  public boolean isNewPayoutWanted() {
    return newPayoutWanted;
  }
  /**
   * @return current boolean value of backWanted( whether the User wants to go back or not! )
   */
  public boolean isBackWanted() {
    return backWanted;
  }
  /**
   * Configures the JFrame, adds all the components(resets the values of the JTextFields to hard coded default).
   * Names the Frame to 'List Transactions - Page'.
   * Sets the Size of the Frame. (Default is 1200 by 800).
   * calls revalidate() and repaint() on the Frame.
   * @param frame The JFrame, which components will be updated
   */
  public void configureFrame(JFrame frame) {
    createComponents();

    frame.setLayout(null);
    frame.setTitle("List Transactions - Page");
    frame.getContentPane().removeAll();
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    for (JComponent comp : components) {
      frame.add(comp);
    }
    frame.revalidate();
    frame.repaint();
  }

  private void createComponents() {
    newDepositWanted = false;
    newPayoutWanted = false;
    backWanted = false;

    components = new ArrayList<>();

    String[] TransactionDescription = {"Type", "Descriptions", "Amount", "Creation-Date",
        "Category"};
    CustomContainer<Transaction> transactionlist = user.getTransactions().get(acc.getAccount_number());
    int listSize = transactionlist == null ? 0 : transactionlist.size();
    String[][] TransactionList_VISU = new String[listSize][6];

    if (listSize > 0) {

      CustomIterator<Transaction> it = transactionlist.getIterator();

      int i = 0;

      while (it.hasNext()) {
        Transaction transtemp = it.next();

        if (transtemp instanceof Payout) {
          TransactionList_VISU[i][0] = "Payout";
          TransactionList_VISU[i][4] = ((Payout) transtemp).getPayoutCategory()
              .toString();
        } else {
          TransactionList_VISU[i][0] = "Deposit";
          TransactionList_VISU[i][4] = ((Deposit) transtemp).getDepositCategory()
              .toString();
        }

        TransactionList_VISU[i][1] = transtemp.getDescription();
        TransactionList_VISU[i][2] = "" + transtemp.getAmount();
        TransactionList_VISU[i][5] = "" + transtemp.getID();
        TransactionList_VISU[i++][3] = transtemp.getCreationDate().toString();


      }

    }

    transactionTable = new JTable(TransactionList_VISU, TransactionDescription);

    //https://stackoverflow.com/questions/9919230/disable-user-edit-in-jtable
    // MAKES THE ELEMENTS IN THE TABLE UNEDITABLE
    transactionTable.setDefaultEditor(Object.class, null);

    //https://stackoverflow.com/questions/10128064/jtable-selected-row-click-event
    transactionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent event) {

        int ID_OF_SELECTED_TRANSACTION = Integer
            .valueOf(TransactionList_VISU[transactionTable.getSelectedRow()][5]);

        System.out.println("selected Row Nr:" + transactionTable.getSelectedRow());
      }
    });

    scrollPane = new JScrollPane(transactionTable);
    scrollPane.setBounds(300, 100, 700, 450);
    components.add(scrollPane);

    IntroTextMessage = "Account-Type: ";

    IntroText = new JLabel();
    IntroText.setBounds(300, 10, 800, 50);

    AccountName = new JLabel("Account Name: " + acc.getName());
    AccountName.setBounds(10 + SHIFT_LEFT, 200, 300, 50);
    components.add(AccountName);

    Balance = new JLabel("Balance: " + acc.getBalance());
    Balance.setBounds(10 + SHIFT_LEFT, 250, 300, 50);
    components.add(Balance);

    Limit = new JLabel("Limit: " + acc.getLimit());
    Limit.setBounds(10 + SHIFT_LEFT, 300, 300, 50);
    components.add(Limit);

    CustomLabel2 = new JLabel();
    CustomLabel2.setBounds(10 + SHIFT_LEFT, 350, 300, 50);

    CustomLabel3 = new JLabel();
    CustomLabel3.setBounds(10 + SHIFT_LEFT, 400, 300, 50);

    if (acc instanceof BankAccount) {
      IntroTextMessage += "BankAccount ";
      CustomLabel2.setText("Bankname: " + ((BankAccount) acc).getBankName());
      CustomLabel3.setText("");
    } else if (acc instanceof Cash) {
      IntroTextMessage += "CashAccount ";
      CustomLabel2.setText("Currency: " + ((Cash) acc).getCurrency());
      CustomLabel3.setText("");
    } else if (acc instanceof CreditCard) {
      IntroTextMessage += "CreditCardAccount ";
      CustomLabel2.setText("Expiry-Date: " + ((CreditCard) acc).getExpiryDate());
      CustomLabel3.setText("");
    } else if (acc instanceof DebitCard) {
      IntroTextMessage += "DebitCardAccount ";
      CustomLabel2.setText("IBAN: " + ((DebitCard) acc).getIBAN());
      CustomLabel3.setText("");
    }

    IntroTextMessage += "  logged in as: " + user.getFirstname() + " " + user.getLastname();

    components.add(CustomLabel2);
    components.add(CustomLabel3);
    IntroText.setText(IntroTextMessage);
    components.add(IntroText);

    BackButton = new JButton("BACK");
    BackButton.setBounds(10, 10, 100, 50);
    components.add(BackButton);
    BackButton.addActionListener(new ActionListener() {
      @Override
      /**
       * sets backWanted to true
       */
      public void actionPerformed(ActionEvent e) {
        backWanted = true;
      }
    });

    newPayoutButton = new JButton("NEW PAYOUT");
    newPayoutButton.setBounds(400, 600, 200, 70);
    newPayoutButton.addActionListener(new ActionListener() {
      @Override
      /**
       * sets newPayoutWanted to true
       */
      public void actionPerformed(ActionEvent e) {
        newPayoutWanted = true;
      }
    });
    components.add(newPayoutButton);

    newDepositButton = new JButton("NEW DEPOST");
    newDepositButton.setBounds(700, 600, 200, 70);
    newDepositButton.addActionListener(new ActionListener() {
      @Override
      /**
       * sets newDepositWanted to true
       */
      public void actionPerformed(ActionEvent e) {
        newDepositWanted = true;
      }
    });
    components.add(newDepositButton);
  }
}