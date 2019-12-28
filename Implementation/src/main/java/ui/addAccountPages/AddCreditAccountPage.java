package ui.addAccountPages;
/**
 * This Page collects all necessary data needed to create a new 'CreditCard'-Account.
 * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
 * @author Paul Kraft
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import ui.main.AbstractPage;

public class AddCreditAccountPage extends AbstractPage {

  private final static int SHIFT_LEFT = 300;
  private final static int OFFSET_Y = 50; // THE AMT OF PIXELS THE TEXT FIELDS ARE OFFSET TO THEIR CORRESPONDING JPANELS!

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

  /**
   * Creates a new AddCreditAccountPage, which will load all needed components to a list.
   * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
   */
  // In Final Version might take a User object to display additional User information.
  public AddCreditAccountPage() {
    createComponents();
  }

  /**
   * @return accName input from the User or default val.
   */
  public String getAccountName() {
    return accountNameInputValue;
  }
  /**
   * @return Expiy Date input from the User or default val.
   */
  public Date getExpiryDate() {
    return expiryDateInputValue;
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

    expiryDateTextLabel = new JLabel("Expiry-Date:");
    expiryDateTextLabel.setBounds(10 + SHIFT_LEFT, 400, 300, 50);
    components.add(expiryDateTextLabel);

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    expiryDateInputField = new JFormattedTextField(df);
    expiryDateInputField.setText("dd/mm/yyyy");
    expiryDateInputField.setBounds(10 + SHIFT_LEFT, 400 + OFFSET_Y, 300, 50);
    components.add(expiryDateInputField);

    bankNameTextLabel = new JLabel("Bank Name:");
    bankNameTextLabel.setBounds(10 + SHIFT_LEFT, 500, 300, 50);
    components.add(bankNameTextLabel);

    bankNameInputField = new JTextField();
    bankNameInputField.setBounds(10 + SHIFT_LEFT, 500 + OFFSET_Y, 300, 50);
    components.add(bankNameInputField);

    submitButton = new JButton("SUBMIT");
    submitButton.setBounds(10 + SHIFT_LEFT, 650, 300, 50);
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
        expiryDateInputValue = (Date) expiryDateInputField.getValue();
        limitInputValue = Float.valueOf(limitInputField.getText());
        bankNameInputValue = bankNameInputField.getText();

        //TODO HANDLE EXCEPTIONS!

      //https://www.javatpoint.com/java-get-current-date
      //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        if (limitInputValue < 0)
          // throw new IOException("Invalid limit Input!");
        if(accountNameInputValue.isEmpty())
          // throw new IOException("Invalid AccountName Input!");
        if(bankNameInputValue.isEmpty())
          //throw new IOException("Invalid BankName Input!");

        //TODO check Expiry Stuff?!?!

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
    frame.setTitle("Add Credit Card Account");
  }
}