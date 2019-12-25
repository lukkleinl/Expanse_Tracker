package transactions.grouping.byCategory;

import java.time.ZonedDateTime;
import java.util.Map;
import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Transaction;
import transactions.grouping.OrganizingRoot;
import transactions.grouping.TransactionOrganizing;

public class ByCategory extends OrganizingRoot {

  public ByCategory(final TransactionOrganizing to) {
    super(to);
  }

  @Override
  public Map<String, CustomContainer<Transaction>> organize() {
    Map<String, CustomContainer<Transaction>> toDec = this.decFilter.organize();
    CustomIterator<Transaction> iter;

    for (String key : toDec.keySet()) {
      iter = toDec.get(key).getIterator();

      Transaction nextToAdd = iter.next();

      for (String category : this.decFilter.getNestedCategories()) {
        while (iter.hasNext()) {
          nextToAdd = iter.next();
          if (category.equalsIgnoreCase(nextToAdd.getCategory())) {
            this.grouped.get(category).add(nextToAdd);
          }
        }
      }
    }

    return this.grouped;
  }

  @Override
  public ZonedDateTime earliest() {
    return this.decFilter.earliest();
  }
}


