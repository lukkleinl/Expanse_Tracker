package transactions.grouping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.Transaction;

public abstract class OrganizingRoot implements TransactionOrganizing {

  protected final TransactionOrganizing decFilter;
  protected final Map<String, CustomContainer<Transaction>> grouped;
  protected final List<CustomIterator<Transaction>> iterators;

  public OrganizingRoot(final TransactionOrganizing method) {
    this.decFilter = method;
    this.iterators = new ArrayList<>();
    this.grouped = new TreeMap<>();
    for (String category : this.decFilter.getNestedCategories()) {
      this.grouped.put(category, new CustomList<>());
    }
  }

  @Override
  public final Set<String> getNestedCategories() {
    return this.decFilter.getNestedCategories();
  }

  protected boolean notDone() {
    for (CustomIterator iter : this.iterators) {
      if (iter.hasNext())
        return true;
    }
    return false;
  }
}
