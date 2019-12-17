package reworked.user;

import java.util.HashMap;
import java.util.Map;
import reworked.transactions.Transaction;
import swe_IteratorPattern.CustomContainer;
import swe_IteratorPattern.CustomList;

public class TransactionStore {
  private final Map<Integer, CustomContainer<Transaction>> transactions;

  TransactionStore() {
    transactions = new HashMap<>();
  }

  public void addTransactionUnderKey(final Integer key, final Transaction transaction) {
    transactions.putIfAbsent(key,new CustomList<>());
    transactions.get(key).add(transaction);
  }

  public Map<Integer,CustomContainer<Transaction>> getTransactions() {
    return transactions;
  }

  public int accountsWithTransactions() {
    return transactions.keySet().size();
  }
}
