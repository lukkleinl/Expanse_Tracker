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

public class AddCashAccountPage extends AbstractPage {

  private final static int SHIFT_LEFT = 300;
  private final static int OFFSET_Y = 50; // THE AMT OF PIXELS THE TEXT FIELDS ARE OFFSET TO THEIR CORRESPONDING JPANELS!

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

  /**
   * Creates a new AddCashAccountPage, which will load all needed components to a list.
   * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
   */
  // In Final Version might take a User object to display additional User information.
  public AddCashAccountPage() {
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

    introTextLabel = new JLabel(
        "Please Enter The relevant Data!" + "                    " + "logged in as:"
            + "<USERNAME>");
    introTextLabel.setBounds(300, 10, 800, 50);
    components.add(introTextLabel);

    accountNameTextLabel = new JLabel("Account Name:");
    accountNameTextLabel.setBounds(10 + SHIFT_LEFT, 200, 300, 50);
    components.add(accountNameTextLabel);

    accountNameInputField = new JTextField();
    accountNameInputField.setBounds(10 + SHIFT_LEFT, 200 + OFFSET_Y, 300, 50);
    components.add(accountNameInputField);

    limitTextLabel = new JLabel("Limit:");
    limitTextLabel.setBounds(10 + SHIFT_LEFT, 300, 300, 50);
    components.add(limitTextLabel);

    limitInputField = new JTextField("0.00");
    limitInputField.setBounds(10 + SHIFT_LEFT, 300 + OFFSET_Y, 300, 50);
    components.add(limitInputField);

    currencyTextLabel = new JLabel("Currency:");
    currencyTextLabel.setBounds(10 + SHIFT_LEFT, 400, 300, 50);
    components.add(currencyTextLabel);

    currencyInputField = new JTextField();
    currencyInputField.setBounds(10 + SHIFT_LEFT, 400 + OFFSET_Y, 300, 50);
    components.add(currencyInputField);

    submitButton = new JButton("SUBMIT");
    submitButton.setBounds(10 + SHIFT_LEFT, 600, 300, 50);
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