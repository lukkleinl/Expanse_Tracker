package reworked.transactions.categories;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import transactions.Deposit;

/**
 * Handles operations for deposit categories.
 *
 * @author Michael Watholowitsch
 */
public class DepositCategory implements TransactionCategoryFunctionality {
  public static final String CATEGORY = new Deposit(null, 0, null, null).getSimpleName();
  private final String categoryname;

  /** Only used for category retrieval. */
  public DepositCategory() {
    categoryname = null;
  }
  public DepositCategory(final String newcategory) {
    categoryname = newcategory;
  }

  @Override
  public void addCategory(final Map<String,Set<String>> categories) {
    if (categoryname == null) return;
    categories.putIfAbsent(CATEGORY, new HashSet<>());
    categories.get(CATEGORY).add(categoryname);
  }

  @Override
  public Set<String> getCategories(final Map<String,Set<String>> categories) {
    if (categories == null) return new HashSet<>();
    Set<String> list = categories.get(CATEGORY);
    return list != null ? list : new HashSet<>();
  }
}
