package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Add_DebitAccountPage implements InterfacePage {

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
  private double Limit_Double = 0.00;

  private boolean submitted;
  private boolean backWanted;

  public Add_DebitAccountPage() {
    createComponents();
  }

  /* TESTING PURPOSES ONLY */
  public static void main(String args[]) {

    JFrame frame = new JFrame();
    Add_DebitAccountPage addp = new Add_DebitAccountPage();
    addp.configureFrame(frame);

  }

  public void configureFrame(JFrame frame) {
    createComponents();

    frame.setVisible(false);
    frame.setLayout(null);
    frame.setTitle("Add Debit Card Account");
    frame.getContentPane().removeAll();
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    for (JComponent comp : components) {
      frame.add(comp);
    }
    frame.revalidate();
    frame.repaint();
    frame.setVisible(true);
  }

  public String getAccName() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
    }

    return AccName_String;
  }

  public String getIBANy() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
    }

    return IBAN_STRING;
  }

  public double getLimit() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
    }

    return Limit_Double;
  }

  public String getBankName_String() {
    try {
      Thread.sleep(1);
    } catch (Exception e) {
    }

    return BankName_String;
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
    SubmitButton.setBounds(10 + SHIFT_LEFT, 700, 300, 50);
    components.add(SubmitButton);

    SubmitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        AccName_String = AccNameText.getText();
        IBAN_STRING = IBANField.getText();
        Limit_Double = Double.valueOf(LimitText.getText());
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
      public void actionPerformed(ActionEvent e) {
        backWanted = true;
      }
    });


  }
}
