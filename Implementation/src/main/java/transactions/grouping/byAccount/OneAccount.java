package transactions.grouping.byAccount;

import iteration.CustomContainer;
import transactions.Transaction;
import transactions.grouping.TransactionOrganizing;
import user.TransactionStore;

public class OneAccount implements TransactionOrganizing {

  private final TransactionStore store;
  private final Integer ID;

  public OneAccount(final TransactionStore store, final Integer accountID) {
    this.store = store;
    ID = accountID;
  }

  @Override
  public CustomContainer<Transaction> organize() {
    return store.getTransactions().get(ID);
  }
}






