package transactions.grouping.byAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.Transaction;
import transactions.grouping.TransactionOrganizing;
import user.User;

/**
 * Base class for decorating the view of the transactions of all accounts of the passed user.
 *
 * @author Michael Watholowitsch
 */
public class AllAccounts implements TransactionOrganizing {

  private final User user;
  private final List<CustomIterator<Transaction>> iterators;

  /**
   * @param user the user whose transactions will be grouped
   */
  public AllAccounts(final User user) {
    this.user = user;
    this.iterators = new ArrayList<>();
  }

  @Override
  public Map<String, CustomContainer<Transaction>> organize() {
    Map<String, CustomContainer<Transaction>> organized = new HashMap<>();
    CustomContainer<Transaction> orgacont = new CustomList<>();
    CustomIterator<Transaction> current = null;

    for (Integer key : this.user.getTransactionStore().getTransactions().keySet()) {
      CustomIterator<Transaction> iter =
          this.user.getTransactionStore().getTransactions().get(key).getIterator();
      this.iterators.add(iter);
    }

    while (this.notDone()) {
      for (int i = 0; i < this.iterators.size(); i++) {
        if ((current == null) && this.iterators.get(i).hasNext()) {
          current = this.iterators.get(i);
        } else if (this.iterators.get(i).hasNext() && current.element().getCreationDate()
            .isAfter(this.iterators.get(i).element().getCreationDate())) {
          current = this.iterators.get(i);
        }
      }
      orgacont.add(current.next());
      current = null;
    }

    organized.put("", orgacont);
    return organized;
  }

  /** @return {@code true} if at least one iterator has elements left, else {@code false} */
  @SuppressWarnings("rawtypes")
  private boolean notDone() {
    for (CustomIterator iter : this.iterators) {
      if (iter.hasNext())
        return true;
    }
    return false;
  }
}


