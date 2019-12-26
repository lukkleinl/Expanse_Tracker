package transactions.grouping.byTime;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import iteration.CustomContainer;
import transactions.Transaction;
import transactions.grouping.OrganizingRoot;
import transactions.grouping.TransactionOrganizing;

public class Yearly extends OrganizingRoot {

  public Yearly(final TransactionOrganizing to) {
    super(to);
  }

  @Override
  public Map<String, CustomContainer<Transaction>> organize() {
    Map<String, CustomContainer<Transaction>> fromNested = this.decFilter.organize();
    int earliestYear = this.decFilter.earliest().getYear();

    for (String key : fromNested.keySet()) {
      // TODO
    }

    return this.grouped;
  }

  @Override
  public ZonedDateTime earliest() {
    int earliestYear = this.decFilter.earliest().getYear();
    return ZonedDateTime.of(earliestYear, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));
  }
}


