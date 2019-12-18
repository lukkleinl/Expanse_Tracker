package transactions.grouping.byTime;

import iteration.CustomContainer;
import transactions.Transaction;
import transactions.grouping.OrganizingRoot;
import transactions.grouping.TransactionOrganizing;

public class ByTimeMonthly extends OrganizingRoot {

  public ByTimeMonthly(final TransactionOrganizing to) {
    super(to);
  }

  @Override
  public CustomContainer<Transaction> organize() {
    // TODO Auto-generated method stub
    return null;
  }

}
