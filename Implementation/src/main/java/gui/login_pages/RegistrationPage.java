package gui.login_pages;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import gui.main.AbstractPage;

/**
 * This page is an input page, where someone can register a new user account. It consists of a
 * user-, password-, retype password-, first name- and last name textfield and a submit button.
 *
 * @author Patrick Gmasz
 * @author Paul Kraft
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
    return this.user;
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
    return this.password;
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
    return this.firstname;
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
    return this.lastname;
  }

  /**
   * The page has a submit button, which the user can press to submit the inputs needed for
   * registering a new user account. If the inputs where correct, a boolean flag will be set to
   * true.
   *
   * @return The boolean flag, which will be true, if the user pressed the submit button and inputs
   *         where correct.
   */
  public boolean isRegistrationComplete() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return this.registrationComplete;
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
    return this.backWanted;
  }

  /**
   * This method creates all components, such as buttons and text fields, and adds it to a list. It
   * also sets every indicator variables to default.
   */
  @Override
  protected void createComponents() {
    this.components = new ArrayList<>();
    this.user = "";
    this.password = "";
    this.firstname = "";
    this.lastname = "";

    this.registrationComplete = false;
    this.backWanted = false;

    // INFO_LABEL
    this.infoLabel = new JLabel();
    this.infoLabel.setFont(LABEL_FONT);
    this.infoLabel.setBounds(430, 670, 350, 50);

    // USER_LABEL
    this.userLabel = new JLabel();
    this.userLabel.setText("User ID:");
    this.userLabel.setFont(LABEL_FONT);
    this.userLabel.setBounds(300, 100, 300, 50);

    // USER_TEXTFIELD
    this.userTextField = new JTextField();
    this.userTextField.setFont(TEXTFIELD_FONT);
    this.userTextField.setBounds(600, 100, 300, 50);

    // PASSWORD_LABEL
    this.passwordLabel = new JLabel();
    this.passwordLabel.setText("Password:");
    this.passwordLabel.setFont(LABEL_FONT);
    this.passwordLabel.setBounds(300, 200, 300, 50);

    // PASSWORD_TEXTFIELD
    this.passwordTextField = new JPasswordField();
    this.passwordTextField.setFont(TEXTFIELD_FONT);
    this.passwordTextField.setBounds(600, 200, 300, 50);

    // PASSWORD2_LABEL
    this.password2Label = new JLabel();
    this.password2Label.setText("Retype Password:");
    this.password2Label.setFont(LABEL_FONT);
    this.password2Label.setBounds(300, 300, 300, 50);

    // PASSWORD2_TEXTFIELD
    this.password2TextField = new JPasswordField();
    this.password2TextField.setFont(TEXTFIELD_FONT);
    this.password2TextField.setBounds(600, 300, 300, 50);

    // FIRSTNAME_LABEL
    this.firstnameLabel = new JLabel();
    this.firstnameLabel.setText("Firstname:");
    this.firstnameLabel.setFont(LABEL_FONT);
    this.firstnameLabel.setBounds(300, 400, 300, 50);

    // FIRSTNAME_TEXTFIELD
    this.firstnameTextField = new JTextField();
    this.firstnameTextField.setFont(TEXTFIELD_FONT);
    this.firstnameTextField.setBounds(600, 400, 300, 50);

    // LASTNAME_TEXTFIELD
    this.lastnameLabel = new JLabel();
    this.lastnameLabel.setText("Lastname:");
    this.lastnameLabel.setFont(LABEL_FONT);
    this.lastnameLabel.setBounds(300, 500, 300, 50);

    // LASTNAME_TEXTFIELD
    this.lastnameTextField = new JTextField();
    this.lastnameTextField.setFont(TEXTFIELD_FONT);
    this.lastnameTextField.setBounds(600, 500, 300, 50);

    // REGISTER_BUTTON
    this.registerButton = new JButton("Submit");
    this.registerButton.setBounds(450, 600, 300, 50);
    this.registerButton.setFont(BUTTON_FONT);
    this.registerButton.setBorder(new LineBorder(Color.BLACK, 2));
    this.registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    this.registerButton.addActionListener(e -> {
      if (!RegistrationPage.this.userTextField.getText().equals("")
          && !RegistrationPage.this.passwordTextField.getText().equals("")
          && !RegistrationPage.this.password2TextField.getText().equals("")
          && !RegistrationPage.this.firstnameTextField.getText().equals("")
          && !RegistrationPage.this.lastnameTextField.getText().equals("")) {
        if (RegistrationPage.this.passwordTextField.getText()
            .equals(RegistrationPage.this.password2TextField.getText())) {
          RegistrationPage.this.user = RegistrationPage.this.userTextField.getText();
          RegistrationPage.this.password = RegistrationPage.this.passwordTextField.getText();
          RegistrationPage.this.firstname = RegistrationPage.this.firstnameTextField.getText();
          RegistrationPage.this.lastname = RegistrationPage.this.lastnameTextField.getText();
          // infoLabel.setText("User added successfully!");
          RegistrationPage.this.registrationComplete = true;
        } else {
          JOptionPane.showMessageDialog(null, "The passwords must match!", "Password Error",
              JOptionPane.WARNING_MESSAGE);
        }
      } else {
        JOptionPane.showMessageDialog(null, "No empty fields are allowed!", "Empty Fields Error",
            JOptionPane.WARNING_MESSAGE);
      }
    });

    // BACK_BUTTON
    this.backButton = new JButton("BACK");
    this.backButton.setBounds(10, 10, 100, 50);
    this.backButton.setFont(BUTTON_FONT);
    this.backButton.setBorder(new LineBorder(Color.BLACK, 2));
    this.backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    this.backButton.addActionListener(e -> RegistrationPage.this.backWanted = true);

    this.components.add(this.userLabel);
    this.components.add(this.userTextField);
    this.components.add(this.passwordLabel);
    this.components.add(this.passwordTextField);
    this.components.add(this.password2Label);
    this.components.add(this.password2TextField);
    this.components.add(this.infoLabel);
    this.components.add(this.registerButton);
    this.components.add(this.firstnameLabel);
    this.components.add(this.firstnameTextField);
    this.components.add(this.lastnameLabel);
    this.components.add(this.lastnameTextField);
    this.components.add(this.backButton);
  }

  /**
   * This method resets the title of the JFrame to Registration.
   *
   * @param frame The JFrame, which to update the title from.
   */
  @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("Registration");
  }
}
