package ui.graphicalRepresentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.swing.*;

import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Transaction;
import transactions.grouping.GroupingBuilder;
import ui.TestUser;
import ui.main.AbstractPage;
import user.User;

import static javax.swing.JOptionPane.DEFAULT_OPTION;

public class GroupingPage extends AbstractPage {

    //ONLY FOR TESTING
    private JFrame TESTING_frame;
    private final boolean TESTING_boolean = true;
    //

    public void setBegin(ZonedDateTime begin) {
        this.begin = begin;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    private ZonedDateTime begin;
    private ZonedDateTime end;
    private  GroupingTypes groupingType;
    User user;
    private JLabel introTextLabel;
    private JButton backButton;
    private JButton showGraphicalButton;
    private JButton selectDateButton;
    private JTable transactionTable;
    private JScrollPane scrollPane;

    private volatile boolean refreshWanted = false;

    String[] options = getNames(GroupingTypes.class);
    int selectedGrouping = 0;
    private String selectedDate ="";
    private String selectedDateEnd ="";

    private volatile boolean backWanted;
    private volatile boolean graphicalWanted;






    String introText = "Grouped by - ";

    public GroupingPage(final User user){
        this.user = user;
        groupingType = GroupingTypes.MONTHLY;
    }

    //ONLY FOR TESTING
    public GroupingPage(User user, JFrame frame){
        this.user = user;
        this.TESTING_frame = frame;
        groupingType = GroupingTypes.MONTHLY;
    }
    //

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
                orga = orga.daily();
                break;
            case MONTHLY:
                orga = orga.monthly();
                break;
            case YEARLY:
                orga = orga.yearly();
                break;
            case USER_DEFINED:
                //this.introText += "User Defined:" + this.begin.toString() + " - " + this.end.toString();
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

                   transactionList_VISU[i][0]=
                           this.user.getCategoryStore().keyOfCategory(it.element().getCategory());

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
        this.introTextLabel.setBounds(100, 50, 500, 50);
        this.components.add(this.introTextLabel);

        this.backButton = new JButton("BACK");
        this.backButton.setBounds(10, 10, 100, 50);
        this.components.add(this.backButton);

        this.backButton.addActionListener(e -> this.backWanted = true);


        this.showGraphicalButton = new JButton("Show Graphical Representation!");
        this.showGraphicalButton.setBounds(400, 700, 300, 50);
        this.components.add(this.showGraphicalButton);


        this.selectDateButton = new JButton("Select Date");
        this.selectDateButton.setBounds(400,100,300,50);
        this.components.add(selectDateButton);

        this.showGraphicalButton.addActionListener(e -> this.graphicalWanted = true);
        this.selectDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                  selectedGrouping = JOptionPane.showOptionDialog(
                          null,
                          "What grouping do you want?",
                          "Grouping-Chooser",
                          DEFAULT_OPTION,
                          JOptionPane.PLAIN_MESSAGE,
                          null,
                          options,
                          options[0]);

                  switch (selectedGrouping){
                      case 0:
                          selectedDate = JOptionPane.showInputDialog("Specify Year, Month and Day for Summary","YYYY/MM/DD");
                          groupingType = GroupingTypes.DAILY;
                          introText = "Grouped by - "+groupingType.toString() +"   "+ selectedDate;
                          break;
                      case 1:
                          selectedDate = JOptionPane.showInputDialog("Specify Year and Month for Summary","YYYY/MM");
                          groupingType = GroupingTypes.MONTHLY;
                          introText = "Grouped by - "+groupingType.toString() +"   "+ selectedDate;
                          break;
                      case 2:
                          selectedDate = JOptionPane.showInputDialog("Specify Year for Summary","YYYY");
                          groupingType = GroupingTypes.YEARLY;
                          introText = "Grouped by - "+groupingType.toString() +"   "+ selectedDate;
                          break;
                      case 3:
                          selectedDate = JOptionPane.showInputDialog("Specify Year, Month and Day Start for Summary","YYYY/MM/DD");
                          setBegin(ZonedDateTime.of(Integer.valueOf(selectedDate.substring(0,3)),Integer.valueOf(selectedDate.substring(5,6)),Integer.valueOf(selectedDate.substring(8,9)),0, 0, 0, 0, ZoneId.of("UTC")));
                          selectedDateEnd = JOptionPane.showInputDialog("Specify Year, Month and Day End for Summary","YYYY/MM/DD");
                          setEnd(ZonedDateTime.of(Integer.valueOf(selectedDateEnd.substring(0,3)),Integer.valueOf(selectedDateEnd.substring(5,6)),Integer.valueOf(selectedDateEnd.substring(8,9)),0, 0, 0, 0, ZoneId.of("UTC")));
                          groupingType = GroupingTypes.USER_DEFINED;
                          introText = "Grouped by - "+groupingType.toString() +"   Start:"+ selectedDate +" End:"+ selectedDateEnd;
                          break;
                  }

                  refreshWanted = true;
                  if(TESTING_boolean && refreshWanted)
                      configureFrame(TESTING_frame);

            }
        });
    }


    public static void main(final String args[]) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        User user = TestUser.getTestUser();
        GroupingPage page = new GroupingPage(user,frame);
        page.configureFrame(frame);
    }

    //https://stackoverflow.com/questions/13783295/getting-all-names-in-an-enum-as-a-string
    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}


