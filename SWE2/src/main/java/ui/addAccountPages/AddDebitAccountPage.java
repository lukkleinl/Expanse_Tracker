package ui.addAccountPages;
/**
 * This Page collects all necessary data needed to create a new 'DebitCard'-Account.
 * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
 * @author Paul Kraft
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import ui.main.InterfacePage;

public class AddDebitAccountPage implements InterfacePage {

  public final static int FRAME_WIDTH = 1200;
  public final static int FRAME_HEIGHT = 800;

  public final static int SHIFT_LEFT = 300;
  public final static int OFFSET_Y = 50; // THE AMT OF PIXELS THE TEXT FIELDS ARE OFFSET TO THEIR CORRESPONDING JPANELS!

  private ArrayList<JComponent> components;
  private JLabel IntroText;
  private JLabel AccountName;
  private JLabel Limit;
  private JLabel BankName;
  private JLabel IBAN;
  private JButton SubmitButton;
  private JButton BackButton;

  private JTextField AccNameText;
  private JTextField LimitText;
  private JTextField BankNameText;
  private JTextField IBANField;
  private String AccName_String = "";

  private String BankName_String = "";
  private String IBAN_STRING = "";
  private float Limit_Double = 0.00f;

  private volatile boolean submitted;
  private volatile boolean backWanted;
  /**
   * Creates a new AddDebitAccountPage, which will load all needed components to a list.
   * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
   */
  // In Final Version might take a User object to display additional User information.
  public AddDebitAccountPage() {
    createComponents();
  }

  /**
   * Configures the JFrame, adds all the components(resets the values of the JTextFields to hard coded default).
   * Names the Frame to 'Add Debit Card Account'.
   * Sets the Size of the Frame. (Default is 1200 by 800).
   * calls revalidate() and repaint() on the Frame.
   * @param frame The JFrame, which components will be updated
   */
  public void configureFrame(JFrame frame) {
    createComponents();

    frame.setLayout(null);
    frame.setTitle("Add Debit Card Account");
    frame.getContentPane().removeAll();
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    for (JComponent comp : components) {
      frame.add(comp);
    }
    frame.revalidate();
    frame.repaint();
  }
  /**
   * @return AccName input from the User or default val.
   */
  public String getAccName() {
    return AccName_String;
  }
  /**
   * @return IBAN input from the User or default val.
   */
  public String getIBAN() {
    return IBAN_STRING;
  }
  /**
   * @return Limit input from the User or default val.
   */
  public float getLimit() {
    return Limit_Double;
  }
  /**
   * @return BankName input from the User or default val.
   */
  public String getBankName_String() {
    return BankName_String;
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

  private void createComponents() {
    components = new ArrayList<>();
    submitted = false;
    backWanted = false;

    IntroText = new JLabel(
        "Please Enter The relevant Data!" + "                    " + "logged in as:"
            + "<USERNAME>");
    IntroText.setBounds(300, 10, 800, 50);
    components.add(IntroText);

    AccountName = new JLabel("Account Name:");
    AccountName.setBounds(10 + SHIFT_LEFT, 200, 300, 50);
    components.add(AccountName);

    AccNameText = new JTextField();
    AccNameText.setBounds(10 + SHIFT_LEFT, 200 + OFFSET_Y, 300, 50);
    components.add(AccNameText);

    Limit = new JLabel("Limit:");
    Limit.setBounds(10 + SHIFT_LEFT, 300, 300, 50);
    components.add(Limit);

    LimitText = new JTextField("0.00");
    LimitText.setBounds(10 + SHIFT_LEFT, 300 + OFFSET_Y, 300, 50);
    components.add(LimitText);

    IBAN = new JLabel("IBAN:");
    IBAN.setBounds(10 + SHIFT_LEFT, 400, 300, 50);
    components.add(IBAN);

    IBANField = new JTextField();
    IBANField.setBounds(10 + SHIFT_LEFT, 400 + OFFSET_Y, 300, 50);
    components.add(IBANField);

    BankName = new JLabel("Bank Name:");
    BankName.setBounds(10 + SHIFT_LEFT, 500, 300, 50);
    components.add(BankName);

    BankNameText = new JTextField();
    BankNameText.setBounds(10 + SHIFT_LEFT, 500 + OFFSET_Y, 300, 50);
    components.add(BankNameText);

    SubmitButton = new JButton("SUBMIT");
    SubmitButton.setBounds(10 + SHIFT_LEFT, 650, 300, 50);
    components.add(SubmitButton);

    SubmitButton.addActionListener(new ActionListener() {
      @Override
      /**
       * @param e Action event
       * sets Submitted to true
       * extracts the Input Values and provides them for getters.
       */
      public void actionPerformed(ActionEvent e) {
        AccName_String = AccNameText.getText();
        IBAN_STRING = IBANField.getText();
        Limit_Double = Float.valueOf(LimitText.getText());
        BankName_String = BankNameText.getText();

        //TODO check limit and IBAN

        System.out.println(
            "Name: " + AccName_String +
                "\nIBAN: " + IBAN_STRING +
                "\nLimit: " + Limit_Double +
                "\nBankName: " + BankName_String
        );

        submitted = true;


      }
    });

    BackButton = new JButton("BACK");
    BackButton.setBounds(10, 10, 100, 50);
    components.add(BackButton);
    BackButton.addActionListener(new ActionListener() {
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
}
