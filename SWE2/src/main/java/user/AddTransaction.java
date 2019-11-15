package user;

import accounts.Account;
import exceptions.LimitException;
import java.util.Map;
import swe_IteratorPattern.CustomContainer;
import transactions.Transaction;

public interface AddTransaction {

  /**
   * Performs either a Deposit or a Payout
   *
   * @param Transaction the transaction which should be performed
   * @param transactions list of executed transactions
   * @param account Account on which the transaction should be performed
   */
  void  add(Object Transaction,
      Map<Integer, CustomContainer<Transaction>> transactions, Account account)
      throws LimitException;

}
