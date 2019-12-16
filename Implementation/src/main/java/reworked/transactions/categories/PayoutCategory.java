package reworked.transactions.categories;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import transactions.Payout;

/**
 * Handles operations for payout categories.
 *
 * @author Michael Watholowitsch
 */
public class PayoutCategory implements TransactionCategoryFunctionality {
  public static final String CATEGORY = new Payout(null, 0, null, null).getSimpleName();
  private final String categoryname;

  /** Only used for category retrieval. */
  public PayoutCategory() {
    categoryname = null;
  }
  public PayoutCategory(final String newcategory) {
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
