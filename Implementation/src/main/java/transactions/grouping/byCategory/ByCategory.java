package transactions.grouping.byCategory;

import java.util.Map;
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

  /**
   * Constructor
   *
   * @param wrappee the grouping which shall be decorated
   */
  public ByCategory(final TransactionOrganizing wrappee) {
    super(wrappee);
  }

  @Override
  public void performOrganizing() {
    Map<String, CustomContainer<Transaction>> toDec = this.root.organize();

    // get all categories
    for (String key : toDec.keySet()) {
      for (CustomIterator<Transaction> iter = toDec.get(key).getIterator(); iter.hasNext(); iter
          .next()) {
        if (!this.grouped.keySet().contains(iter.element().getCategory())) {
          this.grouped.put(key + "_" + iter.element().getCategory(), new CustomList<>());
        }
      }
    }

    // grouping starts here
    for (String key : toDec.keySet()) {
      for (String category : this.grouped.keySet()) {
        for (CustomIterator<Transaction> iter = toDec.get(key).getIterator(); iter.hasNext(); iter
            .next())
          if (category.contains(iter.element().getCategory())) {
            this.grouped.get(category).add(iter.element());
          }
      }
    }
  }
}


