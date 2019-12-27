package transactions.grouping.byTime;

import java.util.Map;
import iteration.CustomContainer;
import transactions.Transaction;
import transactions.grouping.OrganizingRoot;
import transactions.grouping.TransactionOrganizing;

public class Monthly extends OrganizingRoot {

  public Monthly(final TransactionOrganizing to) {
    super(to);
  }

  @Override
  public Map<String, CustomContainer<Transaction>> organize() {
    // TODO Auto-generated method stub
    return null;
  }
}
