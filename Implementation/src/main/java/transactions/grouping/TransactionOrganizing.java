package transactions.grouping;

import java.time.ZonedDateTime;
import java.util.Set;

public interface TransactionOrganizing {
  /**
   * Implementations decorate the grouping.
   * 
   * @return the grouped transactions
   */
  <T> T organize();

  /** @return the categories */
  Set<String> getNestedCategories();

  /** @return the earliest time depending on the transaction and implementation */
  ZonedDateTime earliest();
}
