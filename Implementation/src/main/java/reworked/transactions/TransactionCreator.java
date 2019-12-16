package reworked.transactions;

import java.util.Date;
import exceptions.SWE_RuntimeException;
import reworked.transactions.categories.CategoryStore;

/**
 * Factory for creating transactions.
 */
public abstract class TransactionCreator {
  /**
   * Creates new transactions
   * @param amount the amount of money of the transaction
   * @param description a description of the transaction
   * @param date
   * @param category the category of the transaction (e.g. Food, Salary, ...)
   * @param store the categories of the user who wants to create this transaction
   * @return a transaction with the specified values
   *
   * @throws SWE_RuntimeException if the passed category is not related with this user
   */
  public static Transaction newTransactionWith(final float amount, final String description, final Date date, final String category, final CategoryStore store) {
    if (store.categorySupported(category)) {
      String storedcategory = store.keyOfCategory(category);

      // TODO - maybe simply create date here
      if (storedcategory.equalsIgnoreCase(new Deposit(null, 0, null, null).getSimpleName())) return new Deposit(date, amount, category, description);
      else if (storedcategory.equalsIgnoreCase(new Payout(null, 0, null, null).getSimpleName())) return new Payout(date, amount, category, description);
    }

    throw new SWE_RuntimeException("Cannot create this transaction, the category is unknown !");
  }
}
