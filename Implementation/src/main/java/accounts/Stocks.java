package accounts;

import java.util.Date;

/**
 * @author Patrick Gmasz
 */
public class Stocks extends Account {
  private final Date buyDate;

  /**
   * Creates a new Stock account with a random generated account number and 0 balance.
   *
   * @param accountName The name of the account
   * @param buyDate The date when the stocks were bought
   * @param limit the limit of the account
   */
  public Stocks(final String accountName, final Date buyDate, final float limit) {
    super(accountName, limit);
    this.buyDate = buyDate;
  }

  public Stocks(final String accountName, final Date buyDate, final float limit, final Integer ID,
      final float balance) {
    super(accountName, limit, ID, balance);
    this.buyDate = buyDate;
  }

  /**
   * Returns the buy date of the stocks.
   *
   * @return the buy date
   */
  public Date getBuyDate() {
    return this.buyDate;
  }

  @Override
  public String toString() {
    return "STOCKS";
  }
}
