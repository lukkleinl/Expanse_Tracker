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
  protected void performOrganizing() {
    Map<String, CustomContainer<Transaction>> toDec = this.root.organize();
    String newKey = "";

    for (String oldKey : toDec.keySet()) {
      for (CustomIterator<Transaction> iter = toDec.get(oldKey).getIterator(); iter.hasNext(); iter
          .next()) {
        newKey = keyCreation(iter.element(), oldKey);
        this.grouped.putIfAbsent(newKey, new CustomList<>());

        if (!this.grouped.get(newKey).contains(iter.element())) {
          this.grouped.get(newKey).add(iter.element());
        }
      }
    }
  }

  private String keyCreation(final Transaction trans, final String oldKey) {
    return oldKey + "_" + trans.getCategory();
  }
}


