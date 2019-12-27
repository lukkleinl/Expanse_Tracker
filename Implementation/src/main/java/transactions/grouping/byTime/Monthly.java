package transactions.grouping.byTime;

import java.time.Month;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.Transaction;
import transactions.grouping.OrganizingRoot;
import transactions.grouping.TransactionOrganizing;

/**
 * Groups transactions by their creation-month.
 *
 * @author Michael Watholowitsch
 */
public class Monthly extends OrganizingRoot {
  private final Set<Month> mappedmonths;

  /**
   * Constructor
   *
   * @param wrappee the grouping which shall be decorated
   */
  public Monthly(final TransactionOrganizing wrappee) {
    super(wrappee);
    mappedmonths = new TreeSet<>();
  }

  @Override
  public void performOrganizing() {
    Map<String, CustomContainer<Transaction>> toDec = root.organize();
    CustomIterator<Transaction> iter;

    // get the months
    for (String key : toDec.keySet()) {
      Month month;
      iter = toDec.get(key).getIterator();

      while (iter.hasNext()) {
        month = iter.next().getCreationDate().getMonth();
        if (!mappedmonths.contains(month)) {
          mappedmonths.add(month);
        }
      }
    }

    // grouping starts here
    String groupedkey = null;
    for (String key : toDec.keySet()) {
      iter = toDec.get(key).getIterator();

      for (Month month : mappedmonths) {
        groupedkey = month + "_" + key;
        grouped.putIfAbsent(groupedkey, new CustomList<>());

        while (iter.hasNext() && month.equals(iter.element().getCreationDate().getMonth())) {
          grouped.get(groupedkey).add(iter.next());
        }
      }
    }
  }
}







