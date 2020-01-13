package gui.list_pages;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import gui.main.AbstractPage;
import user.User;

/**
 * This page gives 4 buttons, one for every account page type. This page will come up, when the user
 * wants to add a new account type. Here he will be asked what type of account he wants to create.
 *
 * @author Patrick Gmasz
 * @author Paul Kraft
 */
public class AccountTypePage extends AbstractPage {

  private JLabel welcomeMessage;
  private JLabel introTextLabel;
  private JButton creditCardButton;
  private JButton debitCardButton;
  private JButton cashButton;
  private JButton stockButton;
  private JButton backButton;

  private AccountTypes pageWanted;

  private volatile boolean backWanted;

  private final User user;

  /**
   * Creates a new AccountTypePage, which will load all needed components to a list.
   * 
   * @param user the user who wants to create a new account
   */
  public AccountTypePage(final User user) {
    this.user = user;
    createComponents();
  }

  /**
   * This page has 4 buttons, one for every account type. Pressing on one will set a variable to
   * pressed account type. This method returns the account type, the user wants to create.
   *
   * @return The account type, which the user clicked.
   */
  public AccountTypes getPageWanted() {
    return this.pageWanted;
  }

  /**
   * Pressing the back button on this page will set a boolean flag, that the button was pressed.
   * This method returns the boolean, it will be true, when the user wanted to go a page back.
   *
   * @return The boolean flag, true, if user pressed back button
   */
  public boolean isBackWanted() {
    return this.backWanted;
  }

  /**
   * This method creates all components, such as buttons and text fields, and adds it to a list. It
   * also sets every indicator variables to default and updates the account list of the user.
   */
  @Override
  protected void createComponents() {
    this.pageWanted = AccountTypes.NONE;
    this.backWanted = false;
    this.components = new ArrayList<>();

    this.introTextLabel = new JLabel("Currently logged in as: " + this.user.getFirstname() + " "
        + this.user.getLastname() + ".");
    this.introTextLabel.setBounds(200, 10, 1000, 50);
    this.introTextLabel.setFont(HEADER_FONT);
    this.components.add(this.introTextLabel);

    // WELCOME MESSAGE
    this.welcomeMessage = new JLabel();
    this.welcomeMessage.setText("Select the type of account you want to add!");
    this.welcomeMessage.setFont(new Font("Serif", Font.BOLD, 28));
    this.welcomeMessage.setBounds(250, 100, 1000, 50);

    // CREDITCARD BUTTON
    this.creditCardButton = new JButton("CREDIT CARD ACCOUNTS");
    this.creditCardButton.setBounds(200, 200, 350, 200);
    this.creditCardButton.setFont(BUTTON_FONT);
    this.creditCardButton.setBorder(new LineBorder(Color.BLACK, 3));
    this.creditCardButton.setBackground(new Color(255, 184, 77));
    this.creditCardButton
        .addActionListener(e -> AccountTypePage.this.pageWanted = AccountTypes.CREDIT);

    // DEBITCARD BUTTON
    this.debitCardButton = new JButton("DEBIT CARD ACCOUNTS");
    this.debitCardButton.setBounds(650, 200, 350, 200);
    this.debitCardButton.setBackground(new Color(255, 184, 77));
    this.debitCardButton.setBorder(new LineBorder(Color.BLACK, 3));
    this.debitCardButton.setFont(BUTTON_FONT);
    this.debitCardButton
        .addActionListener(e -> AccountTypePage.this.pageWanted = AccountTypes.DEBIT);

    // CASH BUTTON
    this.cashButton = new JButton("CASH ACCOUNTS");
    this.cashButton.setBounds(200, 500, 350, 200);
    this.cashButton.setFont(BUTTON_FONT);
    this.cashButton.setBorder(new LineBorder(Color.BLACK, 3));
    this.cashButton.setBackground(new Color(255, 184, 77));
    this.cashButton.addActionListener(e -> AccountTypePage.this.pageWanted = AccountTypes.CASH);

    // STOCK BUTTON
    this.stockButton = new JButton("STOCK ACCOUNTS");
    this.stockButton.setBounds(650, 500, 350, 200);
    this.stockButton.setFont(BUTTON_FONT);
    this.stockButton.setBorder(new LineBorder(Color.BLACK, 3));
    this.stockButton.setBackground(new Color(255, 184, 77));
    this.stockButton.addActionListener(e -> AccountTypePage.this.pageWanted = AccountTypes.STOCKS);

    this.backButton = new JButton("BACK");
    this.backButton.setBounds(10, 10, 100, 50);
    this.backButton.setFont(BUTTON_FONT);
    this.backButton.setBorder(new LineBorder(Color.BLACK, 2));
    this.backButton.addActionListener(e -> AccountTypePage.this.backWanted = true);

    this.components.add(this.welcomeMessage);
    this.components.add(this.creditCardButton);
    this.components.add(this.debitCardButton);
    this.components.add(this.cashButton);
    this.components.add(this.stockButton);
    this.components.add(this.backButton);
  }

  /**
   * Sets the title of the JFrame to Account Types.
   *
   * @param frame The JFrame, where to change the title.
   */
  @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("Account Types");
  }
}
