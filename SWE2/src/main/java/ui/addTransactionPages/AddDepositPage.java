package ui.addTransactionPages;

import transactions.DepositCategory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import ui.main.InterfacePage;

/**
 * This Page collects all necessary data needed to create a new 'Deposit' Transaction. Implements the Interface 'InterfacePage'
 * By Pressing Submit(JButton) the Page saves the Values entered into the JTextField's, who then can be aquired through getters.
 * @author Paul Kraft
 */

public class AddDepositPage implements InterfacePage {
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
    private Object[] PayoutCategorys = {DepositCategory.SALARY,DepositCategory.DIVIDEND};
    private DepositCategory catego;
    private String description;
    private float Amount;

    private JButton SubmitButton;
    private JButton BackButton;

    private volatile boolean submitted;
    private volatile boolean backWanted;

    /**
     * @return DepositCategory Input from User or Default value.
     */
    public DepositCategory getCatego() {
        return catego;
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
    public AddDepositPage() {
        createComponents();
    }

    /**
     * Configures the JFrame, adds all the components(resets the values of the JTextFields to hard coded default).
     * Names the Frame to 'Add Deposit - Page'.
     * Sets the Size of the Frame. (Default is 1200 by 800).
     * calls revalidate() and repaint() on the Frame.
     * @param frame The JFrame, which components will be updated
     */
    public void configureFrame(JFrame frame) {
        createComponents();

        //frame.setVisible(false);
        frame.setLayout(null);
        frame.setTitle("Add Deposit - Page");
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

        SubmitButton = new JButton("Deposit");
        SubmitButton.setBounds(100, 500, 300, 50);
        components.add(SubmitButton);

        SubmitButton.addActionListener(new ActionListener() {
            @Override
            /**
             * @param e Action event
             * sets Submitted to true
             * extracts the Input Values and provides them for getters.
             */
            public void actionPerformed(ActionEvent actionEvent) {
                description = DescriptionInp.getText();
                Amount = Float.valueOf(AmountInp.getText());
                catego = (DepositCategory) EnumsCategorysBox.getSelectedItem();

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
