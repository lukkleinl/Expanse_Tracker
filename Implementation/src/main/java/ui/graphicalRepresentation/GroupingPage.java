package ui.graphicalRepresentation;

import ui.TestUser;
import ui.main.AbstractPage;
import user.User;

import javax.swing.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;

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

        switch (groupingType){

            case DAILY:
                introText += "Daily!";
                break;
            case MONTHLY:
                introText += "Monthly!";
                break;
            case YEARLY:
                introText += "Yearly!";
                break;
            case USER_DEFINED:
                introText += "User Defined:" + begin.toString() +" - " +end.toString();
        }


        introTextLabel = new JLabel(introText);
        // 1200 X 800
        introTextLabel.setBounds(400,100,200,50);
        components.add(introTextLabel);

        backButton = new JButton("BACK");
        backButton.setBounds(10,10,100,50);
        components.add(backButton);

        backButton.addActionListener(e -> backWanted = false);


        backButton = new JButton("BACK");
        backButton.setBounds(10,10,100,50);
        components.add(backButton);

        backButton.addActionListener(e -> backWanted = false);


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
