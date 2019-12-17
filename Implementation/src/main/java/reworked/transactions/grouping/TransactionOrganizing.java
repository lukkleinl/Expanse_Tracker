package reworked.transactions.grouping;

import reworked.transactions.Transaction;
import swe_IteratorPattern.CustomContainer;

public interface TransactionOrganizing {
  CustomContainer<Transaction> organize();
}
