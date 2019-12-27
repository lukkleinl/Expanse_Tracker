package transactions.grouping.byTime;

import java.time.ZonedDateTime;
import java.util.Map;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.Transaction;
import transactions.grouping.OrganizingRoot;
import transactions.grouping.TransactionOrganizing;

/**
 * Collects all transactions which's creation-time is within the specified timeframe but leaves their underlying mapping structure untouched.
 *
 * @author Michael Watholowitsch
 */
public class UserDefined extends OrganizingRoot {
  public static final String mappingborder = "CUD";
  private final ZonedDateTime begin;
  private final ZonedDateTime end;

  /**
   * Constructor
   *
   * @param wrappee the grouping which shall be decorated
   * @param begin the begin of the specified timeframe
   * @param end the end of the specified timeframe
   */
  public UserDefined(final TransactionOrganizing wrappee, final ZonedDateTime begin,
      final ZonedDateTime end) {
    super(wrappee);
    this.begin = begin;
    this.end = end;
  }

  @Override
  public void performOrganizing() {
    Map<String, CustomContainer<Transaction>> toDec = root.organize();
    CustomIterator<Transaction> iter;
    String groupedkey = null;

    for (String key : toDec.keySet()) {
      iter = toDec.get(key).getIterator();
      groupedkey = mappingborder + begin.getYear() + "-" + begin.getMonthValue() + "-" + begin.getDayOfMonth() + "-to-" + end.getYear() + "-" + end.getMonthValue() + "-" + end.getDayOfMonth() + mappingborder + "_" + key;
      grouped.putIfAbsent(groupedkey, new CustomList<>());

      while (iter.hasNext() && iter.element().getCreationDate().isAfter(begin) && iter.element().getCreationDate().isBefore(end)) {
        grouped.get(groupedkey).add(iter.next());
      }
    }
  }
}






