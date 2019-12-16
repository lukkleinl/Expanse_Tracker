package reworked.transactions;

import java.util.Date;

/**
 * Class Payout, which is a transaction and extends the class Transaction.
 */
public class Payout extends Transaction {
  private final String payoutCategory;

  /**
   * Creates a new Payout object.
   *
   * @param creationDate Date, when the payout was created.
   * @param amount Amount of the payout
   * @param payoutCategory the payout category
   * @param description Description of the payout
   */
  Payout(final Date creationDate, final float amount, final String payoutCategory, final String description) {
    super(creationDate, amount, description);
    this.payoutCategory = payoutCategory;
  }

  /**
   * Getter for the payout category
   *
   * @return the payout category
   */
  public String getPayoutCategory() {
    return payoutCategory;
  }

  @Override
  public String toString() {
    return this.getSimpleName();
  }
}
