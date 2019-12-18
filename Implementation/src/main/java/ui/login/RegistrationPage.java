package ui.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import ui.main.AbstractPage;

/**
 *
 * This page is an input page, where someone can register a new user account.
 * It consists of a user-, password-, retype password-, first name- and last name textfield and a submit button.
 *
 * @author Patrick Gmasz
 */
public class RegistrationPage extends AbstractPage {

  private JLabel userLabel;
  private JLabel passwordLabel;
  private JLabel password2Label;
  private JLabel firstnameLabel;
  private JLabel lastnameLabel;
  private JLabel infoLabel;
  private JTextField userTextField;
  private JTextField passwordTextField;
  private JTextField password2TextField;
  private JButton registerButton;
  private JButton backButton;
  private JTextField firstnameTextField;
  private JTextField lastnameTextField;

  private final static int FRAME_WIDTH = 410;
  private final static int FRAME_HEIGHT = 530;

  private String user;
  private String password;
  private String firstname;
  private String lastname;
  private boolean registrationComplete;
  private boolean backWanted;


  /**
   * Creates a new RegistrationPage, which will load all needed components to a list.
   */
  public RegistrationPage() {
    createComponents();
  }

  /**
   * Returns the input, which is in the user textfield.
   *
   * @return The string, which the user wrote in the user textfield.
   */
  public String getUser() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return user;
  }

  /**
   * Returns the input, which is in the password textfield.
   *
   * @return The string, which the user wrote in the password textfield.
   */
  public String getPassword() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return password;
  }

  /**
   * Returns the input, which is in the first name textfield.
   *
   * @return The string, which the user wrote in the first name textfield.
   */
  public String getFirstname() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return firstname;
  }

  /**
   * Returns the input, which is in the last name textfield.
   *
   * @return The string, which the user wrote in the last name textfield.
   */
  private String getLastname() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return lastname;
  }

  /**
   * The page has a submit button, which the user can press to submit the inputs needed for registering a new user account.
   * If the inputs where correct, a boolean flag will be set to true.
   *
   * @return The boolean flag, which will be true, if the user pressed the submit button and inputs where correct.
   */
  public boolean isRegistrationComplete() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return registrationComplete;
  }

  /**
   * Pressing the back button on this page will set a boolean flag, that the button was pressed.
   * This method returns the boolean, it will be true, when the user wanted to go a page back.
   *
   * @return The boolean flag, true, if user pressed back button
   */
  public boolean isBackWanted() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return backWanted;
  }

  /**
   * This method creates all components, such as buttons and text fields, and adds it to a list.
   * It also sets every indicator variables to default.
   */
  @Override
  protected void createComponents() {
    components = new ArrayList<>();
    user = "";
    password = "";
    firstname = "";
    lastname = "";

    registrationComplete = false;
    backWanted = false;

    //INFO_LABEL
    infoLabel = new JLabel();
    infoLabel.setBounds(130, 430, 150, 30);

    //USER_LABEL
    userLabel = new JLabel();
    userLabel.setText("User ID:");
    userLabel.setBounds(30, 90, 100, 30);

    //USER_TEXTFIELD
    userTextField = new JTextField();
    userTextField.setBounds(130, 90, 150, 30);

    //PASSWORD_LABEL
    passwordLabel = new JLabel();
    passwordLabel.setText("Password:");
    passwordLabel.setBounds(30, 150, 100, 30);

    //PASSWORD_TEXTFIELD
    passwordTextField = new JTextField();
    passwordTextField.setBounds(130, 150, 150, 30);

    //PASSWORD2_LABEL
    password2Label = new JLabel();
    password2Label.setText("Retype PW:");
    password2Label.setBounds(30, 210, 100, 30);

    //PASSWORD2_TEXTFIELD
    password2TextField = new JTextField();
    password2TextField.setBounds(130, 210, 150, 30);

    //FIRSTNAME_LABEL
    firstnameLabel = new JLabel();
    firstnameLabel.setText("Firstname:");
    firstnameLabel.setBounds(30, 270, 100, 30);

    //FIRSTNAME_TEXTFIELD
    firstnameTextField = new JTextField();
    firstnameTextField.setBounds(130, 270, 150, 30);

    //LASTNAME_TEXTFIELD
    lastnameLabel = new JLabel();
    lastnameLabel.setText("Firstname:");
    lastnameLabel.setBounds(30, 330, 100, 30);

    //FIRSTNAME_TEXTFIELD
    lastnameTextField = new JTextField();
    lastnameTextField.setBounds(130, 330, 150, 30);

    //REGISTER_BUTTON
    registerButton = new JButton("Submit");
    registerButton.setBounds(130, 380, 150, 40);
    registerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!userTextField.getText().equals("") && !passwordTextField.getText().equals("")
            && !password2TextField.getText().equals("") && !firstnameTextField.getText().equals("")
            && !lastnameTextField.getText().equals("")) {
          if (passwordTextField.getText().equals(password2TextField.getText())) {
            user = userTextField.getText();
            password = passwordTextField.getText();
            firstname = firstnameTextField.getText();
            lastname = lastnameTextField.getText();
            infoLabel.setText("User added successfully!");
            registrationComplete = true;
          } else {
            infoLabel.setText("Passwords must match!");
          }
        } else {
          infoLabel.setText("No empty fields allowed!");
        }
      }
    });

    //BACK_BUTTON
    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        backWanted = true;
      }
    });

    components.add(userLabel);
    components.add(userTextField);
    components.add(passwordLabel);
    components.add(passwordTextField);
    components.add(password2Label);
    components.add(password2TextField);
    components.add(infoLabel);
    components.add(registerButton);
    components.add(firstnameLabel);
    components.add(firstnameTextField);
    components.add(lastnameLabel);
    components.add(lastnameTextField);
    components.add(backButton);

  }

  @Override
  protected void resetTitle(JFrame frame) {
    frame.setTitle("Registration");
  }
}
