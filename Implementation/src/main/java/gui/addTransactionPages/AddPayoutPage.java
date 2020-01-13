package gui.addTransactionPages;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import gui.main.AbstractPage;
import transactions.Payout;
import transactions.categories.PayoutCategory;
import user.User;

/**
 * This Page collects all necessary data needed to create a new 'Payout' Transaction. Implements the
 * Interface 'InterfacePage' By Pressing Submit(JButton) the Page saves the Values entered into the
 * JTextField's, who then can be aquired through getters.
 *
 * @author Paul Kraft
 * @author Patrick Gmasz
 */
public class AddPayoutPage extends AbstractPage {

  private JLabel introTextLabel;
  private JLabel amountInputText;
  private JTextField amountInputField;
  private JLabel descriptionInputText;
  private JTextField descriptionInputField;
  private JLabel categoryInputText;
  private JComboBox categoryInputBox;
  private Object[] payoutCategorys = {"EDUCATION", "FOOD", "TRANSPORTATION"};
  private String categoryInputValue;
  private String descriptionInputValue;
  private float amountInputValue;
  private final User user;
  private JButton newCategoryOptionPaneButton;
  private JButton deleteCategoryButton;

  private JButton submitButton;
  private JButton backButton;

  private volatile boolean submitted;
  private volatile boolean backWanted;

  private volatile boolean refreshWanted;

  /**
   * Getter
   * 
   * @return PayoutCategory Input from User or Default value.
   */
  public String getCategory() {
    return this.categoryInputValue;
  }

  /**
   * Getter
   * 
   * @return Description Input from User or Default value.
   */
  public String getDescription() {
    return this.descriptionInputValue;
  }

  /**
   * Getter
   * 
   * @return Amount Input from User or Default value.
   */
  public float getAmount() {
    return this.amountInputValue;
  }

  /**
   * The page has a button, which the user can press if he wants to Submit his Data Pressing the
   * button will set a boolean flag, that the button was pressed. This method returns the boolean,
   * it will be true, if the user Submited his Data.
   *
   * @return The boolean flag, it will be true, if the user pressed the 'Submit' button.
   */
  public boolean isSubmitted() {
    return this.submitted;
  }

  /**
   * The page has a button, which the user can press if he wants to Go 1 Page back. Pressing the
   * button will set a boolean flag, that the button was pressed. This method returns the boolean,
   * it will be true, if the user wants to go back.
   *
   * @return The boolean flag, it will be true, if the user pressed the 'Back' button.
   */
  public boolean isBackWanted() {
    return this.backWanted;
  }

  /**
   * This Page can have the need to Refresh to Signal this to the User Interface there is a booleag
   * Flag. The User Interface will regularly check if the Page wants to refresh.
   *
   * @return The boolean flag, it will be true, if the The Page needs to Refresh
   */
  public boolean isRefreshWanted() {
    return this.refreshWanted;
  }

  /**
   * Creates a new AddPayoutPage, which will load all needed components to a list.
   * 
   * @param user the user who wants to add a new payout
   */
  // In Final Version might take a User object to display additional User information.
  public AddPayoutPage(final User user) {
    this.user = user;
    this.createComponents();
  }

  /**
   * creates all the Components that the JFrame should display(incl. Position,actionlisteners for
   * Buttons, text etc) also resets the booleans for backwanted, refreshwanted etc. is called from
   * within the configureFrame in abstract Page.
   */
  @Override
  protected void createComponents() {
    this.payoutCategorys = this.user.getCategories(new PayoutCategory()).toArray();
    this.components = new ArrayList<>();
    this.submitted = false;
    this.backWanted = false;
    this.refreshWanted = false;

    this.introTextLabel = new JLabel("Currently logged in as: " + this.user.getFirstname() + " "
        + this.user.getLastname() + ".");
    this.introTextLabel.setBounds(200, 10, 1000, 50);
    this.introTextLabel.setFont(HEADER_FONT);
    this.components.add(this.introTextLabel);

    this.categoryInputText = new JLabel("Payout category:");
    this.categoryInputText.setBounds(300, 150, 300, 50);
    this.categoryInputText.setFont(LABEL_FONT);
    this.components.add(this.categoryInputText);

    this.categoryInputBox = new JComboBox(this.payoutCategorys);
    this.categoryInputBox.setBounds(600, 150, 300, 50);
    this.categoryInputBox.setFont(TEXTFIELD_FONT);
    this.components.add(this.categoryInputBox);

    this.newCategoryOptionPaneButton = new JButton("Create category");
    this.newCategoryOptionPaneButton.setBounds(600, 250, 300, 50);
    this.newCategoryOptionPaneButton.setFont(BUTTON_FONT);
    this.newCategoryOptionPaneButton.setBorder(new LineBorder(Color.BLACK, 2));
    this.components.add(this.newCategoryOptionPaneButton);

    this.deleteCategoryButton = new JButton("Delete a category");
    this.deleteCategoryButton.setBounds(300, 250, 250, 50);
    this.deleteCategoryButton.setFont(BUTTON_FONT);
    this.deleteCategoryButton.setBorder(new LineBorder(Color.BLACK, 2));
    this.deleteCategoryButton.addActionListener(
        actionEvent -> {
          JComboBox box = new JComboBox(this.payoutCategorys);
          int delete =
              JOptionPane.showConfirmDialog(
                  null, box, "Select category you want to delete.", JOptionPane.CANCEL_OPTION);
          if (delete == JOptionPane.YES_OPTION) {
            if (box.getSelectedItem().toString().equals("EDUCATION")
                || box.getSelectedItem().toString().equals("FOOD")
                || box.getSelectedItem().toString().equals("TRANSPORTATION")) {
              JOptionPane.showMessageDialog(
                      null,
                      "Default categories can not be deleted!",
                      "Category Error",
                      JOptionPane.WARNING_MESSAGE);
            } else {
              this.user.removeTransactionCategory(box.getSelectedItem().toString());
              this.refreshWanted = true;
            }
          }
        });
    this.components.add(this.deleteCategoryButton);

    this.amountInputText = new JLabel("Amount:");
    this.amountInputText.setBounds(300, 350, 300, 50);
    this.amountInputText.setFont(LABEL_FONT);
    this.components.add(this.amountInputText);

    this.amountInputField = new JTextField();
    this.amountInputField.setBounds(600, 350, 300, 50);
    this.amountInputField.setFont(TEXTFIELD_FONT);
    this.components.add(this.amountInputField);

    this.descriptionInputText = new JLabel("Description:");
    this.descriptionInputText.setBounds(300, 450, 300, 50);
    this.descriptionInputText.setFont(LABEL_FONT);
    this.components.add(this.descriptionInputText);

    this.descriptionInputField = new JTextField();
    this.descriptionInputField.setBounds(600, 450, 300, 50);
    this.descriptionInputField.setFont(TEXTFIELD_FONT);
    this.components.add(this.descriptionInputField);

    this.submitButton = new JButton("SUBMIT");
    this.submitButton.setBounds(450, 600, 300, 50);
    this.submitButton.setBorder(new LineBorder(Color.BLACK, 2));
    this.submitButton.setFont(BUTTON_FONT);
    this.components.add(this.submitButton);

    this.newCategoryOptionPaneButton.addActionListener(actionEvent -> {
      String categoryName = JOptionPane.showInputDialog("Enter name of new category!");
      if (categoryName != null) {
        this.user.newTransactionCategory(new PayoutCategory(categoryName));
        this.payoutCategorys = this.user.getCategories(new PayoutCategory()).toArray();
        this.refreshWanted = true;
      }
    });

    this.submitButton.addActionListener(actionEvent -> {
      try {
      this.amountInputValue = (this.amountInputField.getText().isEmpty()) ? 0
          : Float.valueOf(this.amountInputField.getText());
      } catch (NumberFormatException e) {
        amountInputValue = 0;
      }
      this.descriptionInputValue = this.descriptionInputField.getText();
      this.categoryInputValue = (String) this.categoryInputBox.getSelectedItem();

      if (this.amountInputValue <= 0) {
        JOptionPane.showMessageDialog(null, "Amount must be a number higher than 0", "Amount Error",
            JOptionPane.WARNING_MESSAGE);
      } else if (this.descriptionInputValue.isEmpty()) {
        JOptionPane.showMessageDialog(null, "You must insert a description", "Description Error",
            JOptionPane.WARNING_MESSAGE);
      } else if (this.categoryInputValue == null) {
        JOptionPane.showMessageDialog(null, "You must insert a category", "Category Error",
            JOptionPane.WARNING_MESSAGE);
      } else {
        this.submitted = true;
      }
    });

    this.backButton = new JButton("BACK");
    this.backButton.setBounds(10, 10, 100, 50);
    this.backButton.setFont(BUTTON_FONT);
    this.backButton.setBorder(new LineBorder(Color.BLACK, 2));
    this.components.add(this.backButton);
    this.backButton.addActionListener(e -> this.backWanted = true);
  }

  /**
   * Method to set the Frame Title(only called from within the UserInterface) takes in a frame and
   * sets its title to 'Add Deposit - Page'
   * 
   * @param frame The Frame whose title should be reset.
   */
  @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("Add/Change Payout - Page");
  }

  /**
   * Method to update the input JTextfields needed in case the deposit is updated, so it can display
   * the old values.
   * 
   * @param transaction The (old)Transaction whose Data should be Displayed.
   */
  public void updateFields(final Payout transaction) {
    this.amountInputField.setText(Float.toString(transaction.getAmount()));
    this.descriptionInputField.setText(transaction.getDescription());
  }
}
