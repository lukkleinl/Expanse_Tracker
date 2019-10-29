package accounts;

/**
 * @author Patrick Gmasz
 */
public class Cash extends Account {
  private String currency;

  /**
   * Creates a new Cash account with a random generated account number and 0 balance.
   *
   * @param accountName The name of the account
   * @param currency The currency of the cash
   */
  private Cash(String accountName, String currency) {
    super(accountName);
    this.currency = currency;
  }

  /**
   * Return the currency of the cash.
   *
   * @return the currency
   */
  public String getCurrency() {
    return currency;
  }
}
