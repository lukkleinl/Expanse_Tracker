package transactions.categories;

import java.util.Map;
import java.util.Set;

/**
 * Defines functionality for handling categories. Realized as abstract class restrict usage of
 * the implemented methods to the package since only the CategoryStore should be able to use said
 * methods directly.
 *
 * @author Michael Watholowitsch
 */
public abstract class TransactionCategoryFunctionality {
  /**
   * Adds a new category.
   *
   * @param categories the {@linkplain Map} where the new category is added
   */
  abstract void addCategory(Map<String, Set<String>> categories);

  /**
   * @param categories the {@linkplain Map} of categories
   * @return a {@linkplain Set} of categories which depend on the implementation of this method
   */
  abstract Set<String> getCategories(Map<String, Set<String>> categories);
}
