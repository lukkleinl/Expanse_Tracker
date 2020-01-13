package gui.list_pages;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import accounts.*;
import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import gui.main.AbstractPage;
import user.TransactionStore;
import user.User;

/**
 * This Page Displays the Transactions of 1 Account of 1 User and has 2 Buttons to start a new
 * Deposit. Implements the Interface 'InterfacePage'
 *
 * @author Paul Kraft
 * @author Patrick Gmasz
 */
public class TransactionListPage extends AbstractPage {

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
  private JButton deleteButton;
  private JButton updateButton;

  private JTable transactionTable;
  private JScrollPane scrollPane;

  private volatile boolean newDepositWanted;
  private volatile boolean newPayoutWanted;
  private volatile boolean backWanted;
  private volatile boolean deleteWanted;
  private volatile boolean updateWanted;

  private Transaction selectedTransactionToDeleteOrUpdate;

  private final Account account;
  private final User user;

  /**
   * Creates a new TransactionListPage, which will load all needed components to a list.
   *
   * @param acc who's transactions should be displayed.
   * @param user User who belongs to that account.
   */
  public TransactionListPage(final Account acc, final User user) {
    this.account = acc;
    this.user = user;
    this.createComponents();
  }

  /**
   * @return current boolean value of neDepositWanted( whether the User wants to do a Deposit or
   *     not! )
   */
  public boolean isNewDepositWanted() {
    return newDepositWanted;
  }
  /**
   * @return current boolean value of newPayoutWanted( whether the User wants to do a Payout or not!
   *     )
   */
  public boolean isNewPayoutWanted() {
    return newPayoutWanted;
  }
  /** @return current boolean value of backWanted( whether the User wants to go back or not! ) */
  public boolean isBackWanted() {
    return backWanted;
  }

  /** @return current boolean value of deleteWanted */
  public boolean isDeleteWanted() {
    return deleteWanted;
  }

  /** @return current boolean value of updateWanted */
  public boolean isUpdateWanted() {
    return updateWanted;
  }

  public Transaction getSelectedTransactionToDeleteOrUpdate() {
    return this.selectedTransactionToDeleteOrUpdate;
  }

  /**
   * creates all the Components that the JFrame should display(incl. Position,actionlisteners for Buttons, text etc)
   * also resets the booleans for backwanted, refreshwanted
   * is called from within the configureFrame in abstract Page.
   */
  @Override
  protected void createComponents() {
    newDepositWanted = false;
    newPayoutWanted = false;
    backWanted = false;
    deleteWanted = false;
    updateWanted = false;

    selectedTransactionToDeleteOrUpdate = null;

    components = new ArrayList<>();

    Object[] transactionDescriptions = {
      "ID", "Type", "Category", "Description", "Creation-Date", "Amount"
    };
    CustomContainer<Transaction> transactionlist =
        user.getTransactionStore().getTransactions().get(account.getAccount_number());
    int listSize = transactionlist == null ? 0 : transactionlist.size();
    Object[][] transactionList_VISU = new Object[listSize][6];

    if (listSize > 0) {
      CustomIterator<Transaction> it = transactionlist.getIterator();
      int i = 0;

      while (it.hasNext()) {
        Transaction transtemp = it.next();

        if (transtemp instanceof Payout) {
          transactionList_VISU[i][1] = "Payout";
          transactionList_VISU[i][2] = ((Payout) transtemp).getPayoutCategory().toString();
        } else {
          transactionList_VISU[i][1] = "Deposit";
          transactionList_VISU[i][2] = ((Deposit) transtemp).getCategory().toString();
        }

        transactionList_VISU[i][3] = transtemp.getDescription();
        transactionList_VISU[i][5] = transtemp.getAmount();
        transactionList_VISU[i][0] = transtemp.getID();
        transactionList_VISU[i++][4] = transtemp.getFormattedCreationDate();
      }
    }

    transactionTable = new JTable(transactionList_VISU, transactionDescriptions);
    transactionTable.setFont(TEXTFIELD_FONT);
    transactionTable.setRowHeight(70);

    DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
    tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);

    for (int i = 0; i < 6; ++i) {
      transactionTable.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
    }

    // https://stackoverflow.com/questions/9919230/disable-user-edit-in-jtable
    // MAKES THE ELEMENTS IN THE TABLE UNEDITABLE
    transactionTable.setDefaultEditor(Object.class, null);

    // TABLE MOUSE LISTENER
    transactionTable.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent mouseEvent) {
            JTable table = (JTable) mouseEvent.getSource();
            Point point = mouseEvent.getPoint();
            int row = table.rowAtPoint(point);
            if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
              TransactionStore transactionStore = user.getTransactionStore();

              CustomIterator<Transaction> iterator =
                  transactionStore.getTransactions().get(account.getAccount_number()).getIterator();

              while (iterator.hasNext()) {
                Transaction trans = iterator.next();
                if (trans.getID() == (Integer) transactionTable.getValueAt(row, 0)) {
                  selectedTransactionToDeleteOrUpdate = trans;
                  break;
                }
              }
            }
          }
        });

    scrollPane = new JScrollPane(transactionTable);
    scrollPane.setBounds(225, 150, 900, 450);
    components.add(scrollPane);

    introTextMessage = "Currently logged in as: " + user.getFirstname() + " " + user.getLastname();

    introTextLabel = new JLabel(introTextMessage);
    introTextLabel.setBounds(225, 10, 1000, 50);
    introTextLabel.setFont(HEADER_FONT);
    components.add(introTextLabel);

    accountNameHeader = new JLabel("Account Name:");
    accountNameHeader.setBounds(10, 100, 300, 50);
    accountNameHeader.setFont(new Font("Serif", Font.BOLD, 19));
    components.add(accountNameHeader);

    accountName_VisualizationLabel = new JLabel(account.getName());
    accountName_VisualizationLabel.setBounds(10, 130, 210, 50);
    accountName_VisualizationLabel.setFont(new Font("Serif", Font.PLAIN, 18));
    components.add(accountName_VisualizationLabel);

    balanceHeader = new JLabel("Balance:");
    balanceHeader.setBounds(10, 200, 300, 50);
    balanceHeader.setFont(new Font("Serif", Font.BOLD, 19));
    components.add(balanceHeader);

    balance_VisualizationLabel = new JLabel(Double.toString(account.getBalance()));
    balance_VisualizationLabel.setBounds(10, 230, 300, 50);
    balance_VisualizationLabel.setFont(new Font("Serif", Font.PLAIN, 18));
    components.add(balance_VisualizationLabel);

    limitHeader = new JLabel("Limit:");
    limitHeader.setBounds(10, 300, 300, 50);
    limitHeader.setFont((new Font("Serif", Font.BOLD, 19)));
    components.add(limitHeader);

    limit_VisualizationLabel = new JLabel(Float.toString(account.getLimit()));
    limit_VisualizationLabel.setBounds(10, 330, 300, 50);
    limit_VisualizationLabel.setFont(new Font("Serif", Font.PLAIN, 18));
    components.add(limit_VisualizationLabel);

    customDescriptionHeader_1 = new JLabel();
    customDescriptionHeader_1.setBounds(10, 400, 300, 50);
    customDescriptionHeader_1.setFont(new Font("Serif", Font.BOLD, 19));
    components.add(customDescriptionHeader_1);

    customDescriptionLabel_1 = new JLabel();
    customDescriptionLabel_1.setBounds(10, 430, 300, 50);
    customDescriptionLabel_1.setFont(new Font("Serif", Font.PLAIN, 18));

    customDescriptionHeader_2 = new JLabel("Account Type:");
    customDescriptionHeader_2.setBounds(10, 500, 300, 50);
    customDescriptionHeader_2.setFont(new Font("Serif", Font.BOLD, 19));
    components.add(customDescriptionHeader_2);

    customDescriptionLabel_2 = new JLabel();
    customDescriptionLabel_2.setBounds(10, 530, 300, 50);
    customDescriptionLabel_2.setFont(new Font("Serif", Font.PLAIN, 18));

    if (account instanceof Stocks) {
      customDescriptionHeader_1.setText("Buy date:");
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
      Date buyDate = ((Stocks) account).getBuyDate();
      customDescriptionLabel_1.setText(
          dateFormat.format(buyDate));
      customDescriptionLabel_2.setText("Stocks");
    } else if (account instanceof Cash) {
      customDescriptionHeader_1.setText("Currency:");
      customDescriptionLabel_1.setText(((Cash) account).getCurrency());
      customDescriptionLabel_2.setText("Cash");
    } else if (account instanceof CreditCard) {
      customDescriptionHeader_1.setText("Expiry date:");

      SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
      Date expireDate = ((CreditCard) account).getExpiryDate();
      customDescriptionLabel_1.setText(dateFormat.format(expireDate));
      customDescriptionLabel_2.setText("Credit Card");
    } else if (account instanceof DebitCard) {
      customDescriptionHeader_1.setText("IBAN:");
      customDescriptionLabel_1.setText(((DebitCard) account).getIBAN());
      customDescriptionLabel_1.setFont(new Font("Serif", Font.BOLD, 15));
      customDescriptionLabel_2.setText("Debit Card");
    }

    components.add(customDescriptionLabel_1);
    components.add(customDescriptionLabel_2);
    introTextLabel.setText(introTextMessage);
    components.add(introTextLabel);

    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
    backButton.setFont(BUTTON_FONT);
    backButton.setBorder(new LineBorder(Color.BLACK, 2));
    components.add(backButton);
    backButton.addActionListener(e -> backWanted = true);

    newPayoutButton = new JButton("NEW PAYOUT");
    newPayoutButton.setBounds(400, 650, 200, 70);
    newPayoutButton.setFont(BUTTON_FONT);
    newPayoutButton.setBorder(new LineBorder(Color.BLACK, 2));
    newPayoutButton.addActionListener(e -> newPayoutWanted = true);
    components.add(newPayoutButton);

    newDepositButton = new JButton("NEW DEPOST");
    newDepositButton.setBounds(700, 650, 200, 70);
    newDepositButton.setFont(BUTTON_FONT);
    newDepositButton.setBorder(new LineBorder(Color.BLACK, 2));
    newDepositButton.addActionListener(e -> newDepositWanted = true);
    components.add(newDepositButton);

    deleteButton = new JButton("DELETE");
    deleteButton.setBounds(975, 50, 150, 50);
    deleteButton.setFont(BUTTON_FONT);
    deleteButton.setBorder(new LineBorder(Color.BLACK, 2));
    deleteButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (selectedTransactionToDeleteOrUpdate == null) {
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
                          + selectedTransactionToDeleteOrUpdate.getDescription()
                          + "?",
                      "Confirm",
                      JOptionPane.YES_NO_OPTION);
              if (input == 0) {
                deleteWanted = true;
              }
            }
          }
        });
    components.add(deleteButton);

    // UPDATE BUTTON
    updateButton = new JButton("UPDATE");
    updateButton.setBounds(750, 50, 150, 50);
    updateButton.setFont(BUTTON_FONT);
    updateButton.setBorder(new LineBorder(Color.BLACK, 2));
    updateButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (selectedTransactionToDeleteOrUpdate == null) {
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
    components.add(updateButton);
  }

  /**
   * function to set the Frame Title(only called from within the UserInterface)
   * takes in a frame and sets its title to 'List Transactions - Page'
   * @param frame
   */
  @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("List Transactions - Page");
  }
}
