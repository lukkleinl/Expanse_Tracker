import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RegistrationPage {
  private ArrayList<JComponent> components;
  private JLabel userLabel;
  private JLabel passwordLabel;
  private JLabel password2Label;
  private JLabel infoLabel;
  private JTextField userTextField;
  private JTextField passwordTextField;
  private JTextField password2TextField;
  private JButton registerButton;

  public final int FRAME_WIDTH = 410;
  public final int FRAME_HEIGHT = 320;

  private String user;
  private String password;
  private boolean registrationComplete;

  public RegistrationPage() {
    user = "";
    password = "";
    registrationComplete = false;

    //INFO_LABEL
    infoLabel = new JLabel();
    infoLabel.setBounds(130,260,150,30);

    //USER_LABEL
    userLabel = new JLabel();
    userLabel.setText("User:");
    userLabel.setBounds(30,40,100,30);

    //USER_TEXTFIELD
    userTextField = new JTextField();
    userTextField.setBounds(130,40,150,30);

    //PASSWORD_LABEL
    passwordLabel = new JLabel();
    passwordLabel.setText("Password:");
    passwordLabel.setBounds(30,100,100,30);

    //PASSWORD_TEXTFIELD
    passwordTextField = new JTextField();
    passwordTextField.setBounds(130,100,150,30);

    //PASSWORD2_LABEL
    password2Label = new JLabel();
    password2Label.setText("Retype PW:");
    password2Label.setBounds(30,160, 100, 30);

    //PASSWORD2_TEXTFIELD
    password2TextField = new JTextField();
    password2TextField.setBounds(130,160,150,30);

    //REGISTER_BUTTON
    registerButton = new JButton("Submit");
    registerButton.setBounds(130,210,150,40);
    registerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(!userTextField.getText().equals("") && !passwordTextField.getText().equals("") && !password2TextField.getText().equals("")) {
          if(passwordTextField.getText().equals(password2TextField.getText())) {
            user = userTextField.getText();
            password = passwordTextField.getText();
            infoLabel.setText("User added successfully!");
            registrationComplete = true;
          }
          else {
            infoLabel.setText("Passwords must match!");
          }
        }
        else {
          infoLabel.setText("No empty fields allowed!");
        }
      }
    });

    components = new ArrayList<>();
    components.add(userLabel);
    components.add(userTextField);
    components.add(passwordLabel);
    components.add(passwordTextField);
    components.add(password2Label);
    components.add(password2TextField);
    components.add(infoLabel);
    components.add(registerButton);
  }

  public ArrayList<JComponent> getComponents() {
    return components;
  }

  public String getUser() {
    return user;
  }

  public String getPassword() {
    return password;
  }

  public boolean isRegistrationComplete() {
    return registrationComplete;
  }
}