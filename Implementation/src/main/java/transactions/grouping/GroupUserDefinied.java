package transactions.grouping;

import java.time.ZonedDateTime;
import iteration.CustomContainer;
import transactions.Transaction;

public class GroupUserDefinied extends OrganizingRoot {
  private final ZonedDateTime begin;
  private final ZonedDateTime end;

  public GroupUserDefinied(final TransactionOrganizing to, final ZonedDateTime begin, final ZonedDateTime end) {
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
