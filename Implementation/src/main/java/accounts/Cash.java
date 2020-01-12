package accounts;

/**
 * @author Patrick Gmasz
 */
public class Cash extends Account {
  private String currency;

  /**
   * Creates a new Cash account with a random generated account number and 0 balance.
   *  @param accountName The name of the account
   * @param limit
   * @param currency The currency of the cash
   */
  public Cash(String accountName, float limit,String currency) {
    super(accountName,limit);
    this.currency = currency;
  }

  /**
   * Creates a new Cash account with a random generated account number and 0 balance.
   *  @param accountName The name of the account
   * @param limit
   * @param currency The currency of the cash
   */
  public Cash(String accountName, float limit,String currency,final Integer ID,final float balance) {
    super(accountName,limit,ID,balance);
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

  @Override
  public String toString()
  {
    return "CASH";
  }
}
