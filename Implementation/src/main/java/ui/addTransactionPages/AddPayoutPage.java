package ui.addTransactionPages;

import java.util.ArrayList;
import javax.swing.*;

import transactions.categories.PayoutCategory;
import ui.main.AbstractPage;
import user.User;

/**
 * This Page collects all necessary data needed to create a new 'Payout' Transaction. Implements the Interface 'InterfacePage'
 * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
 * @author Paul Kraft
 */


public class AddPayoutPage extends AbstractPage {

  private JLabel introTextLabel;
  private JLabel amountInputText;
  private JTextField amountInputField;
  private JLabel descriptionInputText;
  private JTextField descriptionInputField;
  private JLabel categoryInputText;
  private JComboBox categoryInputBox;
  private  Object[] payoutCategorys = {"EDUCATION","FOOD","TRANSPORTATION"};
  private String categoryInputValue;
  private String descriptionInputValue;
  private float amountInputValue;
  private User user;
  private JButton newCategoryOptionPaneButton;

  private JButton submitButton;
  private JButton backButton;

  private volatile boolean submitted;
  private volatile boolean backWanted;


  private volatile boolean refreshWanted;

  /**
   * @return PayoutCategory Input from User or Default value.
   */
  public String getCategory() {
    return categoryInputValue;
  }
  /**
   * @return Description Input from User or Default value.
   */
  public String getDescription() {
    return descriptionInputValue;
  }
  /**
   * @return Amount Input from User or Default value.
   */
  public float getAmount() throws Exception
  {
    return amountInputValue;
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

  public boolean isRefreshWanted() {
    return refreshWanted;
  }

  /**
   * Creates a new AddPayoutPage, which will load all needed components to a list.
   */
  // In Final Version might take a User object to display additional User information.
  public AddPayoutPage(User user){
    this.user = user;
    payoutCategorys = user.getCategories(new PayoutCategory()).toArray();
    this.createComponents();
  }

  @Override
  protected void createComponents() {

    components = new ArrayList<>();
    submitted = false;
    backWanted = false;
    refreshWanted = false;

    introTextLabel = new JLabel("Currently logged in as: " + user.getFirstname() + " " + user.getLastname() + ".");
    introTextLabel.setBounds(200, 10, 1000, 50);
    introTextLabel.setFont(HEADER_FONT);
    components.add(introTextLabel);

    categoryInputText = new JLabel("Payout category:");
    categoryInputText.setBounds(300, 150, 300, 50);
    categoryInputText.setFont(LABEL_FONT);
    components.add(categoryInputText);

    categoryInputBox = new JComboBox(payoutCategorys);
    categoryInputBox.setBounds(600, 150, 300, 50);
    categoryInputBox.setFont(TEXTFIELD_FONT);
    components.add(categoryInputBox);

    newCategoryOptionPaneButton = new JButton("Create category");
    newCategoryOptionPaneButton.setBounds(600,250,300,50);
    newCategoryOptionPaneButton.setFont(BUTTON_FONT);
    components.add(newCategoryOptionPaneButton);

    amountInputText = new JLabel("Amount:");
    amountInputText.setBounds(300, 350, 300, 50);
    amountInputText.setFont(LABEL_FONT);
    components.add(amountInputText);

    amountInputField = new JTextField();
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

    submitButton = new JButton("PAYOUT");
    submitButton.setBounds(450, 600, 300, 50);
    submitButton.setFont(BUTTON_FONT);
    components.add(submitButton);

    newCategoryOptionPaneButton.addActionListener(actionEvent -> {
        String categoryName = JOptionPane.showInputDialog("Enter name of new category!");
        System.out.println(categoryName);
        user.newTransactionCategory(new PayoutCategory(categoryName));
        payoutCategorys = user.getCategories(new PayoutCategory()).toArray();
        refreshWanted = true;
    });

    submitButton.addActionListener(actionEvent -> {
        amountInputValue = (amountInputField.getText().isEmpty()) ? 0 : Float.valueOf(amountInputField.getText());
        descriptionInputValue = descriptionInputField.getText();
        categoryInputValue = (String) categoryInputBox.getSelectedItem();

      if (amountInputValue <= 0) {
        JOptionPane.showMessageDialog(
                null, "Amount must be higher than 0", "Amount Error", JOptionPane.WARNING_MESSAGE);
      } else if(descriptionInputValue.isEmpty()) {
        JOptionPane.showMessageDialog(
                null, "You must insert a description", "Description Error", JOptionPane.WARNING_MESSAGE);
      } else {
        submitted = true;
      }
    });


    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
    backButton.setFont(BUTTON_FONT);
    components.add(backButton);
    backButton.addActionListener(e -> backWanted = true);

  }

  @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("Add Payout - Page");
  }
}
