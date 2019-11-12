package transactions;

import java.util.Date;


/**
 * Class Deposit, which is a transaction and extends the class Transaction.
 *
 * @author Patrick Gmasz
 */
public class Deposit extends Transaction {
  private DepositCategory depositCategory;

  /**
   * Creates a new Depost object.
   *
   * @param creationDate Date, when the deposit was created.
   * @param amount Amount of the deposit
   * @param depositCategory Enum of the deposit category
   * @param description Description of the deposit
   */
  public Deposit(
      Date creationDate, float amount, DepositCategory depositCategory, String description) {
    super(creationDate, amount, description);
    this.depositCategory = depositCategory;
  }

  /**
   * Getter for the deposit category
   *
   * @return The enum of the deposit category
   */
  public DepositCategory getDepositCategory() {
    return depositCategory;
  }

  /**
   * toString method for the Deposit class
   *
   * @return The string "DEPOSIT"
   */
  @Override
  public String toString()
  {
    return "DEPOSIT";
  }
}
