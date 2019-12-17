package transactions.categories;

import java.util.Map;
import java.util.Set;

/**
 * Defines functionality for handling categories.
 *
 * @author Michael Watholowitsch
 */
public interface TransactionCategoryFunctionality {
  /**
   * Adds a new category.
   *
   * @param categories the {@linkplain Map} where the new category is added
   */
  void addCategory(Map<String,Set<String>> categories);

  /**
   * @param categories the {@linkplain Map} of categories
   * @return a {@linkplain Set} of categories which depend on the implementation of this method
   */
  Set<String> getCategories(Map<String,Set<String>> categories);
}
