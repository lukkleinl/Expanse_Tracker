package transactions.grouping.byAccount;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import iteration.CustomContainer;
import transactions.Transaction;
import transactions.grouping.TransactionOrganizing;
import user.User;

/**
 * Base class for decorating the view of the transactions of a single account of the passed user.
 *
 * @author Michael Watholowitsch
 */
public class OneAccount implements TransactionOrganizing {

  private final User user;
  private final Integer ID;

  /**
   * @param user the user whose transactions will be grouped
   * @param accountID the ID of the account which's transactions will be grouped
   */
  public OneAccount(final User user, final Integer accountID) {
    this.user = user;
    ID = accountID;
  }

  @Override
  public Map<String, CustomContainer<Transaction>> organize() {
    Map<String, CustomContainer<Transaction>> organized = new HashMap<>();
    organized.put(Integer.toString(ID),
        user.getTransactionStore().getTransactions().get(ID));
    return organized;
  }

  @Override
  public Set<String> getNestedCategories() {
    return user.getCategories(null);
  }

  @Override
  public ZonedDateTime earliest() {
    return user.getTransactionStore().getTransactions().get(ID).getIterator().element()
        .getCreationDate();
  }
}


