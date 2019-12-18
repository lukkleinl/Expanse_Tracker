package transactions;

import java.time.ZonedDateTime;

/**
 * Class Payout, which is a transaction and extends the class Transaction.
 */
public class Payout extends Transaction {
  private final String payoutCategory;

  /**
   * Creates a new Payout object.
   *
   * @param date The date, when the transaction was created.
   * @param amount Amount of the payout
   * @param payoutCategory the payout category
   * @param description Description of the payout
   */
  Payout(final ZonedDateTime date, final float amount, final String payoutCategory, final String description) {
    super(date,amount,description);
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
