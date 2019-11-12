package transactions;

import java.util.Date;

/**
 * Class Payout, which is a transaction and extends the class Transaction.
 *
 * @author Patrick Gmasz
 */
public class Payout extends Transaction {
  private PayoutCategory payoutCategory;

  /**
   * Creates a new Payout object.
   *
   * @param creationDate Date, when the payout was created.
   * @param amount Amount of the payout
   * @param payoutCategory Enum of the payout category
   * @param description Description of the payout
   */
  public Payout(
      Date creationDate, float amount, PayoutCategory payoutCategory, String description) {
    super(creationDate, amount, description);
    this.payoutCategory = payoutCategory;
  }

  /**
   * Getter for the payout category
   *
   * @return The enum of the payout category
   */
  public PayoutCategory getPayoutCategory() {
    return payoutCategory;
  }

  /**
   * toString method for the Payout class
   *
   * @return The string "PAYOUT"
   */
  @Override
  public String toString()
  {
    return "PAYOUT";
  }
}
