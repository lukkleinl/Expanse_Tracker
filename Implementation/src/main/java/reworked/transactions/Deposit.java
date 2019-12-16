package reworked.transactions;

import java.util.Date;


/**
 * Class Deposit, which is a transaction and extends the class Transaction.
 */
public class Deposit extends Transaction {
  private final String depositCategory;

  /**
   * Creates a new Deposit object.
   *
   * @param creationDate Date, when the deposit was created.
   * @param amount Amount of the deposit
   * @param depositCategory the deposit category
   * @param description Description of the deposit
   */
  Deposit (final Date creationDate, final float amount, final String depositCategory, final String description) {
    super(creationDate, amount, description);
    this.depositCategory = depositCategory;
  }

  /**
   * Getter for the deposit category
   *
   * @return the deposit category
   */
  public String getDepositCategory() {
    return depositCategory;
  }

  @Override
  public String toString() {
    return this.getSimpleName();
  }
}




