package ui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Add_StockAccountPage implements InterfacePage{

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

    private String AccName_String ="";
    private Date BuyDate_Date;
    private double Limit_Double = 0.00;

    private boolean submitted;


    public Add_StockAccountPage() {
        createComponents();
    }
    /* TESTING PURPOSES ONLY */
    public static void main(String args[]){

        JFrame frame = new JFrame();
        Add_StockAccountPage addp = new Add_StockAccountPage();
        addp.configureFrame(frame);

    }
    public void configureFrame(JFrame frame) {
        createComponents();

        frame.setVisible(false);
        frame.setLayout(null);
        frame.setTitle("Add Stock Account");
        frame.getContentPane().removeAll();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        for(JComponent comp: components) {
            frame.add(comp);
        }
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    public String getAccName() {
        try {
            Thread.sleep(1);
        }catch (Exception e){ }

        return AccName_String;
    }

    public Date getBuyDate() {
        try {
            Thread.sleep(1);
        }catch (Exception e){ }

        return BuyDate_Date;
    }

    public double getLimit() {
        try {
            Thread.sleep(1);
        }catch (Exception e){ }

        return Limit_Double;
    }

    public boolean isSubmitted() {
        try {
            Thread.sleep(1);
        } catch (Exception e){ }

        return submitted;
    }

    private void createComponents() {
        components = new ArrayList<>();
        submitted = false;

        IntroText = new JLabel("Please Enter The relevant Data!"+"                    "+"logged in as:"+"<USERNAME>");
        IntroText.setBounds(300,10,800,50);
        components.add(IntroText);

        AccountName = new JLabel("Account Name:");
        AccountName.setBounds(10+SHIFT_LEFT,200,300,50);
        components.add(AccountName);

        AccNameText = new JTextField();
        AccNameText.setBounds(10+SHIFT_LEFT,200+OFFSET_Y,300,50);
        components.add(AccNameText);

        Limit = new JLabel("Limit:");
        Limit.setBounds(10+SHIFT_LEFT,300,300,50);
        components.add(Limit);

        LimitText = new JTextField("0.00");
        LimitText.setBounds(10+SHIFT_LEFT,300+OFFSET_Y,300,50);
        components.add(LimitText);

        BuyDate = new JLabel("BuyDate:");
        BuyDate.setBounds(10+SHIFT_LEFT,400,300,50);
        components.add(BuyDate);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        BuyDateText = new JFormattedTextField(df);
        BuyDateText.setText("dd/mm/yyyy");
        BuyDateText.setBounds(10+SHIFT_LEFT,400+OFFSET_Y,300,50);
        components.add(BuyDateText);

        SubmitButton = new JButton("SUBMIT");
        SubmitButton.setBounds(10+SHIFT_LEFT,600,300,50);
        components.add(SubmitButton);

        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccName_String = AccNameText.getText();
                BuyDate_Date= (Date)BuyDateText.getValue();
                Limit_Double = Double.valueOf(LimitText.getText());

                //TODO check limit and DATE

                System.out.println(
                    "Name: " + AccName_String +
                        "\nBuyDate: " + BuyDate_Date+
                        "\nLimit: " + Limit_Double
                );

                submitted = true;
            }
        });

        BackButton = new JButton("BACK");
        BackButton.setBounds(10,10,100,50);
        components.add(BackButton);
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO GO BACK
            }
        });


    }
}