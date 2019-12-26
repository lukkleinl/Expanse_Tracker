package transactions.grouping;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Transaction;

/**
 * Superclass for all non-base decorators.
 *
 * @author Michael Watholowitsch
 */
public abstract class OrganizingRoot implements TransactionOrganizing {

  protected final TransactionOrganizing decFilter;
  protected final Map<String, CustomContainer<Transaction>> grouped;
  protected final List<CustomIterator<Transaction>> iterators;

  public OrganizingRoot(final TransactionOrganizing method) {
    decFilter = method;
    iterators = new ArrayList<>();
    grouped = new HashMap<>();
  }

  @Override
  public final Set<String> getNestedCategories() {
    return decFilter.getNestedCategories();
  }

  @Override
  public ZonedDateTime earliest() {
    return decFilter.earliest();
  }

  /**
   * @return {@code true} if there is still an iterator that has elements left to process
   */
  @SuppressWarnings("rawtypes")
  protected boolean notDone() {
    for (CustomIterator iter : iterators) {
      if (iter.hasNext())
        return true;
    }
    return false;
  }
}
