package ui.addAccountPages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import ui.main.InterfacePage;

public class AddCashAccountPage implements InterfacePage {

  private final static int FRAME_WIDTH = 1200;
  private final static int FRAME_HEIGHT = 800;

  private final static int SHIFT_LEFT = 300;
  private final static int OFFSET_Y = 50; // THE AMT OF PIXELS THE TEXT FIELDS ARE OFFSET TO THEIR CORRESPONDING JPANELS!

  private ArrayList<JComponent> components;
  private JLabel IntroText;
  private JLabel AccountName;
  private JLabel Limit;
  private JLabel Currency;
  private JButton SubmitButton;
  private JButton BackButton;

  private JTextField AccNameText;
  private JTextField CurrencyText;
  private JTextField LimitText;

  private String AccName_String = "";
  private String Currency_String = "";
  private float Limit_Double = 0.00f;

  private boolean submitted;
  private boolean backWanted;


  public AddCashAccountPage() {
    createComponents();

  }

  /* TESTING PURPOSES ONLY */
  public static void main(String args[]) {

    JFrame frame = new JFrame();
    AddCashAccountPage addp = new AddCashAccountPage();
    addp.configureFrame(frame);

  }

  public void configureFrame(JFrame frame) {
    createComponents();

    //frame.setVisible(false);
    frame.setLayout(null);
    frame.setTitle("Add Cash Account");
    frame.getContentPane().removeAll();
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    for (JComponent comp : components) {
      frame.add(comp);
    }
    frame.revalidate();
    frame.repaint();
    //frame.setVisible(true);
  }

  public String getAccName() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
    }

    return AccName_String;
  }

  public String getCurrency() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
    }

    return Currency_String;
  }

  public float getLimit() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
    }

    return Limit_Double;
  }

  public boolean isSubmitted() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
    }

    return submitted;
  }

  public boolean isBackWanted() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
    }

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

    Currency = new JLabel("Currency:");
    Currency.setBounds(10 + SHIFT_LEFT, 400, 300, 50);
    components.add(Currency);

    CurrencyText = new JTextField();
    CurrencyText.setBounds(10 + SHIFT_LEFT, 400 + OFFSET_Y, 300, 50);
    components.add(CurrencyText);

    SubmitButton = new JButton("SUBMIT");
    SubmitButton.setBounds(10 + SHIFT_LEFT, 600, 300, 50);
    components.add(SubmitButton);

    SubmitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        AccName_String = AccNameText.getText();
        Currency_String = CurrencyText.getText();
        Limit_Double = Float.valueOf(LimitText.getText());

        if (Limit_Double < 0) {
          //TODO throw Exception or so // check LIMIT
        }
            /*
            System.out.println(
                    "Name: " + AccName_String +
                    "\nCurrency: " + Currency_String +
                    "\nLimit: " + Limit_Double
            );
            */
        submitted = true;
      }
    });

    BackButton = new JButton("BACK");
    BackButton.setBounds(10, 10, 100, 50);
    components.add(BackButton);
    BackButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        backWanted = true;
      }
    });

  }
}