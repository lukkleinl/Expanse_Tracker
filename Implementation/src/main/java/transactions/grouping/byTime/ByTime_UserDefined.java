package transactions.grouping.byTime;

import java.time.ZonedDateTime;
import java.util.Map;
import iteration.CustomContainer;
import transactions.Transaction;
import transactions.grouping.OrganizingRoot;
import transactions.grouping.TransactionOrganizing;

public class ByTime_UserDefined extends OrganizingRoot {
  private final ZonedDateTime begin;
  private final ZonedDateTime end;

  public ByTime_UserDefined(final TransactionOrganizing to, final ZonedDateTime begin,
      final ZonedDateTime end) {
    super(to);
    this.begin = begin;
    this.end = end;
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
