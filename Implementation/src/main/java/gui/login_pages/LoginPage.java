package gui.login_pages;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

import gui.main.AbstractPage;

/**
 * This page will be the start page, when the application is started. The user can input his user id
 * and password. There is also a button, which will direct the user to a registration page.
 *
 * @author Patrick Gmasz
 */
public class LoginPage extends AbstractPage {

  private JLabel userLabel;
  private JLabel passwordLabel;
  private JTextField userTextField;
  private JPasswordField passwordTextField;
  private JButton submitButton;
  private JButton registerButton;

  private JLabel message;

  private String user;
  private String password;
  private volatile boolean registrationWanted;
  private volatile boolean loginWanted;

  /** Creates a new LoginPage, which will load all needed components in a list. */
  public LoginPage() {
    createComponents();
  }

  /**
   * Returns the input, which is in the user textfield.
   *
   * @return The string, which the user wrote in the user textfield
   */
  public String getUser() {
    return user;
  }

  /**
   * Returns the input, which is in the password textfield.
   *
   * @return The string, which the user wrote in the password textfield
   */
  public String getPassword() {
    return password;
  }

  /**
   * The page has a button, which the user can press if he wants to register a new user account. If
   * the button gets pressed, a boolean flag will be set to true.
   *
   * @return The boolean flag, which will be true, if the user pressed the register button.
   */
  public boolean isRegistrationWanted() {
    return registrationWanted;
  }

  /**
   * The page has a button, which the user can press of he wants to log in. If the button gets
   * pressed, a boolean flag will be set to true.
   *
   * @return The boolean flag, which will be true, if the user pressed the login button:
   */
  public boolean isLoginWanted() {
    return loginWanted;
  }

  /**
   * This method creates all components, such as buttons and text fields, and adds it to a list. It
   * also sets every indicator variables to default.
   */
  @Override
  protected void createComponents() {
    components = new ArrayList<>();
    user = "";
    password = "";
    registrationWanted = false;
    loginWanted = false;

    message = new JLabel();
    message.setText("Expense Tracker");
    message.setFont(new Font("Serif", Font.BOLD, 50));
    message.setBounds(360, 100, 500, 70);

    // USER_LABEL
    userLabel = new JLabel();
    userLabel.setText("User:");
    userLabel.setFont(LABEL_FONT);
    userLabel.setBounds(300, 300, 300, 50);

    // USER_TEXTFIELD
    userTextField = new JTextField();
    userTextField.setFont(TEXTFIELD_FONT);
    userTextField.setBounds(600, 300, 300, 50);

    // PASSWORD_LABEL
    passwordLabel = new JLabel();
    passwordLabel.setText("Password:");
    passwordLabel.setFont(LABEL_FONT);
    passwordLabel.setBounds(300, 400, 300, 50);

    // PASSWORD_TEXTFIELD
    passwordTextField = new JPasswordField();
    passwordTextField.setFont(TEXTFIELD_FONT);
    passwordTextField.setBounds(600, 400, 300, 50);

    // SUBMIT_BUTTON
    submitButton = new JButton("Submit");
    submitButton.setBounds(650, 500, 250, 50);
    submitButton.setFont(BUTTON_FONT);
    submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    submitButton.setBorder(new LineBorder(Color.BLACK, 2));
    submitButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            user = userTextField.getText();
            password = passwordTextField.getText();
            loginWanted = true;
          }
        });

    // REGISTER_BUTTON
    registerButton = new JButton("Register");
    registerButton.setBounds(300, 500, 250, 50);
    registerButton.setFont(BUTTON_FONT);
    registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    registerButton.setBorder(new LineBorder(Color.BLACK, 2));
    registerButton.addActionListener(
        new ActionListener() {
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
    components.add(message);
  }

  /**
   * This method resets the title of the JFrame to "Login".
   *
   * @param frame The JFrame, which to update the title from.
   */
  @Override
  protected void resetTitle(JFrame frame) {
    frame.setTitle("Login");
  }
}


