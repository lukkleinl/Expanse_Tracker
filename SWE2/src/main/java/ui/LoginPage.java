package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoginPage implements InterfacePage {

  private ArrayList<JComponent> components;
  private JLabel userLabel;
  private JLabel passwordLabel;
  private JTextField userTextField;
  private JTextField passwordTextField;
  private JButton submitButton;
  private JButton registerButton;

  private final int FRAME_WIDTH = 410;
  private final int FRAME_HEIGHT = 280;

  private String user;
  private String password;
  private boolean registrationWanted;

  public LoginPage() {
    user = "";
    password = "";
    registrationWanted = false;

    //USER_LABEL
    userLabel = new JLabel();
    userLabel.setText("User:");
    userLabel.setBounds(30, 40, 100, 30);

    //USER_TEXTFIELD
    userTextField = new JTextField();
    userTextField.setBounds(130, 40, 150, 30);

    //PASSWORD_LABEL
    passwordLabel = new JLabel();
    passwordLabel.setText("Password:");
    passwordLabel.setBounds(30, 90, 100, 30);

    //PASSWORD_TEXTFIELD
    passwordTextField = new JTextField();
    passwordTextField.setBounds(130, 90, 150, 30);

    //SUBMIT_BUTTON
    submitButton = new JButton("Submit");
    submitButton.setBounds(210, 150, 150, 40);
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        user = userTextField.getText();
        password = passwordTextField.getText();
        System.out.println(user);
        System.out.println(password);
      }
    });

    //REGISTER_BUTTON
    registerButton = new JButton("Register");
    registerButton.setBounds(30, 150, 150, 40);
    registerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        registrationWanted = true;
      }
    });

    components = new ArrayList<>();
    components.add(userLabel);
    components.add(userTextField);
    components.add(passwordLabel);
    components.add(passwordTextField);
    components.add(submitButton);
    components.add(registerButton);
  }

  public String getUser() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return user;
  }

  public String getPassword() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return password;
  }

  public boolean isRegistrationWanted() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return registrationWanted;
  }

  public void registrationFinished() {
    registrationWanted = false;

    try {
      Thread.sleep(1000);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void configureFrame(JFrame frame) {
    //frame.setVisible(false);
    frame.setTitle("Login");
    frame.getContentPane().removeAll();
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    for (JComponent comp : components) {
      frame.add(comp);
    }
    frame.revalidate();
    frame.repaint();
    //frame.setVisible(true);
  }
}
