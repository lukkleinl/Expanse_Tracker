package GUI.LoginPages;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

import GUI.Main.AbstractPage;

/**
 * This page is an input page, where someone can register a new user account. It consists of a
 * user-, password-, retype password-, first name- and last name textfield and a submit button.
 *
 * @author Patrick Gmasz
 * @autor Paul Kraft
 */
public class RegistrationPage extends AbstractPage {

  private JLabel userLabel;
  private JLabel passwordLabel;
  private JLabel password2Label;
  private JLabel firstnameLabel;
  private JLabel lastnameLabel;
  private JLabel infoLabel;
  private JTextField userTextField;
  private JPasswordField passwordTextField;
  private JPasswordField password2TextField;
  private JButton registerButton;
  private JButton backButton;
  private JTextField firstnameTextField;
  private JTextField lastnameTextField;

  private String user;
  private String password;
  private String firstname;
  private String lastname;
  private boolean registrationComplete;
  private boolean backWanted;

  /** Creates a new RegistrationPage, which will load all needed components to a list. */
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
  public String getLastname() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return lastname;
  }

  /**
   * The page has a submit button, which the user can press to submit the inputs needed for
   * registering a new user account. If the inputs where correct, a boolean flag will be set to
   * true.
   *
   * @return The boolean flag, which will be true, if the user pressed the submit button and inputs
   *     where correct.
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
   * This method creates all components, such as buttons and text fields, and adds it to a list. It
   * also sets every indicator variables to default.
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

    // INFO_LABEL
    infoLabel = new JLabel();
    infoLabel.setFont(LABEL_FONT);
    infoLabel.setBounds(430, 670, 350, 50);

    // USER_LABEL
    userLabel = new JLabel();
    userLabel.setText("User ID:");
    userLabel.setFont(LABEL_FONT);
    userLabel.setBounds(300, 100, 300, 50);

    // USER_TEXTFIELD
    userTextField = new JTextField();
    userTextField.setFont(TEXTFIELD_FONT);
    userTextField.setBounds(600, 100, 300, 50);

    // PASSWORD_LABEL
    passwordLabel = new JLabel();
    passwordLabel.setText("Password:");
    passwordLabel.setFont(LABEL_FONT);
    passwordLabel.setBounds(300, 200, 300, 50);

    // PASSWORD_TEXTFIELD
    passwordTextField = new JPasswordField();
    passwordTextField.setFont(TEXTFIELD_FONT);
    passwordTextField.setBounds(600, 200, 300, 50);

    // PASSWORD2_LABEL
    password2Label = new JLabel();
    password2Label.setText("Retype Password:");
    password2Label.setFont(LABEL_FONT);
    password2Label.setBounds(300, 300, 300, 50);

    // PASSWORD2_TEXTFIELD
    password2TextField = new JPasswordField();
    password2TextField.setFont(TEXTFIELD_FONT);
    password2TextField.setBounds(600, 300, 300, 50);

    // FIRSTNAME_LABEL
    firstnameLabel = new JLabel();
    firstnameLabel.setText("Firstname:");
    firstnameLabel.setFont(LABEL_FONT);
    firstnameLabel.setBounds(300, 400, 300, 50);

    // FIRSTNAME_TEXTFIELD
    firstnameTextField = new JTextField();
    firstnameTextField.setFont(TEXTFIELD_FONT);
    firstnameTextField.setBounds(600, 400, 300, 50);

    // LASTNAME_TEXTFIELD
    lastnameLabel = new JLabel();
    lastnameLabel.setText("Lastname:");
    lastnameLabel.setFont(LABEL_FONT);
    lastnameLabel.setBounds(300, 500, 300, 50);

    // LASTNAME_TEXTFIELD
    lastnameTextField = new JTextField();
    lastnameTextField.setFont(TEXTFIELD_FONT);
    lastnameTextField.setBounds(600, 500, 300, 50);

    // REGISTER_BUTTON
    registerButton = new JButton("Submit");
    registerButton.setBounds(450, 600, 300, 50);
    registerButton.setFont(BUTTON_FONT);
    registerButton.setBorder(new LineBorder(Color.BLACK, 2));
    registerButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (!userTextField.getText().equals("")
                && !passwordTextField.getText().equals("")
                && !password2TextField.getText().equals("")
                && !firstnameTextField.getText().equals("")
                && !lastnameTextField.getText().equals("")) {
              if (passwordTextField.getText().equals(password2TextField.getText())) {
                user = userTextField.getText();
                password = passwordTextField.getText();
                firstname = firstnameTextField.getText();
                lastname = lastnameTextField.getText();
                //infoLabel.setText("User added successfully!");
                registrationComplete = true;
              } else {
                JOptionPane.showMessageDialog(
                    null,
                    "The passwords must match!",
                    "Password Error",
                    JOptionPane.WARNING_MESSAGE);
              }
            } else {
              JOptionPane.showMessageDialog(
                  null,
                  "No empty fields are allowed!",
                  "Empty Fields Error",
                  JOptionPane.WARNING_MESSAGE);
            }
          }
        });

    // BACK_BUTTON
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

  /**
   * This method resets the title of the JFrame to Registration.
   *
   * @param frame The JFrame, which to update the title from.
   */
  @Override
  protected void resetTitle(JFrame frame) {
    frame.setTitle("Registration");
  }
}
