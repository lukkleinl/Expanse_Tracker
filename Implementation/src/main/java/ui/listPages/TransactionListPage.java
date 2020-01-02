package ui.listPages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import accounts.Account;
import accounts.BankAccount;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.grouping.GroupingBuilder;
import ui.main.AbstractPage;
import user.User;

/**
 * This Page Displays the Transactions of 1 Account of 1 User and has 2 Buttons to start a new Deposit. Implements the Interface 'InterfacePage'
 * @author Paul Kraft
 */

public class TransactionListPage extends AbstractPage {

  private final static int SHIFT_LEFT = 0;
  private final static int OFFSET_Y = 50; // THE AMT OF PIXELS THE TEXT FIELDS ARE OFFSET TO THEIR CORRESPONDING JPANELS!

  private JLabel introTextLabel;
  private String introTextMessage;

  private JLabel accountName_VisualizationLabel;
  private JLabel balance_VisualizationLabel;
  private JLabel limit_VisualizationLabel;

  private JLabel customDescriptionLabel_1;
  private JLabel customDescriptionLabel_2;
  private JButton backButton;
  private JButton newDepositButton;
  private JButton newPayoutButton;

  private JTable transactionTable;
  private JScrollPane scrollPane;

  private volatile boolean newDepositWanted;
  private volatile boolean newPayoutWanted;
  private volatile boolean backWanted;

  private final Account account;
  private final User user;

  /**
   * Creates a new TransactionListPage, which will load all needed components to a list.
   * @param acc who's transactions should be displayed.
   * @param user User who belongs to that account.
   */
  public TransactionListPage(final Account acc, final User user) {
    this.account = acc;
    this.user = user;
    this.createComponents();
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

  @Override
  protected void createComponents() {
    newDepositWanted = false;
    newPayoutWanted = false;
    backWanted = false;

    components = new ArrayList<>();

    String[] transactionDescriptions = {"Type", "Descriptions", "Amount", "Creation-Date",
    "Category"};
    CustomContainer<Transaction> transactionlist = user.getTransactionStore().getTransactions().get(account.getAccount_number());
    int listSize = transactionlist == null ? 0 : transactionlist.size();
    String[][] transactionList_VISU = new String[listSize][6];

    if (listSize > 0) {
      CustomIterator<Transaction> it = transactionlist.getIterator();
      int i = 0;

      while (it.hasNext()) {
        Transaction transtemp = it.next();

        if (transtemp instanceof Payout) {
          transactionList_VISU[i][0] = "Payout";
          transactionList_VISU[i][4] = ((Payout) transtemp).getPayoutCategory()
              .toString();
        } else {
          transactionList_VISU[i][0] = "Deposit";
          transactionList_VISU[i][4] = ((Deposit) transtemp).getCategory()
              .toString();
        }

        transactionList_VISU[i][1] = transtemp.getDescription();
        transactionList_VISU[i][2] = "" + transtemp.getAmount();
        transactionList_VISU[i][5] = "" + transtemp.getID();
        transactionList_VISU[i++][3] = transtemp.getFormattedCreationDate();


      }

    }

    transactionTable = new JTable(transactionList_VISU, transactionDescriptions);

    //https://stackoverflow.com/questions/9919230/disable-user-edit-in-jtable
    // MAKES THE ELEMENTS IN THE TABLE UNEDITABLE
    transactionTable.setDefaultEditor(Object.class, null);

    //https://stackoverflow.com/questions/10128064/jtable-selected-row-click-event
    transactionTable.getSelectionModel().addListSelectionListener(event -> {

      int ID_OF_SELECTED_TRANSACTION = Integer
          .valueOf(transactionList_VISU[transactionTable.getSelectedRow()][5]);

      System.out.println("selected Row Nr:" + transactionTable.getSelectedRow());
    });

    scrollPane = new JScrollPane(transactionTable);
    scrollPane.setBounds(225, 100, 900, 450);
    components.add(scrollPane);

    introTextMessage = "Account-Type: ";

    introTextLabel = new JLabel();
    introTextLabel.setBounds(300, 10, 800, 50);

    accountName_VisualizationLabel = new JLabel("Account Name: " + account.getName());
    accountName_VisualizationLabel.setBounds(10 + SHIFT_LEFT, 200, 300, 50);
    components.add(accountName_VisualizationLabel);

    balance_VisualizationLabel = new JLabel("Balance: " + account.getBalance());
    balance_VisualizationLabel.setBounds(10 + SHIFT_LEFT, 250, 300, 50);
    components.add(balance_VisualizationLabel);

    limit_VisualizationLabel = new JLabel("Limit: " + account.getLimit());
    limit_VisualizationLabel.setBounds(10 + SHIFT_LEFT, 300, 300, 50);
    components.add(limit_VisualizationLabel);

    customDescriptionLabel_1 = new JLabel();
    customDescriptionLabel_1.setBounds(10 + SHIFT_LEFT, 350, 300, 50);

    customDescriptionLabel_2 = new JLabel();
    customDescriptionLabel_2.setBounds(10 + SHIFT_LEFT, 400, 300, 50);

    if (account instanceof BankAccount) {
      introTextMessage += "BankAccount ";
      customDescriptionLabel_1.setText("Bankname: " + ((BankAccount) account).getBankName());
      customDescriptionLabel_2.setText("");
    } else if (account instanceof Cash) {
      introTextMessage += "CashAccount ";
      customDescriptionLabel_1.setText("Currency: " + ((Cash) account).getCurrency());
      customDescriptionLabel_2.setText("");
    } else if (account instanceof CreditCard) {
      introTextMessage += "CreditCardAccount ";
      customDescriptionLabel_1.setText("Expiry-Date: " + ((CreditCard) account).getExpiryDate());
      customDescriptionLabel_2.setText("");
    } else if (account instanceof DebitCard) {
      introTextMessage += "DebitCardAccount ";
      customDescriptionLabel_1.setText("IBAN: " + ((DebitCard) account).getIBAN());
      customDescriptionLabel_2.setText("");
    }

    introTextMessage += "  logged in as: " + user.getFirstname() + " " + user.getLastname();

    components.add(customDescriptionLabel_1);
    components.add(customDescriptionLabel_2);
    introTextLabel.setText(introTextMessage);
    components.add(introTextLabel);

    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
    components.add(backButton);
    backButton.addActionListener(e -> backWanted = true);

    newPayoutButton = new JButton("NEW PAYOUT");
    newPayoutButton.setBounds(400, 600, 200, 70);
    newPayoutButton.addActionListener(e -> newPayoutWanted = true);
    components.add(newPayoutButton);

    newDepositButton = new JButton("NEW DEPOST");
    newDepositButton.setBounds(700, 600, 200, 70);
    newDepositButton.addActionListener(e -> newDepositWanted = true);
    components.add(newDepositButton);
  }

  @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("List Transactions - Page");
  }
}