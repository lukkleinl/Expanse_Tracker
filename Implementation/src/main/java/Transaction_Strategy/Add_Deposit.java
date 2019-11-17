package Transaction_Strategy;

import accounts.Account;
import java.util.Date;
import java.util.Map;
import swe_IteratorPattern.CustomContainer;
import swe_IteratorPattern.CustomList;
import transactions.Deposit;
import transactions.Transaction;

public class Add_Deposit implements AddTransaction {


  /**
   * Adds the specified amount of money to the specified account.
   *
   * @param Transaction the transaction which should be performed
   * @param transactions list of executed transactions
   * @param account Account on which the transaction should be performed
   */

  @Override
  public void add(Object Transaction,
      Map<Integer, CustomContainer<Transaction>> transactions, Account account) {
    Date date = new Date();
    Deposit deposit=(Deposit) Transaction;
    account.deposit(deposit.getAmount());

    transactions.putIfAbsent(account.getAccount_number(), new CustomList<>());
    transactions.get(account.getAccount_number()).add(deposit);
  }
}
