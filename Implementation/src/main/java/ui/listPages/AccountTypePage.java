package ui.listPages;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import ui.main.AbstractPage;


/**
 * This page gives 4 buttons, one for every account page type.
 * This page will come up, when the user wants to add a new account type.
 * Here he will be asked what type of account he wants to create.
 *
 * @author Patrick Gmasz
 */
public class AccountTypePage extends AbstractPage {

  private JLabel welcomeMessage;
  private JButton creditCardButton;
  private JButton debitCardButton;
  private JButton cashButton;
  private JButton stockButton;
  private JButton backButton;

  private AccountTypes pageWanted;

  private volatile boolean backWanted;

  /**
   * Creates a new AccountTypePage, which will load all needed components to a list.
   */
  public AccountTypePage() {
    createComponents();
  }

  /**
   * This page has 4 buttons, one for every account type.
   * Pressing on one will set a variable to pressed account type.
   * This method returns the account type, the user wants to create.
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
    components = new ArrayList<>();

    //WELCOME MESSAGE
    welcomeMessage = new JLabel();
    welcomeMessage.setText("Select the type of account you want to add!");
    welcomeMessage.setFont(new Font("Serif", Font.BOLD, 28));
    welcomeMessage.setBounds(350, 50, 1000, 50);

    //CREDITCARD BUTTON
    creditCardButton = new JButton("CREDIT CARD ACCOUNTS");
    creditCardButton.setBounds(200, 150, 350, 200);
    creditCardButton.setFont(new Font("Serif", Font.PLAIN, 22));
    creditCardButton.setBackground(new Color(255, 184, 77));
    creditCardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        pageWanted = AccountTypes.CREDIT;
      }
    });

    //DEBITCARD BUTTON
    debitCardButton = new JButton("DEBIT CARD ACCOUNTS");
    debitCardButton.setBounds(650, 150, 350, 200);
    debitCardButton.setFont(new Font("Serif", Font.PLAIN, 22));
    debitCardButton.setBackground(new Color(255, 184, 77));
    debitCardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        pageWanted = AccountTypes.DEBIT;
      }
    });

    //CASH BUTTON
    cashButton = new JButton("CASH ACCOUNTS");
    cashButton.setBounds(200, 450, 350, 200);
    cashButton.setFont(new Font("Serif", Font.PLAIN, 22));
    cashButton.setBackground(new Color(255, 184, 77));
    cashButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        pageWanted = AccountTypes.CASH;
      }
    });

    //STOCK BUTTON
    stockButton = new JButton("STOCK ACCOUNTS");
    stockButton.setBounds(650, 450, 350, 200);
    stockButton.setFont(new Font("Serif", Font.PLAIN, 22));
    stockButton.setBackground(new Color(255, 184, 77));
    stockButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        pageWanted = AccountTypes.STOCKS;
      }
    });

    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
    backButton.addActionListener(new ActionListener() {
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