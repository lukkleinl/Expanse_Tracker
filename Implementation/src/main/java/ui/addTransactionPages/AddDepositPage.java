package ui.addTransactionPages;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

import transactions.categories.DepositCategory;
import ui.main.AbstractPage;
import user.User;

/**
 * This Page collects all necessary data needed to create a new 'Deposit' Transaction. Implements the Interface 'InterfacePage'
 * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
 * @author Paul Kraft
 */

public class AddDepositPage extends AbstractPage {

  private JLabel introText;
  private JLabel amountInputText;
  private JTextField amountInputField;
  private JLabel descriptionText;
  private JTextField descriptionInputField;
  private JLabel categoryInputText;
  private JComboBox categoryInputBox;
  private Object[] payoutCategorys; /* = {"SALARY","DIVIDEND"}; */
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
   * @return DepositCategory Input from User or Default value.
   */
  public String getCategoryInputValue() {
    return categoryInputValue;
  }
  /**
   * @return Description Input from User or Default value.
   */
  public String getDescriptionInputValue() {
    return descriptionInputValue;
  }
  /**
   * @return Amount Input from User or Default value.
   */
  public float getAmountInputValue() {
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
   * Creates a new AddDepositPage, which will load all needed components to a list.
   */
  // In Final Version might take a User object to display additional User information.
  public AddDepositPage(User user)
  {
    this.user = user;
    payoutCategorys = this.user.getCategories(new DepositCategory()).toArray();
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

    categoryInputBox = new JComboBox(payoutCategorys);
    categoryInputBox.setBounds(100, 100, 300, 50);
    components.add(categoryInputBox);

    newCategoryOptionPaneButton = new JButton("Create Custom Deposit-Category");
    newCategoryOptionPaneButton.setBounds(450,100,300,50);
    components.add(newCategoryOptionPaneButton);

    amountInputText = new JLabel("Amount:");
    amountInputText.setBounds(100, 150, 300, 50);
    components.add(amountInputText);

    amountInputField = new JTextField("");
    amountInputField.setBounds(100, 200, 300, 50);
    components.add(amountInputField);

    descriptionText = new JLabel("Description:");
    descriptionText.setBounds(100, 250, 300, 50);
    components.add(descriptionText);

    descriptionInputField = new JTextField("");
    descriptionInputField.setBounds(100, 300, 300, 50);
    components.add(descriptionInputField);

    submitButton = new JButton("Deposit");
    submitButton.setBounds(100, 500, 300, 50);
    components.add(submitButton);

    newCategoryOptionPaneButton.addActionListener(actionEvent -> {
      String categoryName = JOptionPane.showInputDialog("Enter Name of New Category!");
      System.out.println(categoryName);
      user.newTransactionCategory(new DepositCategory(categoryName));
      payoutCategorys = user.getCategories(new DepositCategory()).toArray();
      refreshWanted = true;
    });

    submitButton.addActionListener(actionEvent -> {
      descriptionInputValue = descriptionInputField.getText();
      amountInputValue = Float.valueOf(amountInputField.getText());
      categoryInputValue = (String) categoryInputBox.getSelectedItem();

      if(amountInputValue<=0)
        //TODO handle Exception that is thrown @ALL
       // throw new IOException("Invalid Amount Input!");

      if(descriptionInputValue.isEmpty())
        //TODO handle Exception that is thrown @ALL
        // throw new IOException("Invalid Description Input, supply Description please!");


        //TODO check more Invalid Inputs?

      submitted = true;
    });


    backButton = new JButton("BACK");
    backButton.setBounds(10, 10, 100, 50);
    components.add(backButton);
    backButton.addActionListener(e -> backWanted = true);

  }

  @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("Add Deposit - Page");
  }
}
