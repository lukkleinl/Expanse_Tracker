package transactions.categories;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Handles operations for payout categories.
 *
 * @author Michael Watholowitsch
 */
public class PayoutCategory extends TransactionCategoryFunctionality {
  public static final String CATEGORY = "PAYOUT";
  private final String categoryname;

  /** Only used for category retrieval. */
  public PayoutCategory() {
    categoryname = null;
  }

  /**
   * Allows adding a new Payout Category.
   * @param newcategory the name of the new category
   */
  public PayoutCategory(final String newcategory) {
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
