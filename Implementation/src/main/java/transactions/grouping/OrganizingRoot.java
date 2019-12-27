package transactions.grouping;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Transaction;

/**
 * Superclass for all non-base decorators.
 *
 * @author Michael Watholowitsch
 */
public abstract class OrganizingRoot implements TransactionOrganizing {

  protected final TransactionOrganizing root;
  protected final Map<String, CustomContainer<Transaction>> grouped;
  protected final List<CustomIterator<Transaction>> iterators;

  public OrganizingRoot(final TransactionOrganizing wrappee) {
    root = wrappee;
    iterators = new ArrayList<>();
    grouped = new TreeMap<>();
  }

  /**
   * Returns the time of the earliest performed transaction.
   * @param map the performed transactions
   * @return the time of the earliest performed transaction
   */
  protected final ZonedDateTime earliest(final Map<String, CustomContainer<Transaction>> map) {
    ZonedDateTime earliest = null;
    for (String key : map.keySet()) {
      if (earliest == null) {
        earliest = map.get(key).getIterator().element().getCreationDate();
      }
      else if (earliest.isAfter(map.get(key).getIterator().element().getCreationDate())) {
        earliest = map.get(key).getIterator().element().getCreationDate();
      }
    }
    return earliest;
  }

  // modified to fit the problem from "https://stackoverflow.com/a/223927"
  /** This method removes any keys with an empty customlist as its value. */
  protected final void clearMappings() {
    for (Iterator<String> it = grouped.keySet().iterator(); it.hasNext(); ) {
      if (grouped.get(it.next()).size() == 0) {
        it.remove();
      }
    }
  }

  /** @return {@code true} if there is an iterator that still has elements left to process */
  @SuppressWarnings("rawtypes")
  protected boolean notDone() {
    for (CustomIterator iter : iterators) {
      if (iter.hasNext())
        return true;
    }
    return false;
  }
}



