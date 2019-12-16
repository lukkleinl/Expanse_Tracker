package reworked.transactions.categories;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Used to store a users categories for transactions.
 *
 * @author Michael Watholowitsch
 */
public class CategoryStore {
  private final Map<String,Set<String>> categories;

  /** Constructor with basic provided categories for transactions. */
  public CategoryStore() {
    categories = new HashMap<>();

    this.addTransactionCategory(new DepositCategory("SALARY"));
    this.addTransactionCategory(new DepositCategory("DIVIDEND"));

    this.addTransactionCategory(new PayoutCategory("EDUCATION"));
    this.addTransactionCategory(new PayoutCategory("FOOD"));
    this.addTransactionCategory(new PayoutCategory("TRANSPORTATION"));
  }

  /**
   * @param strategy The strategy which determines what kind of categories are retrieved.
   * If this parameter is {@code null}, then all stored categories are retrieved.
   * @return a {@linkplain List} of categories
   */
  public Set<String> getCategories(final TransactionCategoryFunctionality strategy) {
    if (strategy != null) return strategy.getCategories(categories);

    Set<String> all = new HashSet<>();

    for (Entry<String,Set<String>> key : categories.entrySet()) {
      all.addAll(key.getValue());
    }

    return all;
  }

  /**
   * Adds a new transaction category.
   *
   * @param strategy the strategy which determines what kind of category is added
   */
  public void addTransactionCategory(final TransactionCategoryFunctionality strategy) {
    if (strategy == null) return;
    strategy.addCategory(categories);
  }

  /**
   * Removes a category if it is supported.
   *
   * @param categoryname the name of the to-be removed category (NOTE: the comparison of names is case-insensitive)
   */
  public void removeCategory(final String categoryname) {
    for (String key : categories.keySet()) {
      for (String type : categories.get(key)) {
        if (type.equalsIgnoreCase(categoryname)) {
          categories.get(key).remove(type);
          return;
        }
      }
    }
  }

  /**
   * @param categoryname the name of the category in question
   * @return {@code true} if the category is supported, else {@code false} (NOTE: the comparison of names is case-insensitive)
   */
  public boolean categorySupported(final String categoryname) {
    for (String key : categories.keySet()) {
      for (String type : categories.get(key)) {
        if (type.equalsIgnoreCase(categoryname))
          return true;
      }
    }
    return false;
  }
}


