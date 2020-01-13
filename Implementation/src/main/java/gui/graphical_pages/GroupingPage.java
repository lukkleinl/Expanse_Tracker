package gui.graphical_pages;

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
import transactions.Transaction;
import transactions.grouping.GroupingBuilder;
import gui.main.AbstractPage;
import user.User;

import static javax.swing.JOptionPane.DEFAULT_OPTION;


/** This Page groups the Transactions by a user given Time slot, its
 * also capable of opening Graphical Representations in the form of either, Bar or Pie Charts.
 * @author Paul Kraft
 * @author Patrick Gmasz
 */
public class GroupingPage extends AbstractPage {
  private ZonedDateTime begin = ZonedDateTime.now(ZoneId.of("UTC")).minusYears(10);   //begin date for the grouping algorithm
  private ZonedDateTime end = ZonedDateTime.now(ZoneId.of("UTC")).plusYears(10);                   //end  date for the grouping algorithm
  private GroupingTypes groupingType = GroupingTypes.USER_DEFINED;          //Types of grouping that are supported
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
  int selectedGrouping = 2;
  private String selectedDate ="2010/01/01";
  private String selectedDateEnd="2030/01/01";
  private String groupedByMessage = "";
  // private volatile boolean graphicalWanted;


    /**
     * User needed for displaying, user releveant information
     * also the Transactions and the Sorting Objects are aquired from this.
     * @param user The User whose Data should be displayed.
     */
  public GroupingPage(final User user) {
    this.user = user;
  }


    /**
     * The page has a button, which the user can press if he wants to Go 1 Page back. Pressing the
     * button will set a boolean flag, that the button was pressed. This method returns the boolean,
     * it will be true, if the user wants to go back.
     *
     * @return The boolean flag, it will be true, if the user pressed the 'Back' button.
     */
  public boolean isBackWanted() {
    return this.backWanted;
  }

    /**
     *  This Page can have the need to Refresh to Signal this to the User Interface there is a booleag Flag.
     *  The User Interface will regularly check if the Page wants to refresh.
     *
     * @return The boolean flag, it will be true, if the The Page needs to Refresh
     */
  public boolean isRefreshWanted() {
    return this.refreshWanted;
  }


    /**
     * setter for the beginDate that is used for grouping(the FROM in grouping )
     * @param begin the ZonedDateTime from which the time period starts
     */
    public void setBegin(ZonedDateTime begin) {
        this.begin = begin;
    }

    /**
     *   setter for the endDate that is used for grouping (the TO in grouping)
     * @param end the ZonedDateTime until the time period goes
     */
    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }


    /**
     * function to set the Frame Title(only called from within the UserInterface)
     * takes in a frame and sets its title to 'Grouping- Page'
     * @param frame The Frame whose Title should be reset
     */
    @Override
  protected void resetTitle(final JFrame frame) {
    frame.setTitle("Grouping - Page");
  }

  /**
   * creates all the Components that the JFrame should display(incl. Position,actionlisteners for Buttons, text etc)
   * also resets the booleans for backwanted, refreshwanted
   * is called from within the configureFrame in abstract Page.
   */
  @Override
  protected void createComponents() {

    refreshWanted = false;
    backWanted = false;
    // graphicalWanted = false;

    this.components = new ArrayList<>();

    // gets the Data from the User to Display the Transactions by time
    GroupingBuilder orga = new GroupingBuilder().allAccs(this.user).category();
    switch (this.groupingType) { //checks which kind of grouping is wanted
      case DAILY:
        orga = orga.daily().userdefined(begin, begin.plusDays(1)); // this day until 1 day later
        break;
      case MONTHLY:
        orga = orga.monthly().userdefined(begin, begin.plusMonths(1)); // this month until 1 month later
        break;
      case YEARLY:
        orga = orga.yearly().userdefined(begin, begin.plusYears(1)); // this year until 1 year later
        break;
      case USER_DEFINED:                                             // user defined from - to
        orga = orga.userdefined(this.begin, this.end);
        break;
    }

    Map<String, CustomContainer<Transaction>> organized = orga.organize();

    String[] transactionDescriptions = { // the Header bar the JTable, so its clear what is what in loaded Page.(the very first row))
            "Type", "Descriptions", "Amount", "Creation-Date", "Category"
    };


    //iterates over the transactions and saves it in a String[][] array in a easy to Visualize format
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

      this.transactionTable = new JTable(transactionList_VISU, transactionDescriptions);  // sets the actual JTable
    } else {
      transactionTable = new JTable(new String[0][0], transactionDescriptions);
    }

    DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();       // optic
    tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
    transactionTable.setRowHeight(70);
    transactionTable.setFont(TEXTFIELD_FONT);
    for (int i = 0; i < 5; ++i) {
      transactionTable.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
    }

    this.transactionTable.setDefaultEditor(Object.class, null);

    // components that are needed:
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
    backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    backButton.setBorder(new LineBorder(Color.BLACK, 2));
    this.components.add(this.backButton);

    this.backButton.addActionListener(e -> this.backWanted = true);

    this.showBarGraphicalButton = new JButton("SHOW BAR CHART");
    showBarGraphicalButton.setFont(BUTTON_FONT);
    showBarGraphicalButton.setBorder(new LineBorder(Color.BLACK, 2));
    showBarGraphicalButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    this.showBarGraphicalButton.setBounds(150, 650, 300, 50);

    this.components.add(this.showBarGraphicalButton);

    this.showPieGraphicalButton = new JButton("SHOW PIE CHART");
    showPieGraphicalButton.setFont(BUTTON_FONT);
    showPieGraphicalButton.setBorder(new LineBorder(Color.BLACK, 2));
    showPieGraphicalButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    this.showPieGraphicalButton.setBounds(750, 650, 300, 50);
    this.components.add(showPieGraphicalButton);

    this.selectDateButton = new JButton("SELECT DATE");
    this.selectDateButton.setBounds(850, 50, 200, 50);
    selectDateButton.setFont(BUTTON_FONT);
    selectDateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    selectDateButton.setBorder(new LineBorder(Color.BLACK, 2));
    this.components.add(selectDateButton);

    this.showBarGraphicalButton.addActionListener(        // actionlistener to display Bar chart, depending on grouping will produce different chart
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

    this.showPieGraphicalButton.addActionListener(  // actionlistener to display Pie chart, depending on grouping will produce different chart
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

    this.selectDateButton.addActionListener(    // actionlistener to select Groupingtype and date
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
      switch (selectedGrouping) {    // int that represents the type of Grouping, 0 day, 1 to Month, 2 to Year, 3 user defined.
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
          // ZonedDateTime.of(Integer.valueOf(selectedDate.substring(0,4)),Integer.valueOf(selectedDate.substring(5,7)),Integer.valueOf(selectedDate.substring(8,10)),0, 0, 0, 0, ZoneId.of("UTC"));
          groupedByMessage = groupingType.toString() + "- " + selectedDate;
          break;
        case 1:
          selectedDate =
                  JOptionPane.showInputDialog("Specify Year and Month for Summary", "YYYY/MM");
          groupingType = GroupingTypes.MONTHLY;
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
          }
        });
  }


  // https://stackoverflow.com/questions/13783295/getting-all-names-in-an-enum-as-a-string
    /**
     * Taken from  // https://stackoverflow.com/questions/13783295/getting-all-names-in-an-enum-as-a-string
     * Helper functon to convert Enum class to string array.
     * @param e Enum The EnumClass which should be converted.
     */
  public static String[] getNames(Class<? extends Enum<?>> e) {
    return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
  }
}
