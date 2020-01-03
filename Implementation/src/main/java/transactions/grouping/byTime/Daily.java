package transactions.grouping.byTime;

import java.util.Map;
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

  /**
   * Constructor
   *
   * @param wrappee the grouping which shall be decorated
   */
  public Daily(final TransactionOrganizing wrappee) {
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
    StringBuilder sb = new StringBuilder();
    sb.append(mappingborder);
    sb.append(trans.getCreationDate().getYear() + "-");
    if (trans.getCreationDate().getMonthValue() < 10) {
      sb.append("0");
    }
    sb.append(trans.getCreationDate().getMonthValue() + "-");
    if (trans.getCreationDate().getDayOfMonth() < 10) {
      sb.append("0");
    }
    sb.append(trans.getCreationDate().getDayOfMonth());
    sb.append(mappingborder);
    sb.append("_" + oldKey);
    return sb.toString();
  }
}


