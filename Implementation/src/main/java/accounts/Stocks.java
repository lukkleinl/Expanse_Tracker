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
  public Stocks(String accountName, Date buyDate,float limit) {
    super(accountName,limit);
    this.buyDate = buyDate;
  }

  public Stocks(String accountName, Date buyDate,float limit,final Integer ID,final float balance) {
    super(accountName,limit,ID,balance);
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

  @Override
  public String toString()
  {
    return "STOCKS";
  }
}
