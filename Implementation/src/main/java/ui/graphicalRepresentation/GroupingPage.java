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

    private ZonedDateTime begin = ZonedDateTime.now(ZoneId.of("UTC")).minusHours(1);
    private ZonedDateTime end;
    private  GroupingTypes groupingType = GroupingTypes.MONTHLY;
    User user;
    private JLabel introTextLabel;
    private JButton backButton;
    private JButton showBarGraphicalButton;
    private JButton showPieGraphicalButton;
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

    }

    //ONLY FOR TESTING
    public GroupingPage(User user, JFrame frame){
        this.user = user;
        this.TESTING_frame = frame;
    }
    //

    @Override
    protected void resetTitle(final JFrame frame) {
        frame.setTitle("Grouping - Page");
    }

    @Override
    protected void createComponents() {

        refreshWanted = false;


        this.components = new ArrayList<>();
        GroupingBuilder orga = new GroupingBuilder().allAccs(this.user).category();
        switch (this.groupingType) {
            case DAILY:
                orga = orga.daily().userdefined(begin,begin.plusDays(1));
                break;
            case MONTHLY:
                orga = orga.monthly().userdefined(begin,begin.plusMonths(1));
                break;
            case YEARLY:
                orga = orga.yearly().userdefined(begin,begin.plusYears(1));
                break;
            case USER_DEFINED:
                //this.introText += "User Defined:" + this.begin.toString() + " - " + this.end.toString();
                orga = orga.userdefined(this.begin, this.end);
                break;
        }

        Map<String, CustomContainer<Transaction>> organized = orga.organize();

        String[] transactionDescriptions =
                {"Type", "Descriptions", "Amount", "Creation-Date", "Category"};

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

            this.transactionTable = new JTable(transactionList_VISU, transactionDescriptions);
        }else
            transactionTable = new JTable(new String[0][0],transactionDescriptions);


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


        this.showBarGraphicalButton = new JButton("Show Graphical Bar-Representation!");
        this.showBarGraphicalButton.setBounds(400, 700, 300, 50);
        this.components.add(this.showBarGraphicalButton);

        this.showPieGraphicalButton = new JButton("Show Graphical Pie-Representations!");
        this.showPieGraphicalButton.setBounds(10, 700, 300, 50);
        this.components.add(showPieGraphicalButton);


        this.selectDateButton = new JButton("Select Date");
        this.selectDateButton.setBounds(400,100,300,50);
        this.components.add(selectDateButton);

        this.showBarGraphicalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                GraphicalRepresentation_Bar bar = new GraphicalRepresentation_Bar(user);
                switch (selectedGrouping){
                    case 0:
                        try {
                            bar.draw(selectedDate.substring(0, 4), selectedDate.substring(5, 7), selectedDate.substring(8, 10));
                        }catch (Exception e) {
                            JOptionPane.showMessageDialog(
                                    null, "Invalid Date", "Date Error", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    case 1:
                        try {
                            bar.draw(selectedDate.substring(0, 4), selectedDate.substring(5, 7));
                        }catch (Exception e) {
                            JOptionPane.showMessageDialog(
                                    null, "Invalid Date", "Date Error", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    case 2:
                        try {
                            bar.draw(selectedDate.substring(0, 4));
                        }catch (Exception e) {
                            JOptionPane.showMessageDialog(
                                    null, "Invalid Date", "Date Error", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    case 3:
                        try {
                        bar.draw(begin,end);
                    }catch (Exception e) {
                        JOptionPane.showMessageDialog(
                                null, "Invalid Date", "Amount Error", JOptionPane.WARNING_MESSAGE);
                    }

                }

            }
        });

        this.showPieGraphicalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                GraphicalRepresentation_Pie pie = new GraphicalRepresentation_Pie(user);
                switch (selectedGrouping){
                    case 0:
                        try {
                            pie.draw(selectedDate.substring(0, 4), selectedDate.substring(5, 7), selectedDate.substring(8, 10));
                        }catch (Exception e) {
                            JOptionPane.showMessageDialog(
                                    null, "Invalid Date", "Date Error", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    case 1:
                        try {
                            pie.draw(selectedDate.substring(0, 4), selectedDate.substring(5, 7));
                        }catch (Exception e) {
                            JOptionPane.showMessageDialog(
                                    null, "Invalid Date", "Date Error", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    case 2:
                        try {
                            pie.draw(selectedDate.substring(0, 4));
                        }catch (Exception e) {
                            JOptionPane.showMessageDialog(
                                    null, "Invalid Date", "Date Error", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    case 3:
                        try {
                            pie.draw(begin,end);
                        }catch (Exception e) {
                            JOptionPane.showMessageDialog(
                                    null, "Invalid Date", "Amount Error", JOptionPane.WARNING_MESSAGE);
                        }

                }

            }
        });


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
                          setBegin(ZonedDateTime.of(Integer.valueOf(selectedDate.substring(0,4)),Integer.valueOf(selectedDate.substring(5,7)),Integer.valueOf(selectedDate.substring(8,10)),0, 0, 0, 0, ZoneId.of("UTC")));
                          groupingType = GroupingTypes.DAILY;
                          //TODO CHECK TIME PROPERLY
                          //ZonedDateTime.of(Integer.valueOf(selectedDate.substring(0,4)),Integer.valueOf(selectedDate.substring(5,7)),Integer.valueOf(selectedDate.substring(8,10)),0, 0, 0, 0, ZoneId.of("UTC"));
                          introText = "Grouped by - "+groupingType.toString() +"   "+ selectedDate;
                          break;
                      case 1:
                          selectedDate = JOptionPane.showInputDialog("Specify Year and Month for Summary","YYYY/MM");
                          groupingType = GroupingTypes.MONTHLY;
                          //TODO CHECK TIME PROPERLY
                          setBegin(ZonedDateTime.of(Integer.valueOf(selectedDate.substring(0,4)),Integer.valueOf(selectedDate.substring(5,7)),01,0, 0, 0, 0, ZoneId.of("UTC")));
                          introText = "Grouped by - "+groupingType.toString() +"   "+ selectedDate;
                          break;
                      case 2:
                          selectedDate = JOptionPane.showInputDialog("Specify Year for Summary","YYYY");
                          //TODO CHECK TIME PROPERLY
                          setBegin(ZonedDateTime.of(Integer.valueOf(selectedDate.substring(0,4)),01,01,0, 0, 0, 0, ZoneId.of("UTC")));
                          groupingType = GroupingTypes.YEARLY;
                          introText = "Grouped by - "+groupingType.toString() +"   "+ selectedDate;
                          break;
                      case 3:
                          selectedDate = JOptionPane.showInputDialog("Specify Year, Month and Day Start for Summary","YYYY/MM/DD");
                          //TODO CHECK TIME PROPERLY
                          setBegin(ZonedDateTime.of(Integer.valueOf(selectedDate.substring(0,4)),Integer.valueOf(selectedDate.substring(5,7)),Integer.valueOf(selectedDate.substring(8,10)),0, 0, 0, 0, ZoneId.of("UTC")));
                          selectedDateEnd = JOptionPane.showInputDialog("Specify Year, Month and Day End for Summary","YYYY/MM/DD");
                          setEnd(ZonedDateTime.of(Integer.valueOf(selectedDateEnd.substring(0,4)),Integer.valueOf(selectedDateEnd.substring(5,7)),Integer.valueOf(selectedDateEnd.substring(8,10)),0, 0, 0, 0, ZoneId.of("UTC")));
                          groupingType = GroupingTypes.USER_DEFINED;
                          introText = "Grouped by - "+groupingType.toString() +"   Start:"+ selectedDate +" End:"+ selectedDateEnd;
                          break;
                  }

                  refreshWanted = true;
                  if(TESTING_boolean && refreshWanted) {
                      configureFrame(TESTING_frame);
                      System.out.println("is testing");
                  }
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


