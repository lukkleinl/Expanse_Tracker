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
 * Groups transactions by their creation-year.
 *
 * @author Michael Watholowitsch
 */
public class Yearly extends OrganizingRoot {
  public static final String mappingborder = "Y";
  private final Set<Integer> mappedyears;

  /**
   * Constructor
   *
   * @param wrappee the grouping which shall be decorated
   */
  public Yearly(final TransactionOrganizing wrappee) {
    super(wrappee);
    mappedyears = new TreeSet<>();
  }

  @Override
  public void performOrganizing() {
    Map<String, CustomContainer<Transaction>> toDec = root.organize();
    CustomIterator<Transaction> iter;

    // get the years
    for (String key : toDec.keySet()) {
      Integer year;
      iter = toDec.get(key).getIterator();

      while (iter.hasNext()) {
        year = iter.next().getCreationDate().getYear();
        if (!mappedyears.contains(year)) {
          mappedyears.add(year);
        }
      }
    }

    // grouping starts here
    String groupedkey = null;
    for (String key : toDec.keySet()) {
      iter = toDec.get(key).getIterator();

      for (Integer year : mappedyears) {
        groupedkey = mappingborder + year + mappingborder + "_" + key;
        grouped.putIfAbsent(groupedkey, new CustomList<>());

        while (iter.hasNext() && year.equals(iter.element().getCreationDate().getYear())) {
          grouped.get(groupedkey).add(iter.next());
        }
      }
    }
  }
}


