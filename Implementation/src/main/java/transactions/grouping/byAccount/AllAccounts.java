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

public class AllAccounts implements TransactionOrganizing {

  private final User user;
  private final List<CustomIterator<Transaction>> iterators;

  public AllAccounts(final User user) {
    this.user = user;
    this.iterators = new ArrayList<>();
  }

  @Override
  public Map<String, CustomContainer<Transaction>> organize() {
    Map<String, CustomContainer<Transaction>> organized = new HashMap<>();
    CustomContainer<Transaction> orgacont = new CustomList<>();
    final List<Transaction> transactions = new ArrayList<>();

    for (Integer key : this.user.getTransactionStore().getTransactions().keySet()) {
      CustomIterator<Transaction> iter =
          this.user.getTransactionStore().getTransactions().get(key).getIterator();
      this.iterators.add(iter);
      transactions.add(iter.next());
    }

    Transaction nextToAdd = null;
    Transaction currentlychecked = null;
    do { // perform do-while as long as any iterator has a next element
      nextToAdd = transactions.get(0);
      currentlychecked = null;

      for (int i = 1; i < transactions.size(); i++) {
        currentlychecked = transactions.get(i);
        if (nextToAdd.getCreationDate().isAfter(currentlychecked.getCreationDate())) {
          nextToAdd = currentlychecked;
        }
      }

      orgacont.add(nextToAdd);
      int idx = transactions.indexOf(nextToAdd);

      // if-case - the iterator cannot contribute any new elements and is therefore removed from the
      // list
      if (!this.iterators.get(idx).hasNext())
        this.iterators.remove(this.iterators.get(idx));
      // else-case - puts the next element of the CustomContainer whose Transaction was just added
      // into the list of currently 'oldest' transactions at the location where the old transaction
      // was
      else
        transactions.add(idx, this.iterators.get(idx).next());

      // remove the 'old oldest' transaction from the list
      transactions.remove(nextToAdd);
    } while (this.notDone());

    // add the remaining transactions
    while (!transactions.isEmpty()) {
      nextToAdd = transactions.get(0);

      for (int i = 1; i < transactions.size(); i++) {
        currentlychecked = transactions.get(i);
        if (nextToAdd.getCreationDate().isAfter(currentlychecked.getCreationDate())) {
          nextToAdd = currentlychecked;
        }
      }
      orgacont.add(nextToAdd);
      transactions.remove(nextToAdd);
    }

    organized.put("AllAccounts", orgacont);
    return organized;
  }

  /** @return {@code true} if at least one iterator has elements left, else {@code false} */
  private boolean notDone() {
    for (CustomIterator iter : this.iterators) {
      if (iter.hasNext())
        return true;
    }
    return false;
  }

  @Override
  public Set<String> getNestedCategories() {
    return this.user.getCategories(null);
  }

  @Override
  public ZonedDateTime earliest() {
    Transaction earliest = null;
    Transaction tmp = null;

    for (Integer i : this.user.getTransactionStore().getTransactions().keySet()) {
      tmp = this.user.getTransactionStore().getTransactions().get(i).getIterator().next();

      if (earliest == null) {
        earliest = tmp;
      } else if (earliest.getCreationDate().isAfter(tmp.getCreationDate())) {
        earliest = tmp;
      }
    }
    return earliest.getCreationDate();
  }
}


