package accounts;

import java.util.Date;

/**
 * @author Patrick Gmasz
 */
public class Stocks extends Account {
  private Date buyDate;

  /**
   * Creates a new Stock account with a random generated account number and 0 balance.
   *
   * @param accountName The name of the account
   * @param buyDate The date when the stocks were bought
   */
  public Stocks(String accountName, Date buyDate) {
    super(accountName);
    this.buyDate = buyDate;
  }

  /**
   * Returns the buy date of the stocks.
   *
   * @return the buy date
   */
  public Date getBuyDate() {
    return buyDate;
  }
}
