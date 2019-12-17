package ui.addTransactionPages;

import java.util.ArrayList;
import javax.swing.*;

import transactions.categories.DepositCategory;
import transactions.categories.PayoutCategory;
import ui.main.AbstractPage;
import user.User;

/**
 * This Page collects all necessary data needed to create a new 'Deposit' Transaction. Implements the Interface 'InterfacePage'
 * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
 * @author Paul Kraft
 */

public class AddDepositPage extends AbstractPage {

  private JLabel IntroText;
  private JLabel Amounttext;
  private JTextField AmountInp;
  private JLabel descriptiontext;
  private JTextField DescriptionInp;
  private JLabel Categorytext;
  private JComboBox CategorysBox;
  private volatile Object[] PayoutCategorys; /* = {"SALARY","DIVIDEND"}; */
  private String category;
  private String description;
  private float Amount;
  private User user;
  private JButton newOptionPane;


  private JButton SubmitButton;
  private JButton BackButton;

  private volatile boolean submitted;
  private volatile boolean backWanted;

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

    Categorytext = new JLabel("Payout Category:");
    Categorytext.setBounds(100, 50, 300, 50);
    components.add(Categorytext);

    // TODO - take categories of user instead
    CategorysBox = new JComboBox(PayoutCategorys);
    CategorysBox.setBounds(100, 100, 300, 50);
    components.add(CategorysBox);

    newOptionPane = new JButton("Create Custom Deposit-Category");
    newOptionPane.setBounds(450,100,300,50);
    components.add(newOptionPane);

    Amounttext = new JLabel("Amount:");
    Amounttext.setBounds(100, 150, 300, 50);
    components.add(Amounttext);

    AmountInp = new JTextField("");
    AmountInp.setBounds(100, 200, 300, 50);
    components.add(AmountInp);

    descriptiontext = new JLabel("Description:");
    descriptiontext.setBounds(100, 250, 300, 50);
    components.add(descriptiontext);

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
    });

    SubmitButton.addActionListener(actionEvent -> {
      description = DescriptionInp.getText();
      Amount = Float.valueOf(AmountInp.getText());
      category = (String) CategorysBox.getSelectedItem();

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
