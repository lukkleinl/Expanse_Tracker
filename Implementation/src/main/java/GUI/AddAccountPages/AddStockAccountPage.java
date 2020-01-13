package GUI.AddAccountPages;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import accounts.Stocks;
import GUI.Main.AbstractPage;
import user.User;
/**
 * This Page collects all necessary data needed to create a new 'Stocks'-Account. By Pressing
 * Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired
 * through getters.
 *
 * @author Paul Kraft
 * @author Patrick Gmasz
 */
public class AddStockAccountPage extends AbstractPage {

  private JLabel introTextLabel;
  private JLabel accountNameTextLabel;
  private JLabel limitTextLabel;
  private JLabel buydateTextLabel;
  private JButton submitButton;
  private JButton backButton;

  private JTextField accountNameInputField;
  private JFormattedTextField buyDateInputField;
  private JTextField limitInputField;

  private String accountNameInputValue = "";
  private Date buyDateInputValue;
  private float limitInputValue = 0.00f;

  private volatile boolean submitted;
  private volatile boolean backWanted;

  private User user;

  /**
   *Creates a new AddCashAccountPage, which will load all needed components to a list. By Pressing
   * Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be
   * aquired through getters.
   *@param user The User whose Data should be Displayed, or to whom the Account should be added.
   */
  public AddStockAccountPage(User user) {
    this.user = user;
    createComponents();
  }

  /**Getter
   *  @return accName input from the User or default val. */
  public String getAccName() {
    return accountNameInputValue;
  }
  /** @return BuyDate input from the User or default val. */
  public Date getBuyDate() {
    return buyDateInputValue;
  }
  /**Getter
   *  @return Limit input from the User or default val. */
  public float getLimit() {
    return limitInputValue;
  }
  /**
   *  The page has a button, which the user can press if he wants to Submit his Data Pressing the
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

    buydateTextLabel = new JLabel("Buy date:");
    buydateTextLabel.setBounds(300, 450, 300, 50);
    buydateTextLabel.setFont(LABEL_FONT);
    components.add(buydateTextLabel);

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    buyDateInputField = new JFormattedTextField(df);
    buyDateInputField.setText("dd/mm/yyyy");
    buyDateInputField.setBounds(600, 450, 300, 50);
    buyDateInputField.setFont(TEXTFIELD_FONT);
    components.add(buyDateInputField);

    submitButton = new JButton("SUBMIT");
    submitButton.setBounds(450, 600, 300, 50);
    submitButton.setFont(BUTTON_FONT);
    submitButton.setBorder(new LineBorder(Color.BLACK, 2));
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
            buyDateInputValue = (Date) buyDateInputField.getValue();

            limitInputValue = Float.valueOf(limitInputField.getText());

            if (limitInputValue < 0) {
              JOptionPane.showMessageDialog(
                  null, "Limit must be higher than 0", "Limit Error", JOptionPane.WARNING_MESSAGE);
            } else if (accountNameInputValue.isEmpty()) {
              JOptionPane.showMessageDialog(
                  null,
                  "You have to enter an accountname",
                  "Missing name error",
                  JOptionPane.WARNING_MESSAGE);
            } else if(buyDateInputValue==null)
            {
              JOptionPane.showMessageDialog(
                  null,
                  "You must insert a legit date with format dd/mm/yyyy!",
                  "Date Error",
                  JOptionPane.WARNING_MESSAGE);
            }
            else
            {
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
   * Method to set the Frame Title(only called from within the UserInterface)
   * takes in a frame and sets its title to 'Add Stock Account - Page'
   * @param frame The Frame whose title should be set.
   */
  @Override
  protected void resetTitle(JFrame frame) {
    frame.setTitle("Add Stock Account - Page");
  }

    /**
     * Method to update the input - JTextfields.
     * Needed in case the Account is updated, so it can display the old Values.
     * @param account The (old)Account of which the Values should be displayed.
     */
  public void updateFields(Stocks account) {
    accountNameInputField.setText(account.getName());
    buyDateInputField.setText(
        account.getBuyDate().getDay()
            + "/"
            + account.getBuyDate().getMonth()
            + "/"
            + account.getBuyDate().getYear());
    limitInputField.setText(Float.toString(account.getLimit()));
  }
}
