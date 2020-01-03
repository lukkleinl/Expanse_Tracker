package ui.listPages;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import ui.main.AbstractPage;
import user.User;

/**
 * This page gives 4 buttons, one for every account page type. This page will come up, when the user
 * wants to add a new account type. Here he will be asked what type of account he wants to create.
 *
 * @author Patrick Gmasz
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

  private User user;

  /** Creates a new AccountTypePage, which will load all needed components to a list. */
  public AccountTypePage(User user) {
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
    return pageWanted;
  }

  /**
   * Pressing the back button on this page will set a boolean flag, that the button was pressed.
   * This method returns the boolean, it will be true, when the user wanted to go a page back.
   *
   * @return The boolean flag, true, if user pressed back button
   */
  public boolean isBackWanted() {
    return backWanted;
  }

  @Override
  protected void createComponents() {
    pageWanted = AccountTypes.NONE;
    backWanted = false;
    components = new ArrayList<>();

    introTextLabel =
        new JLabel(
            "Currently logged in as: " + user.getFirstname() + " " + user.getLastname() + ".");
    introTextLabel.setBounds(200, 10, 1000, 50);
    introTextLabel.setFont(HEADER_FONT);
    components.add(introTextLabel);

    // WELCOME MESSAGE
    welcomeMessage = new JLabel();
    welcomeMessage.setText("Select the type of account you want to add!");
    welcomeMessage.setFont(new Font("Serif", Font.BOLD, 28));
    welcomeMessage.setBounds(250, 100, 1000, 50);

    // CREDITCARD BUTTON
    creditCardButton = new JButton("CREDIT CARD ACCOUNTS");
    creditCardButton.setBounds(200, 200, 350, 200);
    creditCardButton.setFont(BUTTON_FONT);
    creditCardButton.setBorder(new LineBorder(Color.BLACK, 3));
    creditCardButton.setBackground(new Color(255, 184, 77));
    creditCardButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            pageWanted = AccountTypes.CREDIT;
          }
        });

    // DEBITCARD BUTTON
    debitCardButton = new JButton("DEBIT CARD ACCOUNTS");
    debitCardButton.setBounds(650, 200, 350, 200);
    debitCardButton.setBackground(new Color(255, 184, 77));
    debitCardButton.setBorder(new LineBorder(Color.BLACK, 3));
    debitCardButton.setFont(BUTTON_FONT);
    debitCardButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            pageWanted = AccountTypes.DEBIT;
          }
        });

    // CASH BUTTON
    cashButton = new JButton("CASH ACCOUNTS");
    cashButton.setBounds(200, 500, 350, 200);
    cashButton.setFont(BUTTON_FONT);
    cashButton.setBorder(new LineBorder(Color.BLACK, 3));
    cashButton.setBackground(new Color(255, 184, 77));
    cashButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            pageWanted = AccountTypes.CASH;
          }
        });

    // STOCK BUTTON
    stockButton = new JButton("STOCK ACCOUNTS");
    stockButton.setBounds(650, 500, 350, 200);
    stockButton.setFont(BUTTON_FONT);
    stockButton.setBorder(new LineBorder(Color.BLACK, 3));
    stockButton.setBackground(new Color(255, 184, 77));
    stockButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            pageWanted = AccountTypes.STOCKS;
          }
        });

    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
    backButton.setFont(BUTTON_FONT);
    backButton.setBorder(new LineBorder(Color.BLACK, 2));
    backButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            backWanted = true;
          }
        });

    components.add(welcomeMessage);
    components.add(creditCardButton);
    components.add(debitCardButton);
    components.add(cashButton);
    components.add(stockButton);
    components.add(backButton);
  }

  @Override
  protected void resetTitle(JFrame frame) {
    frame.setTitle("Account Types");
  }
}
