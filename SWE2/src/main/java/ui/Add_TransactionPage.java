package ui;

import accounts.*;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.*;
import user.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class Add_TransactionPage implements InterfacePage{

    public final static int FRAME_WIDTH = 1200;
    public final static int FRAME_HEIGHT = 800;

    public final static int SHIFT_LEFT = 0;
    public final static int OFFSET_Y = 50; // THE AMT OF PIXELS THE TEXT FIELDS ARE OFFSET TO THEIR CORRESPONDING JPANELS!

    private ArrayList<JComponent> components;
    private JLabel IntroText;
    private String IntroTextMessage;

    private JLabel AccountName;
    private JLabel Balance;
    private JLabel Limit;

    private JLabel CustomLabel2;
    private JLabel CustomLabel3;
    private JButton BackButton;

    private JTable transactionTable;
    private JScrollPane scrollPane;



    public Add_TransactionPage(Account acc,User user) {


        CustomList<Transaction> transactionlist = user.getTransactions().get(acc.getAccount_number());
        CustomIterator<Transaction> it = transactionlist.getIterator();

        components = new ArrayList<>();

        String[] TransactionDescription = {"Type","Descriptions","Amount","Creation-Date","Category"};
        String[][] TransactionList_VISU = new String[transactionlist.size()][6];

        int i=0;

        while(it.hasNext()){
            Transaction transtemp = it.next();

            if(transtemp instanceof Payout){
                TransactionList_VISU[i][0] = "Payout";
                TransactionList_VISU[i][4] = ((Payout)transtemp).getPayoutCategory().toString();
            }
            else {
                TransactionList_VISU[i][0] = "Deposit";
                TransactionList_VISU[i][4] = ((Deposit)transtemp).getDepositCategory().toString();
            }


            TransactionList_VISU[i][1] = transtemp.getDescription();
            TransactionList_VISU[i][2] = ""+transtemp.getAmount();
            TransactionList_VISU[i++][3] = transtemp.getCreationDate().toString();


        }



        transactionTable = new JTable(TransactionList_VISU,TransactionDescription);

        scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBounds(300,200,500,500);
        components.add(scrollPane);


        IntroTextMessage="Account-Type: ";


        IntroText = new JLabel();
        IntroText.setBounds(300,10,800,50);

        AccountName = new JLabel("Account Name: " + acc.getName());
        AccountName.setBounds(10+SHIFT_LEFT,200,300,50);
        components.add(AccountName);

        Balance = new JLabel("Balance: " + acc.getBalance());
        Balance.setBounds(10+SHIFT_LEFT,250,300,50);
        components.add(Balance);

        Limit = new JLabel("Limit: " + acc.getLimit());
        Limit.setBounds(10+SHIFT_LEFT,300,300,50);
        components.add(Limit);

        CustomLabel2 = new JLabel();
        CustomLabel2.setBounds(10+SHIFT_LEFT,350,300,50);

        CustomLabel3 = new JLabel();
        CustomLabel3.setBounds(10+SHIFT_LEFT,400,300,50);

        if( acc instanceof BankAccount){
            IntroTextMessage+="BankAccount ";
            CustomLabel2.setText("Bankname: "+ ((BankAccount) acc).getBankName());
            CustomLabel3.setText("");
        }
        else if(acc instanceof Cash){
            IntroTextMessage+="CashAccount ";
            CustomLabel2.setText("Currency: "+((Cash) acc).getCurrency());
            CustomLabel3.setText("");
        }
        else if(acc instanceof CreditCard){
            IntroTextMessage+="CreditCardAccount ";
            CustomLabel2.setText("Expiry-Date: "+((CreditCard)acc).getExpiryDate());
            CustomLabel3.setText("");
        }
        else if(acc instanceof DebitCard){
            IntroTextMessage+="DebitCardAccount ";
            CustomLabel2.setText("IBAN: "+((DebitCard)acc).getIBAN());
            CustomLabel3.setText("");
        }

        IntroTextMessage+="  logged in as: "+user.getFirstname()+" "+user.getLastname();

        components.add(CustomLabel2);
        components.add(CustomLabel3);
        IntroText.setText(IntroTextMessage);
        components.add(IntroText);



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
    /* TESTING PURPOSES ONLY */
    public static void main(String args[]){

        //TEST DATA

        BankAccount testbankacc = new BankAccount("pauls acc","BAWAG",1000);
        User testUser = new User(1,"paul","kraft","qwerty");


        for(int i=0;i<100;i++){
            if(i<50) {
                try {
                    testUser.deposit(DepositCategory.SALARY, i % 3 + 1000, "desc" + i, testbankacc);
                } catch (Exception e) {
                }
            }else {
                try {
                    testUser.payOut(PayoutCategory.FOOD, i % 3, "desc" + i, testbankacc);
                } catch (Exception e) {
                }
            }
        }
        //TEST DATA

        JFrame frame = new JFrame();
        Add_TransactionPage addp = new Add_TransactionPage(testbankacc,testUser);
        addp.configureFrame(frame);

    }
    public void configureFrame(JFrame frame) {
        frame.setVisible(false);
        frame.setLayout(null);
        frame.setTitle("Add Cash Account");
        frame.getContentPane().removeAll();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        for(JComponent comp: components) {
            frame.add(comp);
        }
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }
}