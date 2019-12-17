package transactions.grouping;

import java.util.ArrayList;
import java.util.List;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.Transaction;
import user.TransactionStore;

public class ByDateAllAccounts implements TransactionOrganizing {

  private final TransactionStore store;
  private final List<CustomIterator<Transaction>> iterators;

  public ByDateAllAccounts(final TransactionStore store) {
    this.store = store;
    iterators = new ArrayList<>();
  }

  @Override
  public CustomContainer<Transaction> organize() {
    final CustomContainer<Transaction> organized = new CustomList<>();
    final List<Transaction> transactions = new ArrayList<>();

    for (Integer key : store.getTransactions().keySet()) {
      iterators.add(store.getTransactions().get(key).getIterator());
    }

    for (CustomIterator<Transaction> iter : iterators) {
      transactions.add(iter.next());
    }

    Transaction nextToAdd = null;
    Transaction currentlychecked = null;
    do {
      nextToAdd = transactions.get(0);
      currentlychecked = null;

      // TODO - TESTING !!!
      for (int i = 1; i < transactions.size(); i++) {
        currentlychecked = transactions.get(i);
        if (nextToAdd.getCreationDate().isAfter(currentlychecked.getCreationDate())) {
          nextToAdd = currentlychecked;
        }
      }

      organized.add(nextToAdd);
      int idx = transactions.indexOf(nextToAdd);

      // puts the next element of the CustomContainer whose Transaction was just added into the list of
      // currently 'oldest' transactions at the location where the old transaction was
      transactions.add(idx,iterators.get(idx).next());
      // remove the 'old oldest' transaction from the list
      transactions.remove(nextToAdd);
    } while (this.notDone());
    return organized;
  }

  private boolean notDone() {
    for (CustomIterator iter : iterators) {
      if (iter.hasNext()) return true;
    }
    return false;
  }

}
