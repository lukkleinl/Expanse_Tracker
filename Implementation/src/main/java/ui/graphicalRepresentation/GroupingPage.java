package ui.graphicalRepresentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import iteration.CustomContainer;
import iteration.CustomIterator;
import org.junit.jupiter.api.TestInstance;
import transactions.Transaction;
import transactions.grouping.GroupingBuilder;
import ui.TestUser;
import ui.main.AbstractPage;
import user.User;

import static javax.swing.JOptionPane.DEFAULT_OPTION;

public class GroupingPage extends AbstractPage {

  // ONLY FOR TESTING
  private JFrame TESTING_frame;
  private final boolean TESTING_boolean = false;
  //

  private ZonedDateTime begin = ZonedDateTime.now(ZoneId.of("UTC")).minusHours(1);
  private ZonedDateTime end = ZonedDateTime.now(ZoneId.of("UTC"));
  private GroupingTypes groupingType = GroupingTypes.MONTHLY;
  private User user;
  private JLabel groupedByHeader;
  private JLabel groupedByLabel;
  private JLabel introTextLabel;
  private JButton backButton;
  private JButton showBarGraphicalButton;
  private JButton showPieGraphicalButton;
  private JButton selectDateButton;
  private JTable transactionTable;
  private JScrollPane scrollPane;

  private volatile boolean refreshWanted = false;
  private volatile boolean backWanted = false;


    String[] options = getNames(GroupingTypes.class);
  int selectedGrouping = 0;
  private String selectedDate = "";
  private String selectedDateEnd = "";
  private String groupedByMessage = "";
  // private volatile boolean graphicalWanted;

  public GroupingPage(final User user) {
    this.user = user;
  }

    /**
     *DO NOT USE!!! Testing Consctructor that besides the User also takes a JFrame,
     * the code will auto refresh on that Jframe  if the testing boolean is true
     * @param user
     * @param frame
     */
  public GroupingPage(User user, JFrame frame) {
  if(!TESTING_boolean)
      throw new RuntimeException("DO NOT USE THE TEST CONSCTRUCTOR! in GroupingPage");

    this.user = user;
    this.TESTING_frame = frame;
  }
  //

    /**
     * Getter for the boolean if Back is wanted(Signals to User interface if back button was pressed!)
     * @return boolean
     */
  public boolean isBackWanted() {
    return this.backWanted;
  }

    /**
     * Getter for the boolean if refresh is wanted(Signals to the User Interface if Refresh is needed! e.g change of data)
     * @return boolean
     */
  public boolean isRefreshWanted() {
    return this.refreshWanted;
  }


    /**
     * setter for the beginDate that is used for grouping(the FROM in grouping )
     * @param begin the ZonedDateTime
     */
    public void setBegin(ZonedDateTime begin) {
        this.begin = begin;
    }

    /**
     *   setter for the endDate that is used for grouping (the TO in grouping)
     * @param end the ZonedDateTime
     */
    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }


    @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("Grouping - Page");
  }

  @Override
  protected void createComponents() {

    refreshWanted = false;
    backWanted = false;
    // graphicalWanted = false;

    this.components = new ArrayList<>();
    GroupingBuilder orga = new GroupingBuilder().allAccs(this.user).category();
    switch (this.groupingType) {
      case DAILY:
        orga = orga.daily().userdefined(begin, begin.plusDays(1));
        break;
      case MONTHLY:
        orga = orga.monthly().userdefined(begin, begin.plusMonths(1));
        break;
      case YEARLY:
        orga = orga.yearly().userdefined(begin, begin.plusYears(1));
        break;
      case USER_DEFINED:
        // this.introText += "User Defined:" + this.begin.toString() + " - " + this.end.toString();
        orga = orga.userdefined(this.begin, this.end);
        break;
    }

    Map<String, CustomContainer<Transaction>> organized = orga.organize();

    String[] transactionDescriptions = {
      "Type", "Descriptions", "Amount", "Creation-Date", "Category"
    };

    int rows = 0;
    for (String key : organized.keySet()) {
      rows += organized.get(key).size();
    }
    if (rows > 0) {
      String[][] transactionList_VISU = new String[rows][6];
      int i = 0;

      for (String key : organized.keySet()) {
        for (CustomIterator<Transaction> it = organized.get(key).getIterator();
            it.hasNext();
            it.next()) {

          transactionList_VISU[i][0] =
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
    } else {
      transactionTable = new JTable(new String[0][0], transactionDescriptions);
    }

    DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
    tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
    transactionTable.setRowHeight(70);
    transactionTable.setFont(TEXTFIELD_FONT);
    for (int i = 0; i < 5; ++i) {
      transactionTable.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
    }

    this.transactionTable.setDefaultEditor(Object.class, null);

    this.scrollPane = new JScrollPane(this.transactionTable);
    scrollPane.setBounds(150, 150, 900, 450);
    this.components.add(this.scrollPane);

    introTextLabel =
        new JLabel("Currently logged in as: " + user.getFirstname() + " " + user.getLastname());
    introTextLabel.setBounds(225, 10, 1000, 50);
    introTextLabel.setFont(HEADER_FONT);
    components.add(introTextLabel);

    this.groupedByHeader = new JLabel("Grouped by:");
    this.groupedByHeader.setBounds(150, 70, 500, 50);
    groupedByHeader.setFont(new Font("Serif", Font.BOLD, 19));
    this.components.add(this.groupedByHeader);

    groupedByLabel = new JLabel(groupedByMessage);
    groupedByLabel.setBounds(150, 100, 500, 50);
    groupedByLabel.setFont(new Font("Serif", Font.PLAIN, 18));
    components.add(groupedByLabel);

    this.backButton = new JButton("BACK");
    this.backButton.setBounds(10, 10, 100, 50);
    backButton.setFont(BUTTON_FONT);
    backButton.setBorder(new LineBorder(Color.BLACK, 2));
    this.components.add(this.backButton);

    this.backButton.addActionListener(e -> this.backWanted = true);

    this.showBarGraphicalButton = new JButton("SHOW BAR CHART");
    showBarGraphicalButton.setFont(BUTTON_FONT);
    showBarGraphicalButton.setBorder(new LineBorder(Color.BLACK, 2));
    this.showBarGraphicalButton.setBounds(150, 650, 300, 50);

    this.components.add(this.showBarGraphicalButton);

    this.showPieGraphicalButton = new JButton("SHOW PIE CHART");
    showPieGraphicalButton.setFont(BUTTON_FONT);
    showPieGraphicalButton.setBorder(new LineBorder(Color.BLACK, 2));
    this.showPieGraphicalButton.setBounds(750, 650, 300, 50);
    this.components.add(showPieGraphicalButton);

    this.selectDateButton = new JButton("SELECT DATE");
    this.selectDateButton.setBounds(850, 50, 200, 50);
    selectDateButton.setFont(BUTTON_FONT);
    selectDateButton.setBorder(new LineBorder(Color.BLACK, 2));
    this.components.add(selectDateButton);

    this.showBarGraphicalButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ev) {
            GraphicalRepresentation_Bar bar = new GraphicalRepresentation_Bar(user);
            switch (selectedGrouping) {
              case 0:
                try {
                  bar.draw(
                      selectedDate.substring(0, 4),
                      selectedDate.substring(5, 7),
                      selectedDate.substring(8, 10));
                } catch (Exception e) {
                  JOptionPane.showMessageDialog(
                      null, "Invalid Date", "Date Error", JOptionPane.WARNING_MESSAGE);
                }
                break;
              case 1:
                try {
                  bar.draw(selectedDate.substring(0, 4), selectedDate.substring(5, 7));
                } catch (Exception e) {
                  JOptionPane.showMessageDialog(
                      null, "Invalid Date", "Date Error", JOptionPane.WARNING_MESSAGE);
                }
                break;
              case 2:
                try {
                  bar.draw(selectedDate.substring(0, 4));
                } catch (Exception e) {
                  JOptionPane.showMessageDialog(
                      null, "Invalid Date", "Date Error", JOptionPane.WARNING_MESSAGE);
                }
                break;
              case 3:
                try {
                  bar.draw(begin, end);
                } catch (Exception e) {
                  JOptionPane.showMessageDialog(
                      null, "Invalid Date", "Amount Error", JOptionPane.WARNING_MESSAGE);
                }
            }
          }
        });

    this.showPieGraphicalButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ev) {
            GraphicalRepresentation_Pie pie = new GraphicalRepresentation_Pie(user);
            switch (selectedGrouping) {
              case 0:
                try {
                  pie.draw(
                      selectedDate.substring(0, 4),
                      selectedDate.substring(5, 7),
                      selectedDate.substring(8, 10));
                } catch (Exception e) {
                  JOptionPane.showMessageDialog(
                      null, "Invalid Date", "Date Error", JOptionPane.WARNING_MESSAGE);
                }
                break;
              case 1:
                try {
                  pie.draw(selectedDate.substring(0, 4), selectedDate.substring(5, 7));
                } catch (Exception e) {
                  JOptionPane.showMessageDialog(
                      null, "Invalid Date", "Date Error", JOptionPane.WARNING_MESSAGE);
                }
                break;
              case 2:
                try {
                  pie.draw(selectedDate.substring(0, 4));
                } catch (Exception e) {
                  JOptionPane.showMessageDialog(
                      null, "Invalid Date", "Date Error", JOptionPane.WARNING_MESSAGE);
                }
                break;
              case 3:
                try {
                  pie.draw(begin, end);
                } catch (Exception e) {
                  JOptionPane.showMessageDialog(
                      null, "Invalid Date", "Amount Error", JOptionPane.WARNING_MESSAGE);
                }
            }
          }
        });

    this.selectDateButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {

            selectedGrouping =
                JOptionPane.showOptionDialog(
                    null,
                    "What grouping do you want?",
                    "Grouping-Chooser",
                    DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);


    try {
      switch (selectedGrouping) {
        case 0:
          selectedDate =
                  JOptionPane.showInputDialog(
                          "Specify Year, Month and Day for Summary", "YYYY/MM/DD");
          setBegin(
                  ZonedDateTime.of(
                          Integer.valueOf(selectedDate.substring(0, 4)),
                          Integer.valueOf(selectedDate.substring(5, 7)),
                          Integer.valueOf(selectedDate.substring(8, 10)),
                          0,
                          0,
                          0,
                          0,
                          ZoneId.of("UTC")));
          groupingType = GroupingTypes.DAILY;
          // TODO CHECK TIME PROPERLY
          // ZonedDateTime.of(Integer.valueOf(selectedDate.substring(0,4)),Integer.valueOf(selectedDate.substring(5,7)),Integer.valueOf(selectedDate.substring(8,10)),0, 0, 0, 0, ZoneId.of("UTC"));
          groupedByMessage = groupingType.toString() + "- " + selectedDate;
          break;
        case 1:
          selectedDate =
                  JOptionPane.showInputDialog("Specify Year and Month for Summary", "YYYY/MM");
          groupingType = GroupingTypes.MONTHLY;
          // TODO CHECK TIME PROPERLY
          setBegin(
                  ZonedDateTime.of(
                          Integer.valueOf(selectedDate.substring(0, 4)),
                          Integer.valueOf(selectedDate.substring(5, 7)),
                          01,
                          0,
                          0,
                          0,
                          0,
                          ZoneId.of("UTC")));
          groupedByMessage = groupingType.toString() + "- " + selectedDate;
          break;
        case 2:
          selectedDate = JOptionPane.showInputDialog("Specify Year for Summary", "YYYY");
          // TODO CHECK TIME PROPERLY
          setBegin(
                  ZonedDateTime.of(
                          Integer.valueOf(selectedDate.substring(0, 4)),
                          01,
                          01,
                          0,
                          0,
                          0,
                          0,
                          ZoneId.of("UTC")));
          groupingType = GroupingTypes.YEARLY;
          groupedByMessage = groupingType.toString() + "- " + selectedDate;
          break;
        case 3:
          selectedDate =
                  JOptionPane.showInputDialog(
                          "Specify Year, Month and Day Start for Summary", "YYYY/MM/DD");
          // TODO CHECK TIME PROPERLY
          setBegin(
                  ZonedDateTime.of(
                          Integer.valueOf(selectedDate.substring(0, 4)),
                          Integer.valueOf(selectedDate.substring(5, 7)),
                          Integer.valueOf(selectedDate.substring(8, 10)),
                          0,
                          0,
                          0,
                          0,
                          ZoneId.of("UTC")));
          selectedDateEnd =
                  JOptionPane.showInputDialog(
                          "Specify Year, Month and Day End for Summary", "YYYY/MM/DD");
          setEnd(
                  ZonedDateTime.of(
                          Integer.valueOf(selectedDateEnd.substring(0, 4)),
                          Integer.valueOf(selectedDateEnd.substring(5, 7)),
                          Integer.valueOf(selectedDateEnd.substring(8, 10)),
                          0,
                          0,
                          0,
                          0,
                          ZoneId.of("UTC")));
          groupingType = GroupingTypes.USER_DEFINED;
          groupedByMessage =
                  groupingType.toString()
                          + "- Start: "
                          + selectedDate
                          + " End: "
                          + selectedDateEnd;
          break;
      }
    }catch (Exception exc){

      JOptionPane.showMessageDialog(
              null,
              "Not able to select a date",
              "Description Error",
              JOptionPane.WARNING_MESSAGE);

              setBegin(ZonedDateTime.now(ZoneId.of("UTC")).minusHours(1));
              setEnd(ZonedDateTime.now(ZoneId.of("UTC")));

    }
            refreshWanted = true;
            if (TESTING_boolean && refreshWanted) {
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
    GroupingPage page = new GroupingPage(user, frame);
    page.configureFrame(frame);
  }

  // https://stackoverflow.com/questions/13783295/getting-all-names-in-an-enum-as-a-string
  public static String[] getNames(Class<? extends Enum<?>> e) {
    return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
  }
}
