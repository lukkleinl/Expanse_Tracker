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
 */

public class GraphicalRepresentation_Bar {

  User user;
  CustomContainer<Transaction> listOfTransactions;

  /**
   * Constructor that takes in a user and saves it
   * @param user
   */
  public GraphicalRepresentation_Bar(User user) {
    this.user = user;
  }

  /**
   * draws The Requested Bar Chart for that year sorted yearly
   * @param year
   * @throws Exception
   */
  public void draw(String year) throws Exception {
    draw(getYearly(year), year);
  }

  /**
   * draws The Requested Bar Chart for that year and Month sorted Monthly
   * @param year
   * @param month
   * @throws Exception
   */
  public void draw(String year, String month) throws Exception {
    draw(getMonthly(year, month), year + "/" + month);
  }

  /**
   * draws The Requested Bar Chart for that year and Month and day sorted Daily
   * @param year
   * @throws Exception
   */
  public void draw(String year, String month, String day) throws Exception {
    draw(getDaily(year, month, day), year + "/" + month + "/" + day);
  }

  /**
   * draws The Requested Bar Chart for the 2 user given ZonedDate's
   * @param begin
   * @param end
   * @throws Exception
   */
  public void draw(ZonedDateTime begin, ZonedDateTime end) throws Exception {
    draw(getCustom(begin, end), begin.toString() + " - " + end.toString());
  }

  /**
   * private helpfer functions
   * @param listOfTransactions
   * @param dateOfInterest
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

  // needs Month as '01' .. '02' ..

  /**
   * extracts the needed Tranactions for the Chart, takes it from the user that is given in Constructor
   * @param year
   * @param month
   * @return
   * @throws Exception
   */
  public CustomContainer<Transaction> getMonthly(String year, String month) throws Exception {

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
   * @param year
   * @return
   * @throws Exception
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
   * @param year
   * @param month
   * @param day
   * @return
   * @throws Exception
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
   * @param begin
   * @param end
   * @return
   * @throws Exception
   */
  public CustomContainer<Transaction> getCustom(ZonedDateTime begin, ZonedDateTime end)
      throws Exception {
    Map<String, CustomContainer<Transaction>> orga =
        new GroupingBuilder().allAccs(user).userdefined(begin, end).organize();
    for (String key : orga.keySet()) return orga.get(key);

    throw new NoSuchFieldException("Given Date not present");
  }
}
