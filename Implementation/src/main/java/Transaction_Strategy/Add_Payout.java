package Transaction_Strategy;

import accounts.Account;
import exceptions.LimitException;
import java.util.Date;
import java.util.Map;
import swe_IteratorPattern.CustomContainer;
import swe_IteratorPattern.CustomList;
import transactions.Payout;
import transactions.Transaction;

public class Add_Payout implements AddTransaction {

  /**
   * Deductes the specified amount of money from the specified account.
   *
   * @param Transaction the transaction which should be performed
   * @param transactions list of executed transactions
   * @param account Account on which the transaction should be performed
   *
   * @throws LimitException if the resulting account-balance would be smaller than the limit
   */

  @Override
  public void add(Object Transaction,
      Map<Integer, CustomContainer<Transaction>> transactions, Account account)
      throws LimitException {
    Date date = new Date();
    Payout payout =(Payout) Transaction;
    if (account.getBalance() - payout.getAmount()  < account.getLimit())
      throw new LimitException("Limit exceeded!");

    account.payout(payout.getAmount());

    transactions.putIfAbsent(account.getAccount_number(), new CustomList<>());
    transactions.get(account.getAccount_number()).add(payout);
  }
}
