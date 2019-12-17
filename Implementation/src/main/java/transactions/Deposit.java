package transactions;

/**
 * Class Deposit, which is a transaction and extends the class Transaction.
 */
public class Deposit extends Transaction {
  private final String depositCategory;

  /**
   * Creates a new Deposit object.
   *
   * @param amount Amount of the deposit
   * @param depositCategory the deposit category
   * @param description Description of the deposit
   */
  Deposit (final float amount, final String depositCategory, final String description) {
    super(amount, description);
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




