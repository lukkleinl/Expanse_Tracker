package reworked.transactions.grouping;

import reworked.transactions.Transaction;
import swe_IteratorPattern.CustomContainer;

public class GroupUserDefinied extends OrganizingRoot {
  private final String begin;
  private final String end;

  public GroupUserDefinied(final TransactionOrganizing to, final String begin, final String end) {
    super(to);
    this.begin = begin;
    this.end = end;
  }

  @Override
  public CustomContainer<Transaction> organize() {
    // TODO Auto-generated method stub
    return null;
  }

}
