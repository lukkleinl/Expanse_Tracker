package transactions.grouping.byAccount;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    iterators = new ArrayList<>();
  }

  @Override
  public Map<String, CustomContainer<Transaction>> organize() {
    Map<String, CustomContainer<Transaction>> organized = new HashMap<>();
    CustomContainer<Transaction> orgacont = new CustomList<>();
    CustomIterator<Transaction> current = null;

    for (Integer key : user.getTransactionStore().getTransactions().keySet()) {
      CustomIterator<Transaction> iter =
          user.getTransactionStore().getTransactions().get(key).getIterator();
      iterators.add(iter);
    }

    while (this.notDone()) {
      for (int i = 0; i < iterators.size(); i++) {
        if (current == null && iterators.get(i).hasNext()) {
          current = iterators.get(i);
        }
        else if (iterators.get(i).hasNext() && current.element().getCreationDate().isAfter(iterators.get(i).element().getCreationDate())) {
          current = iterators.get(i);
        }
      }
      orgacont.add(current.next());
      current = null;
    }

    organized.put("AllAccounts", orgacont);
    return organized;
  }

  /** @return {@code true} if at least one iterator has elements left, else {@code false} */
  @SuppressWarnings("rawtypes")
  private boolean notDone() {
    for (CustomIterator iter : iterators) {
      if (iter.hasNext())
        return true;
    }
    return false;
  }

  @Override
  public Set<String> getNestedCategories() {
    return user.getCategories(null);
  }

  @Override
  public ZonedDateTime earliest() {
    Transaction earliest = null;
    Transaction tmp = null;

    for (Integer i : user.getTransactionStore().getTransactions().keySet()) {
      tmp = user.getTransactionStore().getTransactions().get(i).getIterator().element();

      if (earliest == null) {
        earliest = tmp;
      } else if (earliest.getCreationDate().isAfter(tmp.getCreationDate())) {
        earliest = tmp;
      }
    }
    return earliest.getCreationDate();
  }
}


