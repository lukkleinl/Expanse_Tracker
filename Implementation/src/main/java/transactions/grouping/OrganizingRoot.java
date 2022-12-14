package transactions.grouping;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import iteration.CustomContainer;
import transactions.Transaction;

/**
 * Superclass for all non-base decorators.
 *
 * @author Michael Watholowitsch
 */
public abstract class OrganizingRoot implements TransactionOrganizing {

  protected final TransactionOrganizing root;
  protected final Map<String, CustomContainer<Transaction>> grouped;

  public OrganizingRoot(final TransactionOrganizing wrappee) {
    this.root = wrappee;
    this.grouped = new TreeMap<>();
  }

  /** Groupers implement this method for their grouping algorithm. */
  protected abstract void performOrganizing();

  /**
   * Override here to force removing mappings with empty CustomContainers after grouping.
   */
  @Override
  public final Map<String, CustomContainer<Transaction>> organize() {
    this.performOrganizing();
    this.clearMappings();

    return this.grouped;
  }

  // modified to fit the problem from "https://stackoverflow.com/a/223927"
  /** This method removes any keys with an empty CustomContainers as its value. */
  private final void clearMappings() {
    for (Iterator<String> it = this.grouped.keySet().iterator(); it.hasNext();) {
      if (this.grouped.get(it.next()).size() == 0) {
        it.remove();
      }
    }
  }
}


