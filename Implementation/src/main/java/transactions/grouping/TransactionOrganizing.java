package transactions.grouping;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;
import iteration.CustomContainer;
import transactions.Transaction;

/**
 * Base-Interface for grouping decorators.
 *
 * @author Michael Watholowitsch
 */
public interface TransactionOrganizing {
  /**
   * Implementations decorate the grouping.
   *
   * @return the grouped transactions
   */
  Map<String, CustomContainer<Transaction>> organize();

  /* --------- the methods below are needed for implementation of different decorators ---------- */

  /** @return the categories */
  Set<String> getNestedCategories();

  /** @return the earliest time depending on the transaction and implementation */
  ZonedDateTime earliest();
}
