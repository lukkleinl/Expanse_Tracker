package transactions;

import java.util.Date;

public class Deposit extends Transaction {
  private DepositCategory depositCategory;

  public Deposit(
      Date creationDate, float amount, DepositCategory depositCategory, String description) {
    super(creationDate, amount, description);
    this.depositCategory = depositCategory;
  }

  public DepositCategory getDepositCategory() {
    return depositCategory;
  }

  @Override
  public String toString()
  {
    return "DEPOSIT";
  }
}
