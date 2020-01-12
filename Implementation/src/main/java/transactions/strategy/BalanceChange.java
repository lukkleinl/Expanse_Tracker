package transactions.strategy;

/**
 * Used to allow flexible ways of conducting balance changes on accounts.
 *
 * @author Michael Watholowitsch
 */
public interface BalanceChange {

  /**
   * Changes the balance depending on the implementing class.
   */
  void applyBalanceChange();
}
