package transactions.strategy;

import exceptions.SWE_Exception;

/**
 * Used to allow flexible ways of conducting balance changes on accounts.
 *
 * @author Michael Watholowitsch
 */
public interface BalanceChange {

  /**
   * Changes the balance depending on the implementing class.
   *
   * @throws SWE_Exception when conducting the change would cause the balance to go below its limit
   */
  void applyBalanceChange() throws SWE_Exception;
}
