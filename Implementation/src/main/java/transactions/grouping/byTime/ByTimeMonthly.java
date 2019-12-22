package transactions.grouping.byTime;

import java.time.ZonedDateTime;
import java.util.Map;
import iteration.CustomContainer;
import transactions.Transaction;
import transactions.grouping.OrganizingRoot;
import transactions.grouping.TransactionOrganizing;

public class ByTimeMonthly extends OrganizingRoot {

  public ByTimeMonthly(final TransactionOrganizing to) {
    super(to);
  }

  @Override
  public Map<String, CustomContainer<Transaction>> organize() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ZonedDateTime earliest() {
    return null;
  }
}
