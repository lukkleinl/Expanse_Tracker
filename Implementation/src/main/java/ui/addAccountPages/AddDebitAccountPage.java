package ui.addAccountPages;
/**
 * This Page collects all necessary data needed to create a new 'DebitCard'-Account.
 * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
 * @author Paul Kraft
 */
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import accounts.DebitCard;
import ui.main.AbstractPage;
import user.User;

public class AddDebitAccountPage extends AbstractPage {

  private final static int SHIFT_LEFT = 300;
  private final static int OFFSET_Y = 50; // THE AMT OF PIXELS THE TEXT FIELDS ARE OFFSET TO THEIR CORRESPONDING JPANELS!

  private JLabel introTextLabel;
  private JLabel accountNameTextLabel;
  private JLabel limitTextLabel;
  private JLabel bankNameTextLabel;
  private JLabel ibanTextLabel;
  private JButton submitButton;
  private JButton backButton;

  private JTextField accountNameInputField;
  private JTextField limitInputField;
  private JTextField bankNameInputField;
  private JTextField ibanInputField;

  private String accountNameInputValue = "";
  private String bankNameInputValue = "";
  private String ibanInputValue = "";
  private float limitInputValue = 0.00f;

  private volatile boolean submitted;
  private volatile boolean backWanted;

  private User user;
  /**
   * Creates a new AddDebitAccountPage, which will load all needed components to a list.
   * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
   */
  // In Final Version might take a User object to display additional User information.
  public AddDebitAccountPage(User user) {
    this.user = user;
    createComponents();
  }

  /**
   * @return AccName input from the User or default val.
   */
  public String getAccountName() {
    return accountNameInputValue;
  }
  /**
   * @return IBAN input from the User or default val.
   */
  public String getIBAN() {
    return ibanInputValue;
  }
  /**
   * @return Limit input from the User or default val.
   */
  public float getLimit() {
    return limitInputValue;
  }
  /**
   * @return BankName input from the User or default val.
   */
  public String getBankName() {
    return bankNameInputValue;
  }
  /**
   * @return current boolean value of submitted(whether the User submitted or not! )
   */
  public boolean isSubmitted() {
    return submitted;
  }
  /**
   * @return current boolean value of backWanted( whether the User wants to go back or not! )
   */
  public boolean isBackWanted() {
    return backWanted;
  }

  @Override
  protected void createComponents() {
    components = new ArrayList<>();
    submitted = false;
    backWanted = false;

    introTextLabel = new JLabel("Currently logged in as: " + user.getFirstname() + " " + user.getLastname() + ".");
    introTextLabel.setBounds(200, 10, 1000, 50);
    introTextLabel.setFont(HEADER_FONT);
    components.add(introTextLabel);

    accountNameTextLabel = new JLabel("Account Name:");
    accountNameTextLabel.setBounds(300, 150, 300, 50);
    accountNameTextLabel.setFont(LABEL_FONT);
    components.add(accountNameTextLabel);

    accountNameInputField = new JTextField();
    accountNameInputField.setBounds(600, 150, 300, 50);
    accountNameInputField.setFont(TEXTFIELD_FONT);
    components.add(accountNameInputField);

    limitTextLabel = new JLabel("Limit:");
    limitTextLabel.setBounds(300, 250, 300, 50);
    limitTextLabel.setFont(LABEL_FONT);
    components.add(limitTextLabel);

    limitInputField = new JTextField("0.00");
    limitInputField.setBounds(600, 250, 300, 50);
    limitInputField.setFont(TEXTFIELD_FONT);
    components.add(limitInputField);

    ibanTextLabel = new JLabel("IBAN:");
    ibanTextLabel.setBounds(300, 350, 300, 50);
    ibanTextLabel.setFont(LABEL_FONT);
    components.add(ibanTextLabel);

    ibanInputField = new JTextField();
    ibanInputField.setBounds(600, 350, 300, 50);
    components.add(ibanInputField);

    bankNameTextLabel = new JLabel("Bank Name:");
    bankNameTextLabel.setBounds(300, 450, 300, 50);
    bankNameTextLabel.setFont(LABEL_FONT);
    components.add(bankNameTextLabel);

    bankNameInputField = new JTextField();
    bankNameInputField.setBounds(600, 450, 300, 50);
    bankNameInputField.setFont(TEXTFIELD_FONT);
    components.add(bankNameInputField);

    submitButton = new JButton("SUBMIT");
    submitButton.setBounds(450, 600, 300, 50);
    submitButton.setBorder(new LineBorder(Color.BLACK,2));
    submitButton.setFont(BUTTON_FONT);
    components.add(submitButton);

    submitButton.addActionListener(
        new ActionListener() {
          @Override
          /**
           * @param e Action event sets Submitted to true extracts the Input Values and provides
           *     them for getters.
           */
          public void actionPerformed(ActionEvent e) {
            accountNameInputValue = accountNameInputField.getText();
            ibanInputValue = ibanInputField.getText();
            limitInputValue =
                (limitInputField.getText().isEmpty())
                    ? 0
                    : Float.valueOf(limitInputField.getText());
            bankNameInputValue = bankNameInputField.getText();

            if (limitInputValue < 0) {
              JOptionPane.showMessageDialog(
                  null, "Limit must be higher than 0", "Limit Error", JOptionPane.WARNING_MESSAGE);
            } else if (accountNameInputValue.isEmpty()) {
              JOptionPane.showMessageDialog(
                  null,
                  "You must insert an account name!",
                  "Account Name Error",
                  JOptionPane.WARNING_MESSAGE);
            } else if (bankNameInputValue.isEmpty()) {
              JOptionPane.showMessageDialog(
                  null,
                  "You must insert an account name!",
                  "Account Name Error",
                  JOptionPane.WARNING_MESSAGE);
            } else if (ibanInputValue.isEmpty()) {
              JOptionPane.showMessageDialog(
                      null,
                      "You must insert an IBAN!",
                      "IBAN Error",
                      JOptionPane.WARNING_MESSAGE);
            } else {
              submitted = true;
            }
          }
        });

    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
    backButton.setFont(BUTTON_FONT);
    backButton.setBorder(new LineBorder(Color.BLACK,2));
    components.add(backButton);
    backButton.addActionListener(new ActionListener() {
      @Override
      /**
       * @param e Action event
       * sets BackWanted to true
       */
      public void actionPerformed(ActionEvent e) {
        backWanted = true;
      }
    });


  }

  @Override
  protected void resetTitle(JFrame frame) {
    frame.setTitle("Add Debit Card Account");
  }

  public void updateFields(DebitCard account) {
    accountNameInputField.setText(account.getName());
    limitInputField.setText(Float.toString(account.getLimit()));
    bankNameInputField.setText(account.getBankName());
    ibanInputField.setText(account.getIBAN());
  }
}
