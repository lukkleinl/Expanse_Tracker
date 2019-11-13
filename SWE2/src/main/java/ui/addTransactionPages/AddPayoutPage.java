package ui.addTransactionPages;

import transactions.PayoutCategory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import ui.InterfacePage;

public class AddPayoutPage implements InterfacePage {
    public final static int FRAME_WIDTH = 1200;
    public final static int FRAME_HEIGHT = 800;


    private ArrayList<JComponent> components;

    private JLabel IntroText;
    private JLabel Amounttext;
    private JTextField AmountInp;
    private JLabel descriptiontext;
    private JTextField DescriptionInp;
    private JLabel Categorytext;
    private JComboBox EnumsCategorysBox;
    private Object[] PayoutCategorys = {PayoutCategory.EDUCATION,PayoutCategory.FOOD,PayoutCategory.TRANSPORTATION};

    public PayoutCategory getCatego() {
        try {
            Thread.sleep(1);
        } catch (Exception e) {
        }
        return catego;
    }

    public String getDescription() {
        try {
            Thread.sleep(1);
        } catch (Exception e) {
        }
        return description;
    }

    public float getAmount() {
        try {
            Thread.sleep(1);
        } catch (Exception e) {
        }
        return Amount;
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

    private PayoutCategory catego;
    private String description;
    private float Amount;

    private JButton SubmitButton;
    private JButton BackButton;

    private boolean submitted;
    private boolean backWanted;



    public AddPayoutPage() {
        createComponents();
    }


    public void configureFrame(JFrame frame) {
        createComponents();

        //frame.setVisible(false);
        frame.setLayout(null);
        frame.setTitle("Add Payout - Page");
        frame.getContentPane().removeAll();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        for (JComponent comp : components) {
            frame.add(comp);
        }
        frame.revalidate();
        frame.repaint();
        //frame.setVisible(true);
    }

    private void createComponents() {

        components = new ArrayList<>();
        submitted = false;
        backWanted = false;

        Categorytext = new JLabel("Payout Category:");
        Categorytext.setBounds(100, 50, 300, 50);
        components.add(Categorytext);

        EnumsCategorysBox = new JComboBox(PayoutCategorys);
        EnumsCategorysBox.setBounds(100, 100, 300, 50);
        components.add(EnumsCategorysBox);

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

        SubmitButton = new JButton("Payout");
        SubmitButton.setBounds(100, 500, 300, 50);
        components.add(SubmitButton);

        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                description = DescriptionInp.getText();
                Amount = Float.valueOf(AmountInp.getText());
                catego = (PayoutCategory) EnumsCategorysBox.getSelectedItem();

                //TODO negative AMOUNT
                //TODO strings are Empty

                System.out.println("Description: " + description +
                        "\nAmount: " + Amount +
                        "\nCategory: " + catego.toString()
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


    public static void main(String args[]){
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        AddPayoutPage page = new AddPayoutPage();
        page.configureFrame(frame);

    }

}
