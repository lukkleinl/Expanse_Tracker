package transactions.grouping;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Transaction;
import transactions.grouping.byAccount.AllAccounts;
import transactions.grouping.byAccount.OneAccount;
import transactions.grouping.byCategory.ByCategory;
import transactions.grouping.byTime.Daily;
import transactions.grouping.byTime.Monthly;
import transactions.grouping.byTime.UserDefined;
import transactions.grouping.byTime.Yearly;
import user.User;

/**
 * This class allows more simple usage of the grouping decoration.
 *
 * @author Michael Watholowitsch
 */
public class GroupingBuilder {
  private TransactionOrganizing root = null;
  private TransactionOrganizing orga = null;
  private boolean reset = false;

  public GroupingBuilder() {}

  public Map<String, CustomContainer<Transaction>> organize() {
    if (this.orga == null)
      return new HashMap<>();
    return this.orga.organize();
  }

  /** Returns the current decoration. */
  public TransactionOrganizing getDecoration() {
    this.resetOrga();
    return this.orga;
  }

  /**
   * Returns the current decoration and sets the current decoration to its root (e.g. for the call
   * {@code new GroupingBuilder().allAccs(...).category().yearly().getDecorationAndResetToRoot();}
   * the root would be the same AllAccounts(...) instance as the .allAccs(...) call)
   */
  public TransactionOrganizing getDecorationAndResetToRoot() {
    this.resetOrga();
    this.reset = true;
    return this.orga;
  }

  /* ------------------------------ Bases ------------------------------ */
  public GroupingBuilder allAccs(final User user) {
    this.orga = new AllAccounts(user);
    this.root = this.orga;
    return this;
  }

  public GroupingBuilder oneAcc(final User user, final Integer accountID) {
    this.orga = new OneAccount(user, accountID);
    this.root = this.orga;
    return this;
  }

  /* ------------------------------ Nesters ------------------------------ */
  public GroupingBuilder category() {
    if (this.orga != null) {
      this.resetOrga();
      this.orga = new ByCategory(this.orga);
    }
    return this;
  }

  public GroupingBuilder daily() {
    if (this.orga != null) {
      this.resetOrga();
      this.orga = new Daily(this.orga);
    }
    return this;
  }

  public GroupingBuilder monthly() {
    if (this.orga != null) {
      this.resetOrga();
      this.orga = new Monthly(this.orga);
    }
    return this;
  }

  public GroupingBuilder yearly() {
    if (this.orga != null) {
      this.resetOrga();
      this.orga = new Yearly(this.orga);
    }
    return this;
  }

  public GroupingBuilder userdefined(final ZonedDateTime begin, final ZonedDateTime end) {
    if (this.orga != null) {
      this.resetOrga();
      this.orga = new UserDefined(this.orga, begin, end);
    }
    return this;
  }

  private void resetOrga() {
    if (this.reset) {
      this.orga = this.root;
      this.reset = false;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Map<String, CustomContainer<Transaction>> transactions = this.orga.organize();

    for (String key : transactions.keySet()) {
      sb.append("\n" + key + "\n");
      for (CustomIterator<Transaction> it = transactions.get(key).getIterator(); it.hasNext(); it
          .next()) {
        sb.append("Creation: " + it.element().getFormattedCreationDate() + ", ");
        sb.append("ID=" + it.element().getID() + ", ");
        sb.append("Category=" + it.element().getCategory() + "\n");
      }
    }
    return sb.toString();
  }
}


