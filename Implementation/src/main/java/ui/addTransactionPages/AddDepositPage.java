package ui.addTransactionPages;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

import transactions.Deposit;
import transactions.categories.DepositCategory;
import ui.main.AbstractPage;
import user.User;

/**
 * This Page collects all necessary data needed to create a new 'Deposit' Transaction. Implements
 * the Interface 'InterfacePage' By Pressing Submit(JButton) the Page saves the Values entered into
 * the JTextField's, who then can be aquired through getters.
 *
 * @author Paul Kraft
 * @author Patrick Gmasz
 */
public class AddDepositPage extends AbstractPage {

  private JLabel introTextLabel;
  private JLabel amountInputText;
  private JTextField amountInputField;
  private JLabel descriptionInputText;
  private JTextField descriptionInputField;
  private JLabel categoryInputText;
  private JComboBox categoryInputBox;
  private Object[] depositCategorys; /* = {"SALARY","DIVIDEND"}; */
  private String categoryInputValue;
  private String descriptionInputValue = "";
  private float amountInputValue = 1.0f;
  private User user;
  private JButton newCategoryOptionPaneButton;

  private JButton submitButton;
  private JButton backButton;

  private volatile boolean submitted;
  private volatile boolean backWanted;
  private volatile boolean refreshWanted;

  /** @return DepositCategory Input from User or Default value. */
  public String getCategory() {
    return categoryInputValue;
  }
  /** @return Description Input from User or Default value. */
  public String getDescription() {
    return descriptionInputValue;
  }
  /** @return Amount Input from User or Default value. */
  public float getAmount() {
    return amountInputValue;
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
  /**
   *  This Page can have the need to Refresh to Signal this to the User Interface there is a booleag Flag.
   *  The User Interface will regularly check if the Page wants to refresh.
   *
   * @return The boolean flag, it will be true, if the The Page needs to Refresh
   */
  public boolean isRefreshWanted() { return refreshWanted; }

  /** Creates a new AddDepositPage, which will load all needed components to a list. */
  // In Final Version might take a User object to display additional User information.
  public AddDepositPage(User user) {
    this.user = user;
    depositCategorys = this.user.getCategories(new DepositCategory()).toArray();
    this.createComponents();
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
    refreshWanted = false;

    introTextLabel =
        new JLabel(
            "Currently logged in as: " + user.getFirstname() + " " + user.getLastname() + ".");
    introTextLabel.setBounds(200, 10, 1000, 50);
    introTextLabel.setFont(HEADER_FONT);
    components.add(introTextLabel);

    categoryInputText = new JLabel("Deposit Category:");
    categoryInputText.setBounds(300, 150, 300, 50);
    categoryInputText.setFont(LABEL_FONT);
    components.add(categoryInputText);

    categoryInputBox = new JComboBox(depositCategorys);
    categoryInputBox.setBounds(600, 150, 300, 50);
    categoryInputBox.setFont(TEXTFIELD_FONT);
    components.add(categoryInputBox);

    newCategoryOptionPaneButton = new JButton("Create Category");
    newCategoryOptionPaneButton.setBounds(600, 250, 300, 50);
    newCategoryOptionPaneButton.setFont(BUTTON_FONT);
    newCategoryOptionPaneButton.setBorder(new LineBorder(Color.BLACK, 2));
    components.add(newCategoryOptionPaneButton);


    amountInputText = new JLabel("Amount:");
    amountInputText.setBounds(300, 350, 300, 50);
    amountInputText.setFont(LABEL_FONT);
    components.add(amountInputText);

    amountInputField = new JTextField("");
    amountInputField.setBounds(600, 350, 300, 50);
    amountInputField.setFont(TEXTFIELD_FONT);
    components.add(amountInputField);

    descriptionInputText = new JLabel("Description:");
    descriptionInputText.setBounds(300, 450, 300, 50);
    descriptionInputText.setFont(LABEL_FONT);
    components.add(descriptionInputText);

    descriptionInputField = new JTextField();
    descriptionInputField.setBounds(600, 450, 300, 50);
    descriptionInputField.setFont(TEXTFIELD_FONT);
    components.add(descriptionInputField);

    submitButton = new JButton("SUBMIT");
    submitButton.setBounds(450, 600, 300, 50);
    submitButton.setFont(BUTTON_FONT);
    submitButton.setBorder(new LineBorder(Color.BLACK, 2));
    components.add(submitButton);

    newCategoryOptionPaneButton.addActionListener(
        actionEvent -> {
          String categoryName = JOptionPane.showInputDialog("Enter name of new category!");
          if(categoryName!=null) {
            System.out.println(categoryName);
            user.newTransactionCategory(new DepositCategory(categoryName));
            depositCategorys = user.getCategories(new DepositCategory()).toArray();
            refreshWanted = true;
          }

        });

    submitButton.addActionListener(
        actionEvent -> {
          amountInputValue =
              (amountInputField.getText().isEmpty())
                  ? 0
                  : Float.valueOf(amountInputField.getText());
          descriptionInputValue = descriptionInputField.getText();
          categoryInputValue = (String) categoryInputBox.getSelectedItem();

          if (amountInputValue <= 0) {
            JOptionPane.showMessageDialog(
                null, "Amount must be higher than 0", "Amount Error", JOptionPane.WARNING_MESSAGE);
          } else if (descriptionInputValue.isEmpty()) {
            JOptionPane.showMessageDialog(
                null,
                "You must insert a description",
                "Description Error",
                JOptionPane.WARNING_MESSAGE);
          } else {
            submitted = true;
          }
        });

    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
    backButton.setFont(BUTTON_FONT);
    backButton.setBorder(new LineBorder(Color.BLACK, 2));
    components.add(backButton);
    backButton.addActionListener(e -> backWanted = true);
  }

  /**
   * Method to set the Frame Title(only called from within the UserInterface)
   * takes in a frame and sets its title to 'Add Deposit - Page'
   * @param frame the Frame whose title should be set.
   */
  @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("Add Deposit - Page");
  }

  /**
   * Method to update the input JTextfields
   * needed in case the deposit is updated, so it can display the old values.
   * @param  transaction the (old)Transaction whose Data should be Displayed.
   */
  public void updateFields(Deposit transaction) {
    amountInputField.setText(Float.toString(transaction.getAmount()));
    descriptionInputField.setText(transaction.getDescription());
  }
}
