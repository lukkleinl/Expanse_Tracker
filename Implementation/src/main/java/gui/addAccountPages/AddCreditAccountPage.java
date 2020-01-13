package gui.addAccountPages;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import accounts.CreditCard;
import gui.main.AbstractPage;
import user.User;

/**
 * This Page collects all necessary data needed to create a new 'CreditCard'-Account. By Pressing
 * Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired
 * through getters.
 *
 * @author Paul Kraft
 * @author Patrick Gmasz
 */
public class AddCreditAccountPage extends AbstractPage {

  private JLabel introTextLabel;
  private JLabel accountNameTextLabel;
  private JLabel limitTextLabel;
  private JLabel bankNameTextLabel;
  private JLabel expiryDateTextLabel;
  private JButton submitButton;
  private JButton backButton;

  private JTextField accountNameInputField;
  private JTextField limitInputField;
  private JTextField bankNameInputField;
  private JFormattedTextField expiryDateInputField;

  private String accountNameInputValue = "";
  private String bankNameInputValue = "";
  private Date expiryDateInputValue;
  private float limitInputValue = 0.00f;

  private volatile boolean submitted;
  private volatile boolean backWanted;

  private User user;

  /**
   * Creates a new AddCreditAccountPage, which will load all needed components to a list. By
   * Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can
   * be acquired through getters.
   *
   * @author Paul Kraft
   * @author Patrick Gmasz
   * be aquired through getters.
   * @param user The User whose Data should be Displayed, or to whom the Account should be added.
   */
  public AddCreditAccountPage(User user) {
    this.user = user;
    createComponents();
  }

  /**
   * Returns the input from the account name field.
   *
   * @return The input from the user for account name.
   */
  public String getAccountName() {
    return accountNameInputValue;
  }

  /**
   * Returns the input from the expiry date field.
   *
   * @return Expiry date input from the user.
   */
  public Date getExpiryDate() {
    return expiryDateInputValue;
  }

  /**
   * Returns the input from the limit field.
   * @return Limit input from the user.
   */
  public float getLimit() {
    return limitInputValue;
  }

  /**
   * Returns the input from the bank name field.
   *
   * @return Bank Name input from the User or default val.
   */
  public String getBankName() {
    return bankNameInputValue;
  }

  /**
   * Returns a boolean, if the submit button got pressed.
   *
   * @return Returns true, if the submit button got pressed, else false.
  /**
   * The page has a button, which the user can press if he wants to Submit his Data Pressing the
   * button will set a boolean flag, that the button was pressed. This method returns the boolean,
   * it will be true, if the user Submitted his Data.
   *
   * @return The boolean flag, it will be true, if the user pressed the 'Submit' button.
   */
  public boolean isSubmitted() {
    return submitted;
  }

  /**
   * Returns a boolean, if the back button got pressed.
   *
   * @return Returns true, if the back button got pressed, else false.
  /**
   * The page has a button, which the user can press if he wants to Go 1 Page back. Pressing the
   * button will set a boolean flag, that the button was pressed. This method returns the boolean,#
   * it will be true, if the user wants to go back.
   *
   * @return The boolean flag, it will be true, if the user pressed the 'Back' button.
   */
  public boolean isBackWanted() {
    return backWanted;
  }

  /**
   * This method creates all components, such as buttons and text fields, and adds it to a list. It
   * also sets every indicator variables to default and updates the account list of the user.
   */
    /** creates all the Components that the JFrame should display(incl. Position,actionlisteners for Buttons, text etc)
     * also resets the booleans for backwanted, refreshwanted etc.
     * is called from within the configureFrame in abstract Page.
     */
  @Override
  protected void createComponents() {
    components = new ArrayList<>();
    submitted = false;
    backWanted = false;

    introTextLabel =
        new JLabel(
            "Currently logged in as: " + user.getFirstname() + " " + user.getLastname() + ".");
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
    limitInputField.setBounds(600, 250, 200, 50);
    limitInputField.setFont(TEXTFIELD_FONT);
    components.add(limitInputField);

    expiryDateTextLabel = new JLabel("Expiry-Date:");
    expiryDateTextLabel.setBounds(300, 350, 300, 50);
    expiryDateTextLabel.setFont(LABEL_FONT);
    components.add(expiryDateTextLabel);

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    expiryDateInputField = new JFormattedTextField(df);
    expiryDateInputField.setText("dd/mm/yyyy");
    expiryDateInputField.setFont(TEXTFIELD_FONT);
    expiryDateInputField.setBounds(600, 350, 300, 50);
    components.add(expiryDateInputField);

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
    submitButton.setBorder(new LineBorder(Color.BLACK, 2));
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
            expiryDateInputValue = (Date) expiryDateInputField.getValue();
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
                  "You must insert a bank name!",
                  "Bank Name Error",
                  JOptionPane.WARNING_MESSAGE);
            } else if (expiryDateInputValue == null) {
              JOptionPane.showMessageDialog(
                  null,
                  "You must insert a legit date with format dd/mm/yyyy!",
                  "Date Error",
                  JOptionPane.WARNING_MESSAGE);
            } else {
              submitted = true;
            }
          }
        });

    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
    backButton.setBorder(new LineBorder(Color.BLACK, 2));
    backButton.setFont(BUTTON_FONT);
    components.add(backButton);
    backButton.addActionListener(
        new ActionListener() {
          @Override
          /** @param e Action event sets BackWanted to true */
          public void actionPerformed(ActionEvent e) {
            backWanted = true;
          }
        });
  }


    /**
     * Method to set the Frame Title(only called from within the UserInterface)
     * takes in a frame and sets its title to 'Add credit Card Account- Page'
     * @param frame The Frame whose title should be set.
     */
  @Override
  protected void resetTitle(JFrame frame) {
    frame.setTitle("Add Credit Card Account - Page");
  }


  /**
   * Updates the text fields with actual values from an account.
   *
   * @param account The account, of which the values shall be displayed.
   */
    /**
     * Method to update the input - JTextfields.
     * Needed in case the Account is updated, so it can display the old Values.
     * @param account The (old) Account of which the Values should be displayed.
     */
  public void updateFields(CreditCard account) {
    accountNameInputField.setText(account.getName());
    limitInputField.setText(Double.toString(account.getLimit()));
    bankNameInputField.setText(account.getBankName());
    expiryDateInputField.setText(
        account.getExpiryDate().getDay()
            + "/"
            + account.getExpiryDate().getMonth()
            + "/"
            + account.getExpiryDate().getYear());
  }
}
