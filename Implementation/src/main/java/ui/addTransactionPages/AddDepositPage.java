package ui.addTransactionPages;

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
  private JLabel amountInpText;
  private JTextField AmountInp;
  private JLabel descriptionText;
  private JTextField DescriptionInp;
  private JLabel categoryText;
  private JComboBox categoryBox;
  private Object[] PayoutCategorys; /* = {"SALARY","DIVIDEND"}; */
  private String category;
  private String description;
  private float Amount;
  private User user;
  private JButton newOptionPane;


  private JButton SubmitButton;
  private JButton BackButton;

  private volatile boolean submitted;
  private volatile boolean backWanted;

  private volatile boolean refreshWanted;


  /**
   * @return DepositCategory Input from User or Default value.
   */
  public String getCatego() {
    return category;
  }
  /**
   * @return Description Input from User or Default value.
   */
  public String getDescription() {
    return description;
  }
  /**
   * @return Amount Input from User or Default value.
   */
  public float getAmount() {
    return Amount;
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
    PayoutCategorys = this.user.getCategories(new DepositCategory()).toArray();
    this.createComponents();
  }

  @Override
  protected void createComponents() {

    components = new ArrayList<>();
    submitted = false;
    backWanted = false;
    refreshWanted = false;

    categoryText = new JLabel("Payout Category:");
    categoryText.setBounds(100, 50, 300, 50);
    components.add(categoryText);

    // TODO - take categories of user instead
    categoryBox = new JComboBox(PayoutCategorys);
    categoryBox.setBounds(100, 100, 300, 50);
    components.add(categoryBox);

    newOptionPane = new JButton("Create Custom Deposit-Category");
    newOptionPane.setBounds(450,100,300,50);
    components.add(newOptionPane);

    amountInpText = new JLabel("Amount:");
    amountInpText.setBounds(100, 150, 300, 50);
    components.add(amountInpText);

    AmountInp = new JTextField("");
    AmountInp.setBounds(100, 200, 300, 50);
    components.add(AmountInp);

    descriptionText = new JLabel("Description:");
    descriptionText.setBounds(100, 250, 300, 50);
    components.add(descriptionText);

    DescriptionInp = new JTextField("");
    DescriptionInp.setBounds(100, 300, 300, 50);
    components.add(DescriptionInp);

    SubmitButton = new JButton("Deposit");
    SubmitButton.setBounds(100, 500, 300, 50);
    components.add(SubmitButton);

    newOptionPane.addActionListener(actionEvent -> {
      String categoryName = JOptionPane.showInputDialog("Enter Name of New Category!");
      System.out.println(categoryName);
      user.newTransactionCategory(new DepositCategory(categoryName));
      PayoutCategorys = user.getCategories(new DepositCategory()).toArray();
      refreshWanted = true;
    });

    SubmitButton.addActionListener(actionEvent -> {
      description = DescriptionInp.getText();
      Amount = Float.valueOf(AmountInp.getText());
      category = (String) categoryBox.getSelectedItem();

      //TODO negative AMOUNT
      //TODO strings are Empty

      System.out.println("Description: " + description +
          "\nAmount: " + Amount +
          "\nCategory: " + category
          );

      submitted = true;
    });


    BackButton = new JButton("BACK");
    BackButton.setBounds(10, 10, 100, 50);
    components.add(BackButton);
    BackButton.addActionListener(e -> backWanted = true);

  }

  @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("Add Deposit - Page");
  }
}
