package accounts;

/**
 * @author Patrick Gmasz
 */
public class Cash extends Account {
  private final String currency;

  /**
   * Creates a new Cash account with a random generated account number and 0 balance.
   * 
   * @param accountName The name of the account
   * @param limit the limit of the account
   * @param currency The currency of the cash
   */
  public Cash(final String accountName, final float limit, final String currency) {
    super(accountName, limit);
    this.currency = currency;
  }

  /**
   * Creates a new Cash account (from database data).
   * 
   * @param accountName The name of the account
   * @param limit the limit of the account
   * @param currency The currency of the cash
   * @param ID the account id
   * @param balance the balance of the account
   */
  public Cash(final String accountName, final float limit, final String currency, final Integer ID,
      final float balance) {
    super(accountName, limit, ID, balance);
    this.currency = currency;
  }

  /**
   * Return the currency of the cash.
   *
   * @return the currency
   */
  public String getCurrency() {
    return this.currency;
  }

  @Override
  public String toString() {
    return "CASH";
  }
}
