package transactions.strategy;

import accounts.Account;
import exceptions.SWE_Exception;
import transactions.Transaction;

/**
 * Deducts the exact amount of money of the transaction on the account.
 *
 * @author Michael Watholowitsch
 */
public class SimplePayout implements BalanceChange {

  private final Account account;
  private final Transaction trans;

  /**
   * @param trans the transaction which's amount will be deducted
   * @param account the account which's balance will be changed
   */
  public SimplePayout(final Transaction trans, final Account account) {
    this.account = account;
    this.trans = trans;
  }

  @Override
  public void applyBalanceChange() throws SWE_Exception {
    account.payout(trans.getAmount());
  }
}
