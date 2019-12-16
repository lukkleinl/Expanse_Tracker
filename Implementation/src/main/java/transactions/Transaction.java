package transactions;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract class of a transaction for the different accounts.
 *
 * @author Patrick Gmasz
 */
public abstract class Transaction {
  private final String description;
  private final float amount;
  private final Date creationDate;
  private int ID;
  private static AtomicInteger nextId = new AtomicInteger();
  private final int id;

  /**
   * Creates a new Transaction.
   *
   * @param creationDate The date, when the transaction was executed.
   * @param amount The amount of money the transaction was about.
   * @param description A description the user can add to a transaction.
   */
  public Transaction(final Date creationDate, final float amount, final String description) {
    id = nextId.incrementAndGet();
    this.description = description;
    this.amount = amount;
    this.creationDate = creationDate;
  }

  /**
   * Getter for the description.
   *
   * @return The String description.
   */
  public String getDescription() {
    return description;
  }

  /**
   *
   * Getter for the amount.
   *
   * @return The float amount.
   */
  public float getAmount() {
    return amount;
  }

  /**
   *
   * Getter for the creation date.
   *
   * @return The Date, when the transaction was created.
   */
  public Date getCreationDate() {
    return creationDate;
  }

  /**
   * Getter for the id.
   *
   * @return The integer id.
   */
  public int getID()  { return id;}

  /**
   * @return the simple name of this transaction
   */
  public final String getSimpleName() {
    return this.getClass().getSimpleName().toUpperCase();
  }
}



