package reworked.transactions.strategy;

import accounts.Account;
import exceptions.LimitException;
import reworked.transactions.Transaction;

public class SimplePayout implements BalanceChange {

  /**
   * Deductes the specified amount of money from the specified account.
   *
   * @param trans the transaction which should be performed
   * @param account Account on which the transaction should be performed
   *
   * @throws LimitException if the resulting account-balance would be smaller than the limit
   */
  @Override
  public void applyBalanceChange(final Transaction trans, final Account account) throws LimitException {
    account.payout(trans.getAmount());
  }
}
