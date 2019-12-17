package transactions.categories;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Handles operations for deposit categories.
 *
 * @author Michael Watholowitsch
 */
public class DepositCategory implements TransactionCategoryFunctionality {
  public static final String CATEGORY = "DEPOSIT";
  private final String categoryname;

  /** Only used for category retrieval. */
  public DepositCategory() {
    categoryname = null;
  }
  public DepositCategory(final String newcategory) {
    categoryname = newcategory.toUpperCase();
  }

  @Override
  public void addCategory(final Map<String,Set<String>> categories) {
    if (categoryname == null) return;
    categories.putIfAbsent(CATEGORY, new TreeSet<>());
    categories.get(CATEGORY).add(categoryname);
  }

  @Override
  public Set<String> getCategories(final Map<String,Set<String>> categories) {
    if (categories == null) return new TreeSet<>();
    Set<String> filtered = categories.get(CATEGORY);
    return filtered != null ? filtered : new TreeSet<>();
  }
}
