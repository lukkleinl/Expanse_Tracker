package transactions.grouping;

import java.util.ArrayList;
import java.util.List;
import iteration.CustomIterator;
import transactions.Transaction;

public abstract class OrganizingRoot implements TransactionOrganizing {
  protected final TransactionOrganizing root;
  protected final List<CustomIterator<Transaction>> iterators;

  public OrganizingRoot(final TransactionOrganizing to) {
    root = to;
    iterators = new ArrayList<>();
  }

  protected boolean notDone() {
    for (CustomIterator iter : iterators) {
      if (iter.hasNext()) return true;
    }
    return false;
  }
}