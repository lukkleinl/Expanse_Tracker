package Transaction_Strategy;

import java.util.Map;
import accounts.Account;
import exceptions.LimitException;
import swe_IteratorPattern.CustomContainer;
import transactions.Transaction;

public interface AddTransaction {

  /**
   * Performs either a Deposit or a Payout
   *
   * @param Transaction the transaction which should be performed
   * @param transactions2 list of executed transactions2
   * @param account Account on which the transaction should be performed
   */
  void  add(Object Transaction,
      Map<Integer, CustomContainer<Transaction>> transactions, Account account)
          throws LimitException;

}
