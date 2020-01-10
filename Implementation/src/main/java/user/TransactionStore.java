package user;

import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import java.util.HashMap;
import java.util.Map;
import transactions.Transaction;

public class TransactionStore {
  private final Map<Integer, CustomContainer<Transaction>> transactions;

  TransactionStore() {
    transactions = new HashMap<>();
  }

  public void addTransactionUnderKey(final Integer key, final Transaction transaction) {
    if (this.alreadyStored(transaction)) return;
    transactions.putIfAbsent(key,new CustomList<>());
    transactions.get(key).add(transaction);
  }

  public Map<Integer,CustomContainer<Transaction>> getTransactions() {
    return transactions;
  }

  public int accountsWithTransactions() {
    return transactions.keySet().size();
  }

  private boolean alreadyStored(final Transaction trans) {
    for (Integer key : transactions.keySet()) {
      CustomIterator<Transaction> iter = transactions.get(key).getIterator();
      while (iter.hasNext()) {
        if (iter.next().equals(trans)) return true;
      }
    }
    return false;
  }

  public void updateTransaction(int accountID, Transaction transaction) {
    CustomContainer<Transaction> transactionContainer = this.transactions.get(accountID);
    transactionContainer.update(transaction);
  }

  public void deleteTransaction(int accountID, Transaction transaction) {
    CustomContainer<Transaction> transactionContainer = this.transactions.get(accountID);
    transactionContainer.delete(transaction);
  }
}




