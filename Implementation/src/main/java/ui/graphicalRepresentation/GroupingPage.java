package ui.graphicalRepresentation;

import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.grouping.GroupingBuilder;
import ui.TestUser;
import ui.main.AbstractPage;
import user.User;

import javax.swing.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Map;

public class GroupingPage extends AbstractPage {


    private ZonedDateTime begin;
    private ZonedDateTime end;
    private GroupingTypes groupingType;
    User user;
    private JLabel introTextLabel;
    private JButton backButton;
    private JButton showGraphicalButton;
    private JTable transactionTable;
    private JScrollPane scrollPane;

    private volatile boolean backWanted;
    private volatile boolean graphicalWanted;

    String introText = "Grouped by - ";

    public GroupingPage(User user, GroupingTypes groupingType) {
        this.groupingType = groupingType;
        this.user = user;
    }

    public GroupingPage(User user, GroupingTypes groupingType, ZonedDateTime begin, ZonedDateTime end) {
        this.begin = begin;
        this.end = end;
        this.groupingType = groupingType;
        this.user = user;
    }

    public GroupingPage(User user, ZonedDateTime begin, ZonedDateTime end) {
        this.begin = begin;
        this.end = end;
        this.groupingType = GroupingTypes.USER_DEFINED;
        this.user = user;
    }


    @Override
    protected void resetTitle(JFrame frame) {
        frame.setTitle("Grouping - Page");
    }

    @Override
    protected void createComponents() {

        components = new ArrayList<>();

        Map<String, CustomContainer<Transaction>> orga = new GroupingBuilder().allAccs(user).organize();

        switch (groupingType){

            case DAILY:
                introText += "Daily!";
                orga = new GroupingBuilder().allAccs(user).daily()  .organize(); // TODO ? wie groupe ich hier?
                break;
            case MONTHLY:
                introText += "Monthly!";
                orga = new GroupingBuilder().allAccs(user).monthly().organize(); //TODO  ? wie groupe ich hier?
                break;
            case YEARLY:
                introText += "Yearly!";
                orga = new GroupingBuilder().allAccs(user).yearly().organize(); //TODO ? wie groupe ich hier?
                break;
            case USER_DEFINED:
                introText += "User Defined:" + begin.toString() +" - " +end.toString();
                orga = new GroupingBuilder().allAccs(user).userdefined(begin,end).organize(); //TODO ? wie groupe ich hier?

        }


        String[] transactionDescriptions = {"Type", "Descriptions", "Amount", "Creation-Date",
                "Category"};

        for(String key : orga.keySet()) {

            CustomContainer<Transaction> transactionlist = orga.get(key);
            int listSize = transactionlist == null ? 0 : transactionlist.size();
            String[][] transactionList_VISU = new String[listSize][6];

            if (listSize > 0) {
                CustomIterator<Transaction> it = transactionlist.getIterator();
                int i = 0;

                while (it.hasNext()) {
                    Transaction transtemp = it.next();

                    if (transtemp instanceof Payout) {
                        transactionList_VISU[i][0] = "Payout";
                        transactionList_VISU[i][4] = ((Payout) transtemp).getPayoutCategory()
                                .toString();
                    } else {
                        transactionList_VISU[i][0] = "Deposit";
                        transactionList_VISU[i][4] = ((Deposit) transtemp).getCategory()
                                .toString();
                    }

                    transactionList_VISU[i][1] = transtemp.getDescription();
                    transactionList_VISU[i][2] = "" + transtemp.getAmount();
                    transactionList_VISU[i][5] = "" + transtemp.getID();
                    transactionList_VISU[i++][3] = transtemp.getFormattedCreationDate();


                }

            }
            transactionTable = new JTable(transactionList_VISU, transactionDescriptions);

            //https://stackoverflow.com/questions/9919230/disable-user-edit-in-jtable
            // MAKES THE ELEMENTS IN THE TABLE UNEDITABLE
            transactionTable.setDefaultEditor(Object.class, null);


            scrollPane = new JScrollPane(transactionTable);
            scrollPane.setBounds(225, 200, 900, 450);
            components.add(scrollPane);

            introTextLabel = new JLabel(introText);
            // 1200 X 800
            introTextLabel.setBounds(400, 100, 200, 50);
            components.add(introTextLabel);

            backButton = new JButton("BACK");
            backButton.setBounds(10, 10, 100, 50);
            components.add(backButton);

            backButton.addActionListener(e -> backWanted = true);


            showGraphicalButton = new JButton("Show Graphical Representation!");
            showGraphicalButton.setBounds(400, 700, 300, 50);
            components.add(showGraphicalButton);

            showGraphicalButton.addActionListener(e -> graphicalWanted = true);


        }
    }


    public static void main(String args[]){
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        User user = TestUser.getTestUser();
        GroupingPage page = new GroupingPage(user,GroupingTypes.DAILY);
        page.configureFrame(frame);
    }
}
