package ui.graphicalRepresentation;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.grouping.GroupingBuilder;
import ui.TestUser;
import ui.main.AbstractPage;
import user.User;

public class GroupingPage extends AbstractPage {


    private ZonedDateTime begin;
    private ZonedDateTime end;
    private final GroupingTypes groupingType;
    User user;
    private JLabel introTextLabel;
    private JButton backButton;
    private JButton showGraphicalButton;
    private JTable transactionTable;
    private JScrollPane scrollPane;

    private volatile boolean backWanted;
    private volatile boolean graphicalWanted;

    String introText = "Grouped by - ";

    public GroupingPage(final User user, final GroupingTypes groupingType) {
        this.groupingType = groupingType;
        this.user = user;
    }

    public GroupingPage(final User user, final GroupingTypes groupingType, final ZonedDateTime begin,
                        final ZonedDateTime end) {
        this.begin = begin;
        this.end = end;
        this.groupingType = groupingType;
        this.user = user;
    }

    public GroupingPage(final User user, final ZonedDateTime begin, final ZonedDateTime end) {
        this.begin = begin;
        this.end = end;
        this.groupingType = GroupingTypes.USER_DEFINED;
        this.user = user;
    }


    @Override
    protected void resetTitle(final JFrame frame) {
        frame.setTitle("Grouping - Page");
    }

    @Override
    protected void createComponents() {
        this.components = new ArrayList<>();
        GroupingBuilder orga = new GroupingBuilder().allAccs(this.user).category();

        switch (this.groupingType) {
            case DAILY:
                this.introText += "Daily!";
                orga = orga.daily();
                break;
            case MONTHLY:
                this.introText += "Monthly!";
                orga = orga.monthly();
                break;
            case YEARLY:
                this.introText += "Yearly!";
                orga = orga.yearly();
                break;
            case USER_DEFINED:
                this.introText += "User Defined:" + this.begin.toString() + " - " + this.end.toString();
                orga = orga.userdefined(this.begin, this.end);
        }

        Map<String, CustomContainer<Transaction>> organized = orga.organize();

        int rows = 0;
        for (String key : organized.keySet()) {
            rows += organized.get(key).size();
        }
        if (rows > 0) {
            String[][] transactionList_VISU = new String[rows][6];
            int i = 0;

            for (String key : organized.keySet()) {
                for (CustomIterator<Transaction> it = organized.get(key).getIterator(); it.hasNext(); it
                        .next()) {

                    if (it.element() instanceof Payout) {
                        transactionList_VISU[i][0] = "Payout";
                    } else if (it.element() instanceof Deposit) {
                        transactionList_VISU[i][0] = "Deposit";
                    }

                    transactionList_VISU[i][1] = it.element().getDescription();
                    transactionList_VISU[i][2] = "" + it.element().getAmount();
                    transactionList_VISU[i][3] = it.element().getFormattedCreationDate();
                    transactionList_VISU[i][4] = it.element().getCategory();
                    transactionList_VISU[i][5] = "" + it.element().getID();
                    i++;
                }
            }

            String[] transactionDescriptions =
                    {"Type", "Descriptions", "Amount", "Creation-Date", "Category"};
            this.transactionTable = new JTable(transactionList_VISU, transactionDescriptions);
        }

        // https://stackoverflow.com/questions/9919230/disable-user-edit-in-jtable
        // MAKES THE ELEMENTS IN THE TABLE UNEDITABLE
        this.transactionTable.setDefaultEditor(Object.class, null);


        this.scrollPane = new JScrollPane(this.transactionTable);
        this.scrollPane.setBounds(225, 200, 900, 450);
        this.components.add(this.scrollPane);

        this.introTextLabel = new JLabel(this.introText);
        // 1200 X 800
        this.introTextLabel.setBounds(400, 100, 200, 50);
        this.components.add(this.introTextLabel);

        this.backButton = new JButton("BACK");
        this.backButton.setBounds(10, 10, 100, 50);
        this.components.add(this.backButton);

        this.backButton.addActionListener(e -> this.backWanted = true);


        this.showGraphicalButton = new JButton("Show Graphical Representation!");
        this.showGraphicalButton.setBounds(400, 700, 300, 50);
        this.components.add(this.showGraphicalButton);

        this.showGraphicalButton.addActionListener(e -> this.graphicalWanted = true);
    }


    public static void main(final String args[]) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        User user = TestUser.getTestUser();
        GroupingPage page = new GroupingPage(user, GroupingTypes.DAILY);
        page.configureFrame(frame);
    }
}


