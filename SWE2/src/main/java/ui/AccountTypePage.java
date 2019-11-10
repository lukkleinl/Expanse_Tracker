package ui;

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

    welcomeMessage = new JLabel();
    welcomeMessage.setText("Welcome *insert username here*!:");
    welcomeMessage.setBounds(50,100,200,30);
    components.add(welcomeMessage);

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


}
