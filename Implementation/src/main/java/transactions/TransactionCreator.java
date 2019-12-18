package transactions;

import java.time.ZonedDateTime;
import exceptions.SWE_RuntimeException;
import transactions.categories.CategoryStore;

/**
 * Factory for creating transactions.
 */
public abstract class TransactionCreator {
  /**
   * Creates new transactions. The primary way to create completely new transactions.
   *
   * @param category the category of the transaction (e.g. Food, Salary, ...)
   * @param amount the amount of money of the transaction
   * @param description a description of the transaction
   * @param store the categories of the user who wants to create this transaction
   * @return a transaction with the specified values
   *
   * @throws SWE_RuntimeException if the passed category is not related with this user or if either the category or the store was {@code null}
   */
  public static Transaction newTransactionWith(final String category, final float amount, final String description, final CategoryStore store) {
    if (store == null || category == null) throw new SWE_RuntimeException("Could not check if the category is known !");

    if (store.categorySupported(category)) {
      String storedcategory = store.keyOfCategory(category);

      if (storedcategory.equalsIgnoreCase(new Deposit(null, 0, null, null).getSimpleName())) return new Deposit(null, amount, category, description);
      else if (storedcategory.equalsIgnoreCase(new Payout(null, 0, null, null).getSimpleName())) return new Payout(null, amount, category, description);
    }

    throw new SWE_RuntimeException("Cannot create this transaction, the category is unknown !");
  }

  /**
   * Creates new transactions. This method should only be used to create transactions from persistently stored data.
   *
   * @param date the creation date of the transaction
   * @param category the category of the transaction (e.g. Food, Salary, ...)
   * @param amount the amount of money of the transaction
   * @param description a description of the transaction
   * @param store the categories of the user who wants to create this transaction
   * @return a transaction with the specified values
   *
   * @throws SWE_RuntimeException if the passed category is not related with this user or if either the category or the store was {@code null}
   */
  public static Transaction newTransactionWith(final ZonedDateTime date, final String category, final float amount, final String description, final CategoryStore store) {
    if (store == null || category == null) throw new SWE_RuntimeException("Could not check if the category is known !");

    if (store.categorySupported(category)) {
      String storedcategory = store.keyOfCategory(category);

      if (storedcategory.equalsIgnoreCase(new Deposit(null, 0, null, null).getSimpleName())) return new Deposit(date, amount, category, description);
      else if (storedcategory.equalsIgnoreCase(new Payout(null, 0, null, null).getSimpleName())) return new Payout(date, amount, category, description);
    }

    throw new SWE_RuntimeException("Cannot create this transaction, the category is unknown !");
  }
}
