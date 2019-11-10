package ui;

import javax.swing.*;

import user.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AccountTypePage {
  public final static int FRAME_WIDTH = 410;
  public final static int FRAME_HEIGHT = 480;
  private ArrayList<JComponent> components;

  private JLabel welcomeMessage;
  private JButton creditCardButton;
  private JButton debitCardButton;
  private JButton cashButton;
  private JButton stockButton;

  private AccountTypes pageWanted = AccountTypes.NONE;
  private User user;

  public AccountTypePage(/*User user*/){

    welcomeMessage = new JLabel("Willkommen - Bitte Kontoart auswählen!");
    welcomeMessage.setBounds(30,330,30,330);
    components.add(welcomeMessage);


    creditCardButton = new JButton("Submit");
    creditCardButton.setBounds(130,330,150,40);
    components.add(creditCardButton);
    creditCardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {

      }
    });
/*
    creditCardButton = new JButton("Submit");
    creditCardButton.setBounds(130,330,150,40);
    creditCardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {

      }
    });

    creditCardButton = new JButton("Submit");
    creditCardButton.setBounds(130,330,150,40);
    creditCardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {

      }
    });

    creditCardButton = new JButton("Submit");
    creditCardButton.setBounds(130,330,150,40);
    creditCardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {

      }
    });
*/




  }

  public void configureFrame(JFrame frame){

    frame.setVisible(false);
    frame.setTitle("Account Types");
    frame.getContentPane().removeAll();
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

      for(JComponent comp: components)
        frame.add(comp);

    frame.revalidate();
    frame.repaint();
    frame.setVisible(true);
  }


}
