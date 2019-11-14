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
import ui.main.InterfacePage;

public class AddStockAccountPage implements InterfacePage {

  public final static int FRAME_WIDTH = 1200;
  public final static int FRAME_HEIGHT = 800;

  public final static int SHIFT_LEFT = 300;
  public final static int OFFSET_Y = 50; // THE AMT OF PIXELS THE TEXT FIELDS ARE OFFSET TO THEIR CORRESPONDING JPANELS!

  private ArrayList<JComponent> components;
  private JLabel IntroText;
  private JLabel AccountName;
  private JLabel Limit;
  private JLabel BuyDate;
  private JButton SubmitButton;
  private JButton BackButton;

  private JTextField AccNameText;
  private JFormattedTextField BuyDateText;
  private JTextField LimitText;

  private String AccName_String = "";
  private Date BuyDate_Date;
  private float Limit_Double = 0.00f;

  private volatile boolean submitted;
  private volatile boolean backWanted;

  /**
   * Creates a new AddStockAccountPage, which will load all needed components to a list.
   */
  // In Final Version might take a User object to display additional User information.
  public AddStockAccountPage() {
    createComponents();
  }


  /**
   * Configures the JFrame, adds all the components(resets the values of the JTextFields to hard coded default).
   * Names the Frame to 'Add Stock Account'.
   * Sets the Size of the Frame. (Default is 1200 by 800).
   * calls revalidate() and repaint() on the Frame.
   * @param frame The JFrame, which components will be updated
   */
  public void configureFrame(JFrame frame) {
    createComponents();

    frame.setLayout(null);
    frame.setTitle("Add Stock Account");
    frame.getContentPane().removeAll();
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    for (JComponent comp : components) {
      frame.add(comp);
    }
    frame.revalidate();
    frame.repaint();
  }
  /**
   * @return accName input from the User or default val.
   */
  public String getAccName() {
    return AccName_String;
  }
  /**
   * @return BuyDate input from the User  or default val.
   */
  public Date getBuyDate() {
    return BuyDate_Date;
  }

  /**
   * @return Limit input from the User  or default val.
   */
  public float getLimit() {
    return Limit_Double;
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

    BuyDate = new JLabel("BuyDate:");
    BuyDate.setBounds(10 + SHIFT_LEFT, 400, 300, 50);
    components.add(BuyDate);

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    BuyDateText = new JFormattedTextField(df);
    BuyDateText.setText("dd/mm/yyyy");
    BuyDateText.setBounds(10 + SHIFT_LEFT, 400 + OFFSET_Y, 300, 50);
    components.add(BuyDateText);

    SubmitButton = new JButton("SUBMIT");
    SubmitButton.setBounds(10 + SHIFT_LEFT, 600, 300, 50);
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
        BuyDate_Date = (Date) BuyDateText.getValue();
        Limit_Double = Float.valueOf(LimitText.getText());

        //TODO check limit and DATE

        System.out.println(
            "Name: " + AccName_String +
                "\nBuyDate: " + BuyDate_Date +
                "\nLimit: " + Limit_Double
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