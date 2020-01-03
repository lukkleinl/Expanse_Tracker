package ui.addTransactionPages;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

import transactions.categories.DepositCategory;
import ui.main.AbstractPage;
import user.User;

/**
 * This Page collects all necessary data needed to create a new 'Deposit' Transaction. Implements the Interface 'InterfacePage'
 * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
 * @author Paul Kraft
 */

public class AddDepositPage extends AbstractPage{

  private JLabel introTextLabel;
  private JLabel amountInputText;
  private JTextField amountInputField;
  private JLabel descriptionInputText;
  private JTextField descriptionInputField;
  private JLabel categoryInputText;
  private JComboBox categoryInputBox;
  private Object[] depositCategorys; /* = {"SALARY","DIVIDEND"}; */
  private String categoryInputValue;
  private String descriptionInputValue ="";
  private float amountInputValue = 1.0f;
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
  public float getAmount() { return amountInputValue; }
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
    depositCategorys = this.user.getCategories(new DepositCategory()).toArray();
    this.createComponents();
  }

  @Override
  protected void createComponents(){

    components = new ArrayList<>();
    submitted = false;
    backWanted = false;
    refreshWanted = false;

    introTextLabel = new JLabel("Currently logged in as: " + user.getFirstname() + " " + user.getLastname() + ".");
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
    newCategoryOptionPaneButton.setBounds(600,250,300,50);
    newCategoryOptionPaneButton.setFont(BUTTON_FONT);
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

    submitButton = new JButton("DEPOSIT");
    submitButton.setBounds(450, 600, 300, 50);
    submitButton.setFont(BUTTON_FONT);
    submitButton.setBorder(new LineBorder(Color.BLACK,2));
    components.add(submitButton);

    newCategoryOptionPaneButton.addActionListener(actionEvent -> {
      String categoryName = JOptionPane.showInputDialog("Enter name of new category!");
      System.out.println(categoryName);
      user.newTransactionCategory(new DepositCategory(categoryName));
      depositCategorys = user.getCategories(new DepositCategory()).toArray();
      refreshWanted = true;
    });

    submitButton.addActionListener(
        actionEvent -> {
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
    backButton.setBorder(new LineBorder(Color.BLACK,2));
    components.add(backButton);
    backButton.addActionListener(e -> backWanted = true);

  }

  @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("Add Deposit - Page");
  }
}
