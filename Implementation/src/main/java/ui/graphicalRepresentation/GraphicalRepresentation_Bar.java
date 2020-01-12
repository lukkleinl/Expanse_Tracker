package ui.graphicalRepresentation;

import iteration.CustomContainer;
import iteration.CustomIterator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.experimental.categories.Categories;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.categories.DepositCategory;
import transactions.categories.PayoutCategory;
import transactions.grouping.GroupingBuilder;
import user.User;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Class to genereate a Bar Chart
 * @author Paul Kraft
 * @author Patrick Gmasz
 */
public class GraphicalRepresentation_Bar {

  User user;
  CustomContainer<Transaction> listOfTransactions;

  /**
   * Constructor that takes in a user and saves it
   * @param user User whose Data are of Interest
   */
  public GraphicalRepresentation_Bar(User user) {
    this.user = user;
  }

  /**
   * draws The Requested Bar Chart for that year sorted yearly
   * @param year The Year whose Data is of interrest for the Grouping algorithm.
   * @throws Exception Exception thrown If the Year is not valid, or there is not enough data to create a Chart.
   */
  public void draw(String year) throws Exception {
    draw(getYearly(year), year);
  }

  /**
   * draws The Requested Bar Chart for that year and Month sorted Monthly
   * @param year The Year, the 'When' for the Grouping algorithm.
   * @param month The Month, the 'When' for the Grouping algorithm.
   * @throws Exception Exception thrown If the Year is not valid, or there is not enough data to create a Chart.
   */
  public void draw(String year, String month) throws Exception {
    draw(getMonthly(year, month), year + "/" + month);
  }

  /**
   * draws The Requested Bar Chart for that year and Month and day sorted Daily
   * @param year The Year, the 'When' for the Grouping algorithm.
   * @param month The Month, the 'When' for the Grouping algorithm
   * @param day The Day, the 'When' for the Grouping algorithm.
   * @throws Exception Exception thrown If the Year is not valid, or there is not enough data to create a Chart.
   */
  public void draw(String year, String month, String day) throws Exception {
    draw(getDaily(year, month, day), year + "/" + month + "/" + day);
  }

  /**
   * draws The Requested Bar Chart for the 2 user given ZonedDate's
   * @param begin the Begin Date for the Grouping algorithm.
   * @param end the End Date for the Grouping algorithm.
   * @throws Exception Exception thrown If the Year is not valid, or there is not enough data to create a Chart.*/
  public void draw(ZonedDateTime begin, ZonedDateTime end) throws Exception {
    draw(getCustom(begin, end), begin.toString() + " - " + end.toString());
  }

  /**
   * private helper function to draw the Chart
   * @param listOfTransactions list of Transactions that should be displayed in the Chart.
   * @param dateOfInterest String that is also Displayed so the End User knows, for which period the Chart is
   */
  private void draw(CustomContainer<Transaction> listOfTransactions, String dateOfInterest) {

    this.listOfTransactions = listOfTransactions;

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    Set<String> payoutCategorys = user.getCategories(new PayoutCategory());

    Map<String, Float> combinedTransactionValuesOfCategorys = new HashMap<>();

    //combines all Payouts
    for (CustomIterator it = listOfTransactions.getIterator(); it.hasNext(); it.next()) {

      if (it.element() instanceof Payout) {

        String category = ((Payout) it.element()).getCategory();

        if (combinedTransactionValuesOfCategorys.containsKey(category)) {
          combinedTransactionValuesOfCategorys.put(
              category,
              combinedTransactionValuesOfCategorys.get(category)
                  + ((Payout) it.element()).getAmount());
        } else
          combinedTransactionValuesOfCategorys.put(category, ((Payout) it.element()).getAmount());

      } else { // Combines all deposits

        String category = ((Deposit) it.element()).getCategory();

        if (combinedTransactionValuesOfCategorys.containsKey(category)) {
          combinedTransactionValuesOfCategorys.put(
              category,
              combinedTransactionValuesOfCategorys.get(category)
                  + ((Deposit) it.element()).getAmount());
        } else
          combinedTransactionValuesOfCategorys.put(category, ((Deposit) it.element()).getAmount());
      }

      //adds it to the dataset
      for (String category : combinedTransactionValuesOfCategorys.keySet()) {
        dataset.addValue(
            combinedTransactionValuesOfCategorys.get(category),
            category,
            (payoutCategorys.contains(category)) ? "Payout" : "Deposit");
      }
    }

    JFreeChart chart =
        ChartFactory.createBarChart(
            "Bar Chart Summary - " + dateOfInterest, "Money", "Payout/Deposit", dataset);

    ChartFrame frame = new ChartFrame("Bar Chart", chart);
    frame.setSize(1000, 500);
    frame.setVisible(true);
  }



  /**
   * extracts the needed Tranactions for the Chart, takes it from the user that is given in Constructor
   *   needs Month as '01' .. '02' .. year as'2016'
   * @param year The Year, the 'When' for the Grouping algorithm.
   * @param month The Month, the 'When' for the Grouping algorithm
   * @return CustomContainer of type Transaction that contains the Requested Transactions in the given Time period.
   * @throws Exception Exception thrown If the Year is not valid, or there is not enough data to create a Chart.
   */
  private CustomContainer<Transaction> getMonthly(String year, String month) throws Exception {

    if (Integer.valueOf(year) <= 0) throw new IndexOutOfBoundsException("No Banks before Jesus!");

    if (Integer.valueOf(month) > 12)
      throw new IndexOutOfBoundsException("That is not a valid Month!");

    Map<String, CustomContainer<Transaction>> orga =
        new GroupingBuilder().allAccs(user).monthly().organize();

    for (String key : orga.keySet()) {
      if (key.contains("" + year) && key.contains(month + "M")) return orga.get(key);
    }

    throw new NoSuchFieldException("Given Date not present");
  }
  /**
   * extracts the needed Tranactions for the Chart, takes it from the user that is given in Constructor
   * needs year as '2016'
   * @param year The Year, the 'When' for the Grouping algorithm.
   * @return CustomContainer of type Transaction that contains the Requested Transactions in the given Time period.
   * @throws Exception Exception thrown If the Year is not valid, or there is not enough data to create a Chart.
   */
  public CustomContainer<Transaction> getYearly(String year) throws Exception {

    if (Integer.valueOf(year) <= 0) throw new IndexOutOfBoundsException("No Banks before Jesus!");

    Map<String, CustomContainer<Transaction>> orga =
        new GroupingBuilder().allAccs(user).yearly().organize();

    for (String key : orga.keySet()) {
      if (key.contains("Y" + year)) return orga.get(key);
    }

    throw new NoSuchFieldException("Given Date not present");
  }
  /**
   * extracts the needed Tranactions for the Chart, takes it from the user that is given in Constructor
   * needs year as '2015' day as '01' month as'01'
   * @param year The Year, the 'When' for the Grouping algorithm.
   * @param month The Month, the 'When' for the Grouping algorithm
   * @param day The Day, the 'When' for the Grouping algorithm.
   * @return CustomContainer of type Transaction that contains the Requested Transactions in the given Time period.
   * @throws Exception Exception thrown If the Year is not valid, or there is not enough data to create a Chart.
   */

  public CustomContainer<Transaction> getDaily(String year, String month, String day)
      throws Exception {

    if (Integer.valueOf(year) <= 0) throw new IndexOutOfBoundsException("No Banks before Jesus!");

    if (Integer.valueOf(month) > 12)
      throw new IndexOutOfBoundsException("That is not a valid Month!");

    Map<String, CustomContainer<Transaction>> orga =
        new GroupingBuilder().allAccs(user).daily().organize();

    for (String key : orga.keySet()) {
      if (key.contains("D" + year + "-" + month + "-" + day + "D_")) return orga.get(key);
    }

    throw new NoSuchFieldException("Given Date not present");
  }

  /**
   * extracts the needed Tranactions for the Chart, takes it from the user that is given in Constructor
   * @param begin ZonedDateTime begin, begin of the Time period which should be grouped.
   * @param end ZonedDateTime end, end of the Time period which should be grouped.
   * @return CustomContainer of type Transaction that contains the Requested Transactions in the given Time period.
   * @throws Exception Exception thrown If the Year is not valid, or there is not enough data to create a Chart.
   */
  public CustomContainer<Transaction> getCustom(ZonedDateTime begin, ZonedDateTime end)
      throws Exception {
    Map<String, CustomContainer<Transaction>> orga =
        new GroupingBuilder().allAccs(user).userdefined(begin, end).organize();
    for (String key : orga.keySet()) return orga.get(key);

    throw new NoSuchFieldException("Given Date not present");
  }
}
