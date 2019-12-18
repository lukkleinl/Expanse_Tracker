package transactions.categories;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * Used to store a users categories for transactions.
 *
 * @author Michael Watholowitsch
 */
public final class CategoryStore {
  private final Map<String, Set<String>> categories;

  /** Constructor */
  public CategoryStore() {
    categories = new TreeMap<>();
  }

  /**
   * @param strategy The strategy which determines what kind of categories are retrieved. If this
   *        parameter is {@code null}, then all stored categories are retrieved.
   * @return a {@linkplain Set} of categories
   */
  public Set<String> getCategories(final TransactionCategoryFunctionality strategy) {
    if (strategy != null)
      return strategy.getCategories(categories);

    Set<String> all = new LinkedHashSet<>();

    for (Entry<String, Set<String>> key : categories.entrySet()) {
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
    if (strategy == null)
      return;
    strategy.addCategory(categories);
  }

  /**
   * Removes a category if it is supported.
   *
   * @param categoryname the name of the to-be removed category (NOTE: the comparison of names is
   *        case-insensitive)
   */
  public void removeCategory(final String categoryname) {
    if (categoryname == null) return;
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
   * Returns the key to which {@code category} is mapped or an empty string if no mapping is
   * present.
   *
   * @param category the category of which the key should be be
   * @return the key
   */
  public String keyOfCategory(final String category) {
    if (category != null) {
      for (Entry entry : categories.entrySet()) {
        if (categories.get(entry.getKey()).contains(category))
          return (String) entry.getKey();
      }
    }
    return "";
  }

  /**
   * @param categoryname the name of the category in question
   * @return {@code true} if the category is supported, else {@code false} (NOTE: the comparison of
   *         names is case-insensitive)
   */
  public boolean categorySupported(final String categoryname) {
    if (categoryname == null) return false;
    for (String key : categories.keySet()) {
      for (String type : categories.get(key)) {
        if (type.equalsIgnoreCase(categoryname))
          return true;
      }
    }
    return false;
  }

  /**
   * Adds default categories for the user. Returns the same instance for smooth calling without
   * extra instantiation when the store should be passed to a method.
   *
   * @return the calling CategoryStore
   */
  public CategoryStore withDefaultCategories() {
    this.addTransactionCategory(new PayoutCategory("EDUCATION"));
    this.addTransactionCategory(new PayoutCategory("FOOD"));
    this.addTransactionCategory(new PayoutCategory("TRANSPORTATION"));

    this.addTransactionCategory(new DepositCategory("SALARY"));
    this.addTransactionCategory(new DepositCategory("DIVIDEND"));

    return this;
  }
}


