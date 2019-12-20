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
   * @param ID the ID of this transaction.
   */
  Payout(final ZonedDateTime date, final float amount, final String payoutCategory,
      final String description, final Integer ID) {
    super(date, amount, description, ID);
    this.payoutCategory = payoutCategory;
  }

  /**
   * Getter for the payout category
   *
   * @return the payout category
   */
  public String getPayoutCategory() {
    return this.payoutCategory;
  }

  @Override
  public String toString() {
    return getSimpleName() + ", Category=" + this.payoutCategory + super.toString();
  }

  /**
   * Convenience method for simpler checking.
   * 
   * @return the string PAYOUT
   */
  public static String getSimpleName() {
    return "PAYOUT";
  }

  @Override
  public String getCategory() {
    return this.payoutCategory;
  }
}


