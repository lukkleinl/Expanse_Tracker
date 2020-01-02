package ui.addAccountPages;


/**
 * This Page collects all necessary data needed to create a new 'Cash'-Account. Implements the Interface 'InterfacePage'
 * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
 * @author Paul Kraft
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    createComponents();
    this.user = user;
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

    introTextLabel = new JLabel("Enter the needed information.");
    introTextLabel.setBounds(100, 100, 1000, 50);
    introTextLabel.setFont(HEADER_FONT);
    components.add(introTextLabel);

    accountNameTextLabel = new JLabel("Account Name:");
    accountNameTextLabel.setBounds(300, 200, 300, 50);
    accountNameTextLabel.setFont(LABEL_FONT);
    components.add(accountNameTextLabel);

    accountNameInputField = new JTextField();
    accountNameInputField.setBounds(600, 200, 300, 50);
    accountNameInputField.setFont(TEXTFIELD_FONT);
    components.add(accountNameInputField);

    limitTextLabel = new JLabel("Limit:");
    limitTextLabel.setBounds(300, 300, 300, 50);
    limitTextLabel.setFont(LABEL_FONT);
    components.add(limitTextLabel);

    limitInputField = new JTextField("0.00");
    limitInputField.setBounds(600, 300, 300, 50);
    limitInputField.setFont(TEXTFIELD_FONT);
    components.add(limitInputField);

    currencyTextLabel = new JLabel("Currency:");
    currencyTextLabel.setBounds(300, 400, 300, 50);
    currencyTextLabel.setFont(LABEL_FONT);
    components.add(currencyTextLabel);

    currencyInputField = new JTextField();
    currencyInputField.setBounds(600, 400, 300, 50);
    currencyInputField.setFont(TEXTFIELD_FONT);
    components.add(currencyInputField);

    submitButton = new JButton("SUBMIT");
    submitButton.setBounds(450, 550, 300, 50);
    components.add(submitButton);

    submitButton.addActionListener(new ActionListener() {
      @Override
      /**
       * @param e Action event
       * sets Submitted to true
       * extracts the Input Values and provides them for getters.
       */
      public void actionPerformed(ActionEvent e) {
        accountNameInputValue = accountNameInputField.getText();
        currencyInputValue = currencyInputField.getText();
        limitInputValue = Float.valueOf(limitInputField.getText());


        //TODO HANDLE EXCEPTIONS!

        if (limitInputValue < 0)
          // throw new IOException("Invalid limit Input!");
        if(accountNameInputValue.isEmpty())
          // throw new IOException("Invalid AccountName Input!");
        if(currencyInputValue.isEmpty())
          // throw new IOException("Invalid Currency Input!");


          submitted = true;
      }
    });

    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
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
}