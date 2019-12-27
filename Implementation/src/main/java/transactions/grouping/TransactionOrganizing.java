package transactions.grouping;

import java.util.Map;
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
}
