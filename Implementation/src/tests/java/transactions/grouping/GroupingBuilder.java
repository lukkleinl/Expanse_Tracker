package transactions.grouping;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import iteration.CustomContainer;
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
class GroupingBuilder {
  private TransactionOrganizing root = null;
  private TransactionOrganizing orga = null;
  private boolean reset = false;

  GroupingBuilder() {}

  public Map<String, CustomContainer<Transaction>> organize() {
    if (orga == null)
      return new HashMap<>();
    return orga.organize();
  }

  /** Returns the current decoration. */
  public TransactionOrganizing getDecoration() {
    return orga;
  }

  /**
   * Returns the current decoration and sets the current decoration to its root (e.g. for the call
   * {@code new GroupingBuilder().allAccs(...).category().yearly().getDecorationAndResetToRoot();} the root
   * would be the same AllAccounts(...) instance as the .allAccs(...) call)
   */
  public TransactionOrganizing getDecorationAndResetToRoot() {
    this.resetOrga();
    reset = true;
    return orga;
  }

  /* ------------------------------ Bases ------------------------------ */
  public GroupingBuilder allAccs(final User user) {
    orga = new AllAccounts(user);
    root = orga;
    return this;
  }

  public GroupingBuilder oneAcc(final User user, final Integer accountID) {
    orga = new OneAccount(user, accountID);
    root = orga;
    return this;
  }

  /* ------------------------------ Nesters ------------------------------ */
  public GroupingBuilder category() {
    if (orga != null) {
      this.resetOrga();
      orga = new ByCategory(orga);
    }
    return this;
  }

  public GroupingBuilder daily() {
    if (orga != null) {
      this.resetOrga();
      orga = new Daily(orga);
    }
    return this;
  }

  public GroupingBuilder monthly() {
    if (orga != null) {
      this.resetOrga();
      orga = new Monthly(orga);
    }
    return this;
  }

  public GroupingBuilder yearly() {
    if (orga != null) {
      this.resetOrga();
      orga = new Yearly(orga);
    }
    return this;
  }

  public GroupingBuilder userdefined(final ZonedDateTime begin, final ZonedDateTime end) {
    if (orga != null) {
      this.resetOrga();
      orga = new UserDefined(orga,begin,end);
    }
    return this;
  }

  private void resetOrga() {
    if (reset) {
      orga = root;
      reset = false;
    }
  }
}






