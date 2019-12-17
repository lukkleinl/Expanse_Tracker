package transactions.strategy;

import accounts.Account;
import exceptions.SWE_Exception;
import transactions.Transaction;

public class SimpleDeposit implements BalanceChange {

  /**
   * Stores the specified amount of money on the specified account.
   *
   * @param trans the transaction which should be performed
   * @param account Account on which the transaction should be performed
   *
   * @throws LimitException if the resulting account-balance would be smaller than the limit
   */
  @Override
  public void applyBalanceChange(final Transaction trans, final Account account) throws SWE_Exception {
    account.deposit(trans.getAmount());
  }
}
