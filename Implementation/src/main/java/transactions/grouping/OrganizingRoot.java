package transactions.grouping;

import java.util.ArrayList;
import java.util.List;
import iteration.CustomIterator;
import transactions.Transaction;

public abstract class OrganizingRoot implements TransactionOrganizing {
  protected final TransactionOrganizing decFilter;
  protected final List<CustomIterator<Transaction>> iterators;

  public OrganizingRoot(final TransactionOrganizing method) {
    decFilter = method;
    iterators = new ArrayList<>();
  }

  protected boolean notDone() {
    for (CustomIterator iter : iterators) {
      if (iter.hasNext()) return true;
    }
    return false;
  }
}
