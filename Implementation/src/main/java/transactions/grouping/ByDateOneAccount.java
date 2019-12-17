package transactions.grouping;

import iteration.CustomContainer;
import transactions.Transaction;
import user.TransactionStore;

public class ByDateOneAccount implements TransactionOrganizing {

  private final TransactionStore store;
  private final Integer ID;

  public ByDateOneAccount(final TransactionStore store, final Integer accountID) {
    this.store = store;
    ID = accountID;
  }

  @Override
  public CustomContainer<Transaction> organize() {
    return store.getTransactions().get(ID);
  }
}






