package transactions.grouping.byCategory;

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
 * Groups transactions by their category.
 *
 * @author Michael Watholowitsch
 */
public class ByCategory extends OrganizingRoot {

  private final Set<String> categories;

  /**
   * Constructor
   *
   * @param wrappee the grouping which shall be decorated
   */
  public ByCategory(final TransactionOrganizing wrappee) {
    super(wrappee);
    categories = new TreeSet<>();
  }

  @Override
  public void performOrganizing() {
    Map<String, CustomContainer<Transaction>> toDec = root.organize();
    CustomIterator<Transaction> iter;
    String groupedkey = null;

    // get all categories
    for (String key : toDec.keySet()) {
      iter = toDec.get(key).getIterator();

      while (iter.hasNext()) {
        groupedkey = iter.next().getCategory();
        if (!categories.contains(groupedkey)) {
          categories.add(groupedkey);
        }
      }
    }

    // grouping starts here
    for (String key : toDec.keySet()) {
      iter = toDec.get(key).getIterator();

      for (String category : categories) {
        groupedkey = key + "_" + category;
        grouped.putIfAbsent(groupedkey, new CustomList<>());

        while (iter.hasNext() && category.equalsIgnoreCase(iter.element().getCategory())) {
          grouped.get(groupedkey).add(iter.next());
        }
      }
    }
  }
}


