package transactions.strategy;

import accounts.Account;
import transactions.Transaction;

/**
 * Stores the exact amount of money of the transaction on the account.
 *
 * @author Michael Watholowitsch
 */
public class SimpleDeposit implements BalanceChange {

  private final Account account;
  private final Transaction trans;

  /**
   * @param trans the transaction which's amount will be deducted
   * @param account the account which's balance will be changed
   */
  public SimpleDeposit(final Transaction trans, final Account account) {
    this.account = account;
    this.trans = trans;
  }

  @Override
  public void applyBalanceChange() {
    this.account.deposit(this.trans.getAmount());
  }
}
