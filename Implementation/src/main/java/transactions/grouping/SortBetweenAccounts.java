package transactions.grouping;

import java.util.ArrayList;
import java.util.List;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.Transaction;
import user.TransactionStore;

public class SortBetweenAccounts implements TransactionOrganizing {

  private final TransactionStore store;
  private final List<CustomIterator<Transaction>> iterators;

  public SortBetweenAccounts(final TransactionStore store) {
    this.store = store;
    iterators = new ArrayList<>();
  }

  @Override
  public CustomContainer<Transaction> organize() {
    CustomContainer<Transaction> organized = new CustomList<>();
    List<Transaction> transactions = new ArrayList<>();

    for (Integer key : store.getTransactions().keySet()) {
      iterators.add(store.getTransactions().get(key).getIterator());
    }

    while (this.notDone()) {
      Transaction nextToAdd = null;

      // TODO - find the 'oldest' transaction in transactions and assign it to nextToAdd

      organized.add(nextToAdd);
      int idx = transactions.indexOf(nextToAdd);

      // puts the next element of the CustomContainer whose Transaction was just added into the list of
      // currently 'oldest' transactions at the location where the old transaction was
      transactions.add(idx,iterators.get(idx).next());
      // remove the 'old oldest' transaction from the list
      transactions.remove(nextToAdd);
    }

    return organized;
  }

  private boolean notDone() {
    for (CustomIterator iter : iterators) {
      if (iter.hasNext()) return true;
    }
    return false;
  }

}
