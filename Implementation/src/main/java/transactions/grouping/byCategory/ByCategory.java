package transactions.grouping.byCategory;

import java.util.Map;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.Transaction;
import transactions.grouping.OrganizingRoot;
import transactions.grouping.TransactionOrganizing;

public class ByCategory extends OrganizingRoot {

  public ByCategory(final TransactionOrganizing to) {
    super(to);
  }

  @Override
  public Map<String, CustomContainer<Transaction>> organize() {
    Map<String, CustomContainer<Transaction>> toDec = decFilter.organize();
    CustomIterator<Transaction> iter;
    String groupedkey = null;

    for (String key : toDec.keySet()) {
      iter = toDec.get(key).getIterator();

      for (String category : decFilter.getNestedCategories()) {
        groupedkey = key + " " + category;
        grouped.putIfAbsent(groupedkey, new CustomList<>());

        while (iter.hasNext() && category.equalsIgnoreCase(iter.element().getCategory())) {
          grouped.get(groupedkey).add(iter.next());
        }
      }
    }

    return grouped;
  }
}


