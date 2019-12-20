package transactions.grouping.byAccount;

import java.time.ZonedDateTime;
import java.util.Set;
import iteration.CustomContainer;
import transactions.Transaction;
import transactions.grouping.TransactionOrganizing;
import user.User;

public class OneAccount implements TransactionOrganizing {

  private final User user;
  private final Integer ID;

  public OneAccount(final User user, final Integer accountID) {
    this.user = user;
    this.ID = accountID;
  }

  @Override
  public CustomContainer<Transaction> organize() {
    return this.user.getTransactionStore().getTransactions().get(this.ID);
  }

  @Override
  public Set<String> getNestedCategories() {
    return this.user.getCategories(null);
  }

  @Override
  public ZonedDateTime earliest() {
    return this.user.getTransactionStore().getTransactions().get(this.ID).getIterator().next()
        .getCreationDate();
  }
}


