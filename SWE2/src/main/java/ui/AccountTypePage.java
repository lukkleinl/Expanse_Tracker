package ui;

import java.awt.Font;
import javax.swing.*;

import user.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AccountTypePage {
  private final int FRAME_WIDTH = 1200;
  private final int FRAME_HEIGHT = 800;

  private ArrayList<JComponent> components;

  private JLabel welcomeMessage;
  private JButton creditCardButton;
  private JButton debitCardButton;
  private JButton cashButton;
  private JButton stockButton;

  private AccountTypes pageWanted;
  private User user;

  public AccountTypePage(/*User user*/) {

    /*this.user = user;
     */
    pageWanted = AccountTypes.NONE;
    components = new ArrayList<>();

    //WELCOME MESSAGE
    welcomeMessage = new JLabel();
    welcomeMessage.setText("Welcome *insert username here*!");
    welcomeMessage.setFont(new Font("Serif", Font.BOLD, 24));
    welcomeMessage.setBounds(100,50,1000,50);

    //CREDITCARD BUTTON
    creditCardButton = new JButton("CREDIT CARD ACCOUNTS");
    creditCardButton.setBounds(200, 150, 350, 200);
    creditCardButton.setFont(new Font("Serif",Font.PLAIN,22));
    creditCardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        pageWanted = AccountTypes.CREDIT;
      }
    });

    //DEBITCARD BUTTON
    debitCardButton = new JButton("DEBIT CARD ACCOUNTS");
    debitCardButton.setBounds(650, 150, 350, 200);
    debitCardButton.setFont(new Font("Serif",Font.PLAIN,22));
    debitCardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        pageWanted = AccountTypes.DEBIT;
      }
    });


    //CASH BUTTON
    cashButton = new JButton("CASH ACCOUNTS");
    cashButton.setBounds(200, 450,350,200);
    cashButton.setFont(new Font("Serif",Font.PLAIN,22));
    cashButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        pageWanted = AccountTypes.CASH;
      }
    });

    //STOCK BUTTON
    stockButton = new JButton("STOCK ACCOUNTS");
    stockButton.setBounds(650, 450,350,200);
    stockButton.setFont(new Font("Serif",Font.PLAIN,22));
    stockButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        pageWanted = AccountTypes.STOCKS;
      }
    });

    components.add(welcomeMessage);
    components.add(creditCardButton);
    components.add(debitCardButton);
    components.add(cashButton);
    components.add(stockButton);

  }

  public void configureFrame(JFrame frame){

    frame.setVisible(false);
    frame.setTitle("Account Types");
    frame.getContentPane().removeAll();
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    for(JComponent comp: components) {
      frame.add(comp);
    }

    frame.revalidate();
    frame.repaint();
    frame.setVisible(true);
  }

  public AccountTypes getPageWanted() {
    return pageWanted;
  }

  public void resetPageWanted() {
    this.pageWanted = AccountTypes.NONE;
  }
}
