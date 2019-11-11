package ui;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RegistrationPage implements InterfacePage {

  private ArrayList<JComponent> components;
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
  private JTextField firstnameTextField;
  private JTextField lastnameTextField;

  private final int FRAME_WIDTH = 410;
  private final int FRAME_HEIGHT = 480;

  private String user;
  private String password;
  private boolean registrationComplete;

  public RegistrationPage() {
    createComponents();
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

  public boolean isRegistrationComplete() {
    return registrationComplete;
  }

  public void configureFrame(JFrame frame) {
    createComponents();
    frame.setVisible(false);
    frame.setTitle("Registration");
    frame.getContentPane().removeAll();
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    for (JComponent comp : components) {
      frame.add(comp);
    }
    frame.revalidate();
    frame.repaint();
    frame.setVisible(true);
  }

  private void createComponents() {
    user = "";
    password = "";
    registrationComplete = false;

    //INFO_LABEL
    infoLabel = new JLabel();
    infoLabel.setBounds(130, 380, 150, 30);

    //USER_LABEL
    userLabel = new JLabel();
    userLabel.setText("User ID:");
    userLabel.setBounds(30, 40, 100, 30);

    //USER_TEXTFIELD
    userTextField = new JTextField();
    userTextField.setBounds(130, 40, 150, 30);

    //PASSWORD_LABEL
    passwordLabel = new JLabel();
    passwordLabel.setText("Password:");
    passwordLabel.setBounds(30, 100, 100, 30);

    //PASSWORD_TEXTFIELD
    passwordTextField = new JTextField();
    passwordTextField.setBounds(130, 100, 150, 30);

    //PASSWORD2_LABEL
    password2Label = new JLabel();
    password2Label.setText("Retype PW:");
    password2Label.setBounds(30, 160, 100, 30);

    //PASSWORD2_TEXTFIELD
    password2TextField = new JTextField();
    password2TextField.setBounds(130, 160, 150, 30);

    //FIRSTNAME_LABEL
    firstnameLabel = new JLabel();
    firstnameLabel.setText("Firstname:");
    firstnameLabel.setBounds(30, 220, 100, 30);

    //FIRSTNAME_TEXTFIELD
    firstnameTextField = new JTextField();
    firstnameTextField.setBounds(130, 220, 150, 30);

    //LASTNAME_TEXTFIELD
    lastnameLabel = new JLabel();
    lastnameLabel.setText("Firstname:");
    lastnameLabel.setBounds(30, 280, 100, 30);

    //FIRSTNAME_TEXTFIELD
    lastnameTextField = new JTextField();
    lastnameTextField.setBounds(130, 280, 150, 30);

    //REGISTER_BUTTON
    registerButton = new JButton("Submit");
    registerButton.setBounds(130, 330, 150, 40);
    registerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!userTextField.getText().equals("") && !passwordTextField.getText().equals("")
            && !password2TextField.getText().equals("") && !firstnameTextField.getText().equals("")
            && !lastnameTextField.getText().equals("")) {
          if (passwordTextField.getText().equals(password2TextField.getText())) {
            //TODO: ADD USER HERE
            user = userTextField.getText();
            password = passwordTextField.getText();
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

    components = new ArrayList<>();
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
  }
}
