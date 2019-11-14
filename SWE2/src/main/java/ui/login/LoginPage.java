package ui.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import ui.main.InterfacePage;


/**
 * This page will be the start page, when the application is started.
 * The user can input his user id and password.
 * There is also a button, which will direct the user to a registration page.
 *
 * @author Patrick Gmasz
 */
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
  private volatile boolean registrationWanted;
  private volatile boolean loginWanted;


  /**
   * Creates a new LoginPage, which will load all needed components in a list.
   */
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
   * The page has a button, which the user can press if he wants to register a new user account.
   * If the button gets pressed, a boolean flag will be set to true.
   *
   * @return The boolean flag, which will be true, if the user pressed the register button.
   */
  public boolean isRegistrationWanted() {
    return registrationWanted;
  }

  /**
   * The page has a button, which the user can press of he wants to log in.
   * If the button gets pressed, a boolean flag will be set to true.
   *
   * @return The boolean flag, which will be true, if the user pressed the login button:
   */
  public boolean isLoginWanted() {
    return loginWanted;
  }

  /**
   * This method updates the given JFrame with every components, e.g. buttons or text fields, and also sets indicators to default.
   *
   * @param frame The JFrame, which components will be updated
   */
  public void configureFrame(JFrame frame) {
    createComponents();

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

  /**
   * This method creates all components, such as buttons and text fields, and adds it to a list.
   * It also sets every indicator variables to default.
   */
  private void createComponents() {
    user = "";
    password = "";
    registrationWanted = false;
    loginWanted = false;

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
        loginWanted = true;
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
}
