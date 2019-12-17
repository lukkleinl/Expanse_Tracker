package transactions.grouping;

import iteration.CustomContainer;
import transactions.Transaction;

public interface TransactionOrganizing {
  CustomContainer<Transaction> organize();
}
