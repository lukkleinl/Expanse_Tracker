package ui.graphicalRepresentation;

import iteration.CustomContainer;
import iteration.CustomIterator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.categories.PayoutCategory;
import transactions.grouping.GroupingBuilder;
import user.User;

import javax.swing.*;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class to Generate a Pie Chart
 * @author Paul Kraft
 */
public class GraphicalRepresentation_Pie {

  User user;
  CustomContainer<Transaction> listOfTransactions;

  /**
   * Constructor that takes in a user and saves it
   * @param user
   */

  public GraphicalRepresentation_Pie(User user) {
    this.user = user;
  }


  /**
   * draws The Requested Bar Chart for that year and Month sorted Monthly
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
   * @param month
   * @param day
   * @throws Exception
   */
  public void draw(String year, String month, String day) throws Exception {
    draw(getDaily(year, month, day), year + "/" + month + "/" + day);
  }

  /**
   * draws The Requested Bar Chart for that year and Month and day sorted Custom
   * @param begin
   * @param end
   * @throws Exception
   */
  public void draw(ZonedDateTime begin, ZonedDateTime end) throws Exception {
    draw(getCustom(begin, end), begin.toString() + " - " + end.toString());
  }

  private void draw(CustomContainer<Transaction> listOfTransactions, String dateOfInterest) {

    this.listOfTransactions = listOfTransactions;

    DefaultPieDataset dataset_Payout = new DefaultPieDataset();
    DefaultPieDataset dataset_Deposit = new DefaultPieDataset();

    Set<String> payoutCategorys = user.getCategories(new PayoutCategory());

    Map<String, Float> combinedTransactionValuesOfCategorys = new HashMap<>();

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

      } else {

        String category = ((Deposit) it.element()).getCategory();

        if (combinedTransactionValuesOfCategorys.containsKey(category)) {
          combinedTransactionValuesOfCategorys.put(
              category,
              combinedTransactionValuesOfCategorys.get(category)
                  + ((Deposit) it.element()).getAmount());
        } else
          combinedTransactionValuesOfCategorys.put(category, ((Deposit) it.element()).getAmount());
      }
    }

    for (String category : combinedTransactionValuesOfCategorys.keySet()) {

      if (payoutCategorys.contains(category))
        dataset_Payout.setValue(category, combinedTransactionValuesOfCategorys.get(category));
      else dataset_Deposit.setValue(category, combinedTransactionValuesOfCategorys.get(category));
    }

    JPanel panel_deposit = new JPanel();
    JPanel panel_payout = new JPanel();

    JFreeChart chart_deposit =
        ChartFactory.createPieChart("Deposits - " + dateOfInterest, dataset_Deposit);
    JFreeChart chart_payout =
        ChartFactory.createPieChart("Payouts - " + dateOfInterest, dataset_Payout);

    ChartFrame chartFrame_deposit = new ChartFrame("Pie Chart Deposit", chart_deposit);
    ChartFrame chartFrame_payout = new ChartFrame("Pie Chart Payout", chart_payout);

    // panel_deposit.add(chartFrame_deposit);
    // panel_deposit.add(chartFrame_payout);

    // panel_deposit.setSize(500,500);
    // panel_deposit.setVisible(true);

    chartFrame_payout.setSize(450, 500);
    chartFrame_payout.setVisible(true);

    chartFrame_deposit.setSize(450, 500);
    chartFrame_deposit.setVisible(true);
  }
  // needs Month as '01' .. '02' ..
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

  public CustomContainer<Transaction> getYearly(String year) throws Exception {

    if (Integer.valueOf(year) <= 0) throw new IndexOutOfBoundsException("No Banks before Jesus!");

    Map<String, CustomContainer<Transaction>> orga =
        new GroupingBuilder().allAccs(user).yearly().organize();

    for (String key : orga.keySet()) {
      if (key.contains("Y" + year)) return orga.get(key);
    }

    throw new NoSuchFieldException("Given Date not present");
  }

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

  public CustomContainer<Transaction> getCustom(ZonedDateTime begin, ZonedDateTime end)
      throws Exception {
    Map<String, CustomContainer<Transaction>> orga =
        new GroupingBuilder().allAccs(user).userdefined(begin, end).organize();
    for (String key : orga.keySet()) return orga.get(key);

    throw new NoSuchFieldException("Given Date not present");
  }
}
