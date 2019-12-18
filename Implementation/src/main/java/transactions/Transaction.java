package transactions;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract class of a transaction for the different accounts.
 */
public abstract class Transaction {
  private final String description;
  private final float amount;
  private final ZonedDateTime creationDate;
  private static AtomicInteger nextId = new AtomicInteger();
  private final int id;

  /**
   * Creates a new Transaction.
   *
   * @param date The date, when the transaction was created.
   * @param amount The amount of money the transaction was about.
   * @param description A description the user can add to a transaction.
   */
  Transaction(final ZonedDateTime date, final float amount, final String description) {
    creationDate = (date != null ? date : ZonedDateTime.now(ZoneId.of("UTC")));
    id = nextId.incrementAndGet();
    this.description = description;
    this.amount = amount;
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
   * Getter for the amount.
   *
   * @return The float amount.
   */
  public float getAmount() {
    return amount;
  }

  /**
   * Getter for the creation date.
   *
   * @return The Date, when the transaction was created.
   */
  public ZonedDateTime getCreationDate() {
    return creationDate;
  }


  /**
   * Getter for the formatted creation date. Should only be used for displaying purposes.
   * Shows the stored creation time according to the timezone of the system that runs this method.
   *
   * @return The Date, when the transaction was created.
   */
  public String getFormattedCreationDate() {
    return creationDate.withZoneSameInstant(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'X"));
  }

  /**
   * Getter for the id.
   *
   * @return The integer id.
   */
  public int getID()  { return id; }

  /**
   * @return the simple name of this transaction
   */
  public final String getSimpleName() {
    return this.getClass().getSimpleName().toUpperCase();
  }
}



