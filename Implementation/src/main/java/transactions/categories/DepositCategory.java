package transactions.categories;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import transactions.Deposit;

/**
 * Handles operations for deposit categories.
 *
 * @author Michael Watholowitsch
 */
public class DepositCategory extends TransactionCategoryFunctionality {
  public static final String CATEGORY = Deposit.getSimpleName();
  private final String categoryname;

  /** Only used for category retrieval. */
  public DepositCategory() {
    categoryname = null;
  }

  /**
   * Allows adding a new Deposit Category.
   * @param newcategory the name of the new category
   */
  public DepositCategory(final String newcategory) {
    categoryname = newcategory.toUpperCase();
  }

  @Override
  void addCategory(final Map<String,Set<String>> categories) {
    if (categoryname == null) return;
    categories.putIfAbsent(CATEGORY, new TreeSet<>());
    categories.get(CATEGORY).add(categoryname);
  }

  @Override
  Set<String> getCategories(final Map<String,Set<String>> categories) {
    if (categories == null) return new TreeSet<>();
    Set<String> filtered = categories.get(CATEGORY);
    return filtered != null ? filtered : new TreeSet<>();
  }
}
