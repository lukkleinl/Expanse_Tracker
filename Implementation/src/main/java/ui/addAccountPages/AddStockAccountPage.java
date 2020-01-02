package ui.addAccountPages;
/**
 * This Page collects all necessary data needed to create a new 'Stocks'-Account.
 * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
 * @author Paul Kraft
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import ui.main.AbstractPage;
import user.User;

public class AddStockAccountPage extends AbstractPage {

  private final static int SHIFT_LEFT = 300;
  private final static int OFFSET_Y = 50; // THE AMT OF PIXELS THE TEXT FIELDS ARE OFFSET TO THEIR CORRESPONDING JPANELS!

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
   * Creates a new AddStockAccountPage, which will load all needed components to a list.
   */
  // In Final Version might take a User object to display additional User information.
  public AddStockAccountPage(User user) {
    this.user = user;
    createComponents();
  }

  /**
   * @return accName input from the User or default val.
   */
  public String getAccName() {
    return accountNameInputValue;
  }
  /**
   * @return BuyDate input from the User  or default val.
   */
  public Date getBuyDate() {
    return buyDateInputValue;
  }

  /**
   * @return Limit input from the User  or default val.
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
        buyDateInputValue = (Date) buyDateInputField.getValue();
        limitInputValue = Float.valueOf(limitInputField.getText());

        if (limitInputValue < 0)
          // throw new IOException("Invalid limit Input!");
        if(accountNameInputValue.isEmpty())
          // throw new IOException("Invalid AccountName Input!");

        //TODO check buyDate Stuff?!?!

        submitted = true;
      }
    });

    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
    backButton.setFont(BUTTON_FONT);
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
    frame.setTitle("Add Stock Account");
  }
}