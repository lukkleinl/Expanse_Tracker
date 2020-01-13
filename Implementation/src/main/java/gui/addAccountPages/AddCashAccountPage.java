package gui.addAccountPages;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import accounts.Cash;
import gui.main.AbstractPage;
import user.User;

/**
 * This Page collects all necessary data needed to create a new 'Cash'-Account. Implements the
 * Interface 'InterfacePage' By Pressing Submit(JButton) the Page saves the Values entered into the
 * JTextField's, who then can be aquired through getters.
 *
 * @author Paul Kraft
 * @author Patrick Gmasz
 */
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
   * Creates a new AddCashAccountPage, which will load all needed components to a list. By Pressing
   * Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be
   * aquired through getters.
   * @param user The User whose Data should be Displayed, or to whom the Account should be added.
   */
  public AddCashAccountPage(User user) {
    this.user = user;
    createComponents();
  }


  /** Getter for the AccountName that was inputed by the User.
   * @return AccName input from the User or default val. */
  public String getAccountName() {
    return accountNameInputValue;
  }
  /** Getter for the Currency that was inputed by the User.
   * @return Currency input from the User or default val. */
  public String getCurrency() {
    return currencyInputValue;
  }
  /**Getter for the Limit that was inputed by the User
   *  @return Limit input from the User or default val. */
  public float getLimit() {
    return limitInputValue;
  }
  /**
   * The page has a button, which the user can press if he wants to Submit his Data Pressing the
   * button will set a boolean flag, that the button was pressed. This method returns the boolean,
   * it will be true, if the user Submited his Data.
   *
   * @return The boolean flag, it will be true, if the user pressed the 'Submit' button.
   */
  public boolean isSubmitted() {
    return submitted;
  }
  /**
   * The page has a button, which the user can press if he wants to Go 1 Page back. Pressing the
   * button will set a boolean flag, that the button was pressed. This method returns the boolean,
   * it will be true, if the user wants to go back.
   *
   * @return The boolean flag, it will be true, if the user pressed the 'Back' button.
   */
  public boolean isBackWanted() {
    return backWanted;
  }

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
    submitButton.setBorder(new LineBorder(Color.BLACK, 2));
    submitButton.setFont(BUTTON_FONT);
    components.add(submitButton);

    submitButton.addActionListener(
        new ActionListener() {
          @Override
          /**sets Submitted to true extracts the Input Values and provides
           * them for getters.
           * @param e Action event
           */
          public void actionPerformed(ActionEvent e) {
            accountNameInputValue = accountNameInputField.getText();
            currencyInputValue = currencyInputField.getText();
            try {
            limitInputValue =
                (limitInputField.getText().isEmpty())
                    ? 0
                    : Float.valueOf(limitInputField.getText());

            } catch (NumberFormatException ex) {
              limitInputValue = 0;
            }
            if (accountNameInputValue.isEmpty()) {
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
    backButton.setBorder(new LineBorder(Color.BLACK, 2));
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
   * This method resets the title of the JFrame to "Login".
   *
   * @param frame The JFrame, which to update the title from.
   * Method to set the Frame Title(only called from within the UserInterface)
   * takes in a frame and sets its title to 'Add Cash Account- Page'
   * @param frame The Frame whose title should be set.
   */
  @Override
  protected void resetTitle(JFrame frame) {
    frame.setTitle("Add Cash Account - Page");
  }

    /**
     * Method to update the input - JTextfields.
     * Needed in case the Account is updated, so it can display the old Values.
     * @param account The (old) Transaction of which the Values should be displayed.
     */
  public void updateFields(Cash account) {
    accountNameInputField.setText(account.getName());
    limitInputField.setText(Double.toString(account.getLimit()));
    currencyInputField.setText(account.getCurrency());
  }
}
