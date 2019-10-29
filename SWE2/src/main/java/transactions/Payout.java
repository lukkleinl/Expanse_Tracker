package transactions;

import java.util.Date;

public class Payout extends Transaction {
  private PayoutCategory payoutCategory;

  public Payout(
      Date creationDate, float amount, PayoutCategory payoutCategory, String description) {
    super(creationDate, amount, description);
    this.payoutCategory = payoutCategory;
  }

  public PayoutCategory getPayoutCategory() {
    return payoutCategory;
  }
}
