package ui.listPages;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import accounts.*;
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

  private JLabel accountNameHeader;
  private JLabel balanceHeader;
  private JLabel limitHeader;
  private JLabel customDescriptionHeader_1;
  private JLabel customDescriptionHeader_2;

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

    String[] transactionDescriptions = {"Type", "Category", "Description", "Creation-Date","Amount"};
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
          transactionList_VISU[i][1] = ((Payout) transtemp).getPayoutCategory()
              .toString();
        } else {
          transactionList_VISU[i][0] = "Deposit";
          transactionList_VISU[i][1] = ((Deposit) transtemp).getCategory()
              .toString();
        }

        transactionList_VISU[i][2] = transtemp.getDescription();
        transactionList_VISU[i][4] = "" + transtemp.getAmount();
        transactionList_VISU[i][5] = Integer.toString(transtemp.getID());
        transactionList_VISU[i++][3] = transtemp.getFormattedCreationDate();
      }

    }

    transactionTable = new JTable(transactionList_VISU, transactionDescriptions);
    transactionTable.setFont(TEXTFIELD_FONT);
    transactionTable.setRowHeight(70);


    DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
    tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);

    for (int i = 0; i < 5; ++i) {
      transactionTable.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
    }

    //https://stackoverflow.com/questions/9919230/disable-user-edit-in-jtable
    // MAKES THE ELEMENTS IN THE TABLE UNEDITABLE
    transactionTable.setDefaultEditor(Object.class, null);

    //https://stackoverflow.com/questions/10128064/jtable-selected-row-click-event
    transactionTable.getSelectionModel().addListSelectionListener(event -> {
      /*
      int ID_OF_SELECTED_TRANSACTION = Integer
          .valueOf(transactionList_VISU[transactionTable.getSelectedRow()][5]);

      System.out.println("selected Row Nr:" + transactionTable.getSelectedRow());*/
    });

    scrollPane = new JScrollPane(transactionTable);
    scrollPane.setBounds(225, 100, 900, 450);
    components.add(scrollPane);

    introTextMessage = "Currently logged in as: " + user.getFirstname() + " " + user.getLastname() + ".          Account Type: ";


    introTextLabel = new JLabel(introTextMessage);
    introTextLabel.setBounds(225, 10, 1000, 50);
    introTextLabel.setFont(HEADER_FONT);
    components.add(introTextLabel);

    accountNameHeader = new JLabel("Account Name:");
    accountNameHeader.setBounds(10,100,300,50);
    accountNameHeader.setFont(new Font("Serif",Font.BOLD,19));
    components.add(accountNameHeader);

    accountName_VisualizationLabel = new JLabel(account.getName());
    accountName_VisualizationLabel.setBounds(10, 130, 210, 50);
    accountName_VisualizationLabel.setFont(new Font("Serif", Font.PLAIN, 18));
    components.add(accountName_VisualizationLabel);

    balanceHeader = new JLabel("Balance:");
    balanceHeader.setBounds(10,200,300,50);
    balanceHeader.setFont(new Font("Serif",Font.BOLD,19));
    components.add(balanceHeader);

    balance_VisualizationLabel = new JLabel(Double.toString(account.getBalance()));
    balance_VisualizationLabel.setBounds(10, 230, 300, 50);
    balance_VisualizationLabel.setFont(new Font("Serif", Font.PLAIN, 18));
    components.add(balance_VisualizationLabel);

    limitHeader = new JLabel("Limit:");
    limitHeader.setBounds(10,300,300,50);
    limitHeader.setFont((new Font("Serif", Font.BOLD, 19)));
    components.add(limitHeader);

    limit_VisualizationLabel = new JLabel(Float.toString(account.getLimit()));
    limit_VisualizationLabel.setBounds(10, 330, 300, 50);
    limit_VisualizationLabel.setFont(new Font("Serif", Font.PLAIN, 18));
    components.add(limit_VisualizationLabel);

    customDescriptionHeader_1 = new JLabel();
    customDescriptionHeader_1.setBounds(10,400,300,50);
    customDescriptionHeader_1.setFont(new Font("Serif", Font.BOLD, 19));
    components.add(customDescriptionHeader_1);

    customDescriptionLabel_1 = new JLabel();
    customDescriptionLabel_1.setBounds(10, 430, 300, 50);
    customDescriptionLabel_1.setFont(new Font("Serif", Font.PLAIN, 18));

    customDescriptionHeader_2 = new JLabel();
    customDescriptionHeader_2.setBounds(10,500, 300,50);
    customDescriptionHeader_2.setFont(new Font("Serif", Font.BOLD, 19));
    components.add(customDescriptionHeader_2);

    customDescriptionLabel_2 = new JLabel();
    customDescriptionLabel_2.setBounds(10, 530, 300, 50);
    customDescriptionLabel_2.setFont(new Font("Serif", Font.PLAIN, 18));

    if (account instanceof Stocks) {
      introTextMessage += "Stocks ";
      customDescriptionHeader_1.setText("Buy date:");
      Date buyDate = ((Stocks) account).getBuyDate();
      customDescriptionLabel_1.setText(buyDate.getDay()-1+"."+buyDate.getMonth()+"."+buyDate.getYear());
      customDescriptionHeader_2.setText("");
      customDescriptionLabel_2.setText("");
    } else if (account instanceof Cash) {
      introTextMessage += "Cash Account ";
      customDescriptionHeader_1.setText("Currency:");
      customDescriptionLabel_1.setText(((Cash) account).getCurrency());
      customDescriptionLabel_2.setText("");
    } else if (account instanceof CreditCard) {
      introTextMessage += "CreditCard Account ";
      customDescriptionHeader_1.setText("Expiry date:");
      Date expireDate = ((CreditCard) account).getExpiryDate();
      customDescriptionLabel_1.setText(expireDate.getDay()-1+"."+expireDate.getMonth()+"."+expireDate.getYear());
      customDescriptionLabel_2.setText("");
    } else if (account instanceof DebitCard) {
      introTextMessage += "DebitCardAccount ";
      customDescriptionHeader_1.setText("IBAN:");
      customDescriptionLabel_1.setText(((DebitCard) account).getIBAN());
      customDescriptionLabel_1.setFont(new Font("Serif",Font.BOLD,15));
      customDescriptionLabel_2.setText("");
    }

    components.add(customDescriptionLabel_1);
    components.add(customDescriptionLabel_2);
    introTextLabel.setText(introTextMessage);
    components.add(introTextLabel);

    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
    backButton.setFont(BUTTON_FONT);
    backButton.setBorder(new LineBorder(Color.BLACK,2));
    components.add(backButton);
    backButton.addActionListener(e -> backWanted = true);

    newPayoutButton = new JButton("NEW PAYOUT");
    newPayoutButton.setBounds(400, 600, 200, 70);
    newPayoutButton.setFont(BUTTON_FONT);
    newPayoutButton.setBorder(new LineBorder(Color.BLACK,2));
    newPayoutButton.addActionListener(e -> newPayoutWanted = true);
    components.add(newPayoutButton);

    newDepositButton = new JButton("NEW DEPOST");
    newDepositButton.setBounds(700, 600, 200, 70);
    newDepositButton.setFont(BUTTON_FONT);
    newDepositButton.setBorder(new LineBorder(Color.BLACK,2));
    newDepositButton.addActionListener(e -> newDepositWanted = true);
    components.add(newDepositButton);
  }

  @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("List Transactions - Page");
  }
}