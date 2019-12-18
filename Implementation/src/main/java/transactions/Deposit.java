package transactions;

import java.time.ZonedDateTime;

/**
 * Class Deposit, which is a transaction and extends the class Transaction.
 */
public class Deposit extends Transaction {
  private final String depositCategory;

  /**
   * Creates a new Deposit object.
   *
   * @param date The date, when the transaction was created.
   * @param amount Amount of the deposit
   * @param depositCategory the deposit category
   * @param description Description of the deposit
   * @param ID the ID of this transaction.
   */
  Deposit(final ZonedDateTime date, final float amount, final String depositCategory, final String description, final Integer ID) {
    super(date,amount,description,ID);
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
    return getSimpleName() + ", Category=" + depositCategory + super.toString();
  }

  /**
   * Convenience method for simpler checking.
   * @return the string DEPOSIT
   */
  public static String getSimpleName() {
    return "DEPOSIT";
  }
}



