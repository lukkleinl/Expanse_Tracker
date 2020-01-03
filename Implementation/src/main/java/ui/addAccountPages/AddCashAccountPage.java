package ui.addAccountPages;


/**
 * This Page collects all necessary data needed to create a new 'Cash'-Account. Implements the Interface 'InterfacePage'
 * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
 * @author Paul Kraft
 */

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import accounts.Account;
import accounts.Cash;
import ui.main.AbstractPage;
import user.User;

public class AddCashAccountPage extends AbstractPage {

  private JLabel introTextLabel;
  private JLabel accountNameTextLabel;
  private JLabel limitTextLabel;
  private JLabel currencyTextLabel;
  private JButton submitButton;
  private JButton backButton;

  private JTextField accountNameInputField;
  private JTextField currencyInputField;
  private JTextField limitInputField;

  private String accountNameInputValue = "";
  private String currencyInputValue = "";
  private float limitInputValue = 0.00f;

  private volatile boolean submitted;
  private volatile boolean backWanted;

  private User user;
  /**
   * Creates a new AddCashAccountPage, which will load all needed components to a list.
   * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
   */
  // In Final Version might take a User object to display additional User information.
  public AddCashAccountPage(User user) {
    this.user = user;
    createComponents();
  }

  /**
   * Configures the JFrame, adds all the components(resets the values of the JTextFields to hard coded default).
   * Names the Frame to 'Add Cash Account'.
   * Sets the Size of the Frame. (Default is 1200 by 800).
   * calls revalidate() and repaint() on the Frame.
   * @param frame The JFrame, which components will be updated
   */

  /**
   * @return AccName input from the User or default val.
   */
  public String getAccountName() { return accountNameInputValue; }
  /**
   * @return Currency input from the User or default val.
   */
  public String getCurrency() {
    return currencyInputValue;
  }
  /**
   * @return Limit input from the User or default val.
   */
  public float getLimit() {
    return limitInputValue;
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
    accountNameTextLabel.setBounds(300, 250, 300, 50);
    accountNameTextLabel.setFont(LABEL_FONT);
    components.add(accountNameTextLabel);

    accountNameInputField = new JTextField();
    accountNameInputField.setBounds(600, 250, 300, 50);
    accountNameInputField.setFont(TEXTFIELD_FONT);
    components.add(accountNameInputField);

    limitTextLabel = new JLabel("Limit:");
    limitTextLabel.setBounds(300, 350, 300, 50);
    limitTextLabel.setFont(LABEL_FONT);
    components.add(limitTextLabel);

    limitInputField = new JTextField("0.00");
    limitInputField.setBounds(600, 350, 300, 50);
    limitInputField.setFont(TEXTFIELD_FONT);
    components.add(limitInputField);

    currencyTextLabel = new JLabel("Currency:");
    currencyTextLabel.setBounds(300, 450, 300, 50);
    currencyTextLabel.setFont(LABEL_FONT);
    components.add(currencyTextLabel);

    currencyInputField = new JTextField();
    currencyInputField.setBounds(600, 450, 300, 50);
    currencyInputField.setFont(TEXTFIELD_FONT);
    components.add(currencyInputField);

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
            currencyInputValue = currencyInputField.getText();
            limitInputValue = (limitInputField.getText().isEmpty()) ? 0 : Float.valueOf(limitInputField.getText());

            if (limitInputValue < 0) {
              JOptionPane.showMessageDialog(
                  null, "Limit must be higher than 0", "Limit Error", JOptionPane.WARNING_MESSAGE);
            } else if (accountNameInputValue.isEmpty()) {
              JOptionPane.showMessageDialog(
                  null,
                  "You must insert an account name!",
                  "Account Name Error",
                  JOptionPane.WARNING_MESSAGE);
            } else if (currencyInputValue.isEmpty()) {
              JOptionPane.showMessageDialog(
                  null,
                  "You must insert a currency!",
                  "Currency Error",
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
    frame.setTitle("Add Cash Account");
  }

  public void updateFields(Cash account) {
    accountNameInputField.setText(account.getName());
    limitInputField.setText(Double.toString(account.getLimit()));
    currencyInputField.setText(account.getCurrency());
  }
}