package transactions.changes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryStore {
  private final List<String> categories;

  /**
   * Constructor with basic provided categories for transactions.
   */
  public CategoryStore() {
    this.categories = new ArrayList<>();

    this.categories.add("SALARY");
    this.categories.add("DIVIDEND");

    this.categories.add("EDUCATION");
    this.categories.add("FOOD");
    this.categories.add("TRANSPORTATION");
  }

  /**
   * @param categoryname the name of the requested category
   * @return an Optional with the category if it is provided.
   */
  public Optional<String> getCategory(final String categoryname) {
    return this.categories.stream().filter(c -> c.equalsIgnoreCase(categoryname)).findAny();
  }

  /**
   * Adds a new category.
   * 
   * @param categoryname the name of the new category
   */
  public void addCategory(final String categoryname) {
    this.categories.add(categoryname);
  }

  /**
   * Removes a category if it is supported.
   * 
   * @param categoryname the name of the to-be removed category
   */
  public void removeCategory(final String categoryname) {
    if (this.categories.contains(categoryname)) {
      this.categories.remove(categoryname);
    }
  }
}


