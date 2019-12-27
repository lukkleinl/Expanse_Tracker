package transactions.grouping.byTime;

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
 * Groups transactions by their creation-day.
 *
 * @author Michael Watholowitsch
 */
public class Daily extends OrganizingRoot {
  public static final String mappingborder = "D";
  private final Set<Integer> mappeddays;

  /**
   * Constructor
   *
   * @param wrappee the grouping which shall be decorated
   */
  public Daily(final TransactionOrganizing wrappee) {
    super(wrappee);
    mappeddays = new TreeSet<>();
  }

  @Override
  public void performOrganizing() {
    Map<String, CustomContainer<Transaction>> toDec = root.organize();
    CustomIterator<Transaction> iter;

    // get the days
    for (String key : toDec.keySet()) {
      Integer day;
      iter = toDec.get(key).getIterator();

      while (iter.hasNext()) {
        day = iter.next().getCreationDate().getDayOfMonth();
        if (!mappeddays.contains(day)) {
          mappeddays.add(day);
        }
      }
    }

    // grouping starts here
    String groupedkey = null;
    for (String key : toDec.keySet()) {
      iter = toDec.get(key).getIterator();

      for (Integer day : mappeddays) {
        groupedkey = mappingborder + day + mappingborder + "_" + key;
        grouped.putIfAbsent(groupedkey, new CustomList<>());

        while (iter.hasNext() && day.equals(iter.element().getCreationDate().getDayOfMonth())) {
          grouped.get(groupedkey).add(iter.next());
        }
      }
    }
  }
}
