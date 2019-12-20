package transactions;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract class of a transaction for the different accounts.
 */
public abstract class Transaction {
  private static AtomicInteger nextId = new AtomicInteger();
  private final String description;
  private final float amount;
  private final ZonedDateTime creationDate;
  private final Integer ID;

  /**
   * Creates a new Transaction.
   *
   * @param date The date, when the transaction was created.
   * @param amount The amount of money the transaction was about.
   * @param description A description the user can add to a transaction.
   * @param ID the ID of this transaction.
   */
  Transaction(final ZonedDateTime date, final float amount, final String description,
      final Integer ID) {
    this.creationDate = (date != null ? date : ZonedDateTime.now(ZoneId.of("UTC")));
    this.ID = (ID != null ? ID : nextId.incrementAndGet());

    this.description = description;
    this.amount = amount;
  }

  /**
   * Getter for the description.
   *
   * @return The String description.
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Getter for the amount.
   *
   * @return The float amount.
   */
  public float getAmount() {
    return this.amount;
  }

  /**
   * Getter for the creation date.
   *
   * @return The Date, when the transaction was created.
   */
  public ZonedDateTime getCreationDate() {
    return this.creationDate;
  }


  /**
   * Getter for the formatted creation date. Should only be used for displaying purposes. Shows the
   * stored creation time according to the timezone of the system that runs this method.
   *
   * @return The Date, when the transaction was created.
   */
  public String getFormattedCreationDate() {
    return this.creationDate.withZoneSameInstant(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'X"));
  }

  /**
   * Getter for the ID.
   *
   * @return the ID
   */
  public int getID() {
    return this.ID;
  }

  /**
   * Getter for the category.
   * 
   * @return a string representation of the category
   */
  public abstract String getCategory();

  /** Override for comparing by the unique ID. */
  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof Transaction))
      return false;
    return ((Transaction) obj).ID == this.ID;
  }

  @Override
  public String toString() {
    return " ID=" + this.getID() + ", Creation=" + this.getFormattedCreationDate() + ", Amount="
        + this.getAmount() + ", Description=" + this.getDescription();
  }
}


