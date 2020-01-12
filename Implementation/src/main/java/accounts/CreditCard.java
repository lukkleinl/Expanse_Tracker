package accounts;

import java.util.Date;

/**
 * @author Patrick Gmasz
 */
public class CreditCard extends BankAccount {
  private Date expiryDate;

  /**
   * Creates a new Credit Card with a random generated account number and 0 balance.
   *
   * @param accountName The name of the account
   * @param bankName The name of the bank
   * @param limit The limit for overdrawing the bank account
   * @param expiryDate The expiry date of the credit card.
   */
  public CreditCard(String accountName, String bankName, float limit, Date expiryDate) {
    super(accountName, bankName, limit);
    this.expiryDate = expiryDate;
  }

  public CreditCard(String accountName, String bankName, float limit, Date expiryDate,final Integer ID,final float balance) {
    super(accountName, bankName, limit,ID,balance);
    this.expiryDate = expiryDate;
  }

  /**
   * Returns the expiry date of the credit card.
   *
   * @return the expiry date
   */
  public Date getExpiryDate() {
    return expiryDate;

   /* DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String formattedString = expiryDate.format(formatter);
    return formattedString;*/
  }

  @Override
  public String toString()
  {
    return "CREDITCARD";
  }
}
