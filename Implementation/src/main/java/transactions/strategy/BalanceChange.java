package transactions.strategy;

import accounts.Account;
import exceptions.SWE_Exception;
import transactions.Transaction;

public interface BalanceChange {

  /**
   * Changes the balance of {@code account} depending on the amount of money in {@code transaction} and the implementing class.
   *
   * @param trans the transaction which should be performed
   * @param account Account on which the transaction should be performed
   */
  void applyBalanceChange(Transaction trans, Account account) throws SWE_Exception;
}
