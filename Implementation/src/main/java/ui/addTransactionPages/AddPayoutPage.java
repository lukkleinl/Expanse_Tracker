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

  private JLabel introText;
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

    categoryInputText = new JLabel("Payout Category:");
    categoryInputText.setBounds(100, 50, 300, 50);
    components.add(categoryInputText);

    newCategoryOptionPaneButton = new JButton("Create Custom Payout-Category");
    newCategoryOptionPaneButton.setBounds(450,100,300,50);
    components.add(newCategoryOptionPaneButton);


    // TODO - take categories of user instead
    categoryInputBox = new JComboBox(payoutCategorys);
    categoryInputBox.setBounds(100, 100, 300, 50);
    components.add(categoryInputBox);

    amountInputText = new JLabel("Amount:");
    amountInputText.setBounds(100, 150, 300, 50);
    components.add(amountInputText);

    amountInputField = new JTextField("");
    amountInputField.setBounds(100, 200, 300, 50);
    components.add(amountInputField);

    descriptionInputText = new JLabel("Description:");
    descriptionInputText.setBounds(100, 250, 300, 50);
    components.add(descriptionInputText);

    descriptionInputField = new JTextField("");
    descriptionInputField.setBounds(100, 300, 300, 50);
    components.add(descriptionInputField);

    submitButton = new JButton("Payout");
    submitButton.setBounds(100, 500, 300, 50);
    components.add(submitButton);

    newCategoryOptionPaneButton.addActionListener(actionEvent -> {
        String categoryName = JOptionPane.showInputDialog("Enter Name of New Category!");
        System.out.println(categoryName);
        user.newTransactionCategory(new PayoutCategory(categoryName));
        payoutCategorys = user.getCategories(new PayoutCategory()).toArray();
        refreshWanted = true;
    });

    submitButton.addActionListener(actionEvent -> {
        amountInputValue = (amountInputField.getText().isEmpty()) ? 1.0f : Float.valueOf(amountInputField.getText());
        descriptionInputValue = descriptionInputField.getText();
        categoryInputValue = (String) categoryInputBox.getSelectedItem();

        if(descriptionInputValue.isEmpty() || amountInputValue<=0){
            JOptionPane.showMessageDialog(null, "Invalid Input!");
        }else
            submitted = true;
    });


    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
    components.add(backButton);
    backButton.addActionListener(e -> backWanted = true);

  }

  public void checkValues() throws Exception{
    throw new Exception();
  }


  @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("Add Payout - Page");
  }
}
