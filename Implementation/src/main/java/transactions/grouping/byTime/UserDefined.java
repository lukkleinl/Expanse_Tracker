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
 * Collects all transactions which's creation-time is within the specified timeframe but leaves
 * their underlying mapping structure untouched.
 *
 * @author Michael Watholowitsch
 */
public class UserDefined extends OrganizingRoot {
  public static final String mappingborder = "U";
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
  protected void performOrganizing() {
    Map<String, CustomContainer<Transaction>> toDec = this.root.organize();
    String newKey = "";

    for (String oldKey : toDec.keySet()) {
      for (CustomIterator<Transaction> iter = toDec.get(oldKey).getIterator(); iter.hasNext(); iter
          .next()) {
        newKey = keyCreation(iter.element(), oldKey);
        this.grouped.putIfAbsent(newKey, new CustomList<>());

        if (!this.grouped.get(newKey).contains(iter.element()) && withinTimeFrame(iter)) {
          this.grouped.get(newKey).add(iter.element());
        }
      }
    }
  }

  private boolean withinTimeFrame(final CustomIterator<Transaction> iter) {
    return iter.element().getCreationDate().isAfter(this.begin)
        && iter.element().getCreationDate().isBefore(this.end);
  }

  private String keyCreation(final Transaction trans, final String oldKey) {
    StringBuilder sb = new StringBuilder();
    sb.append(mappingborder);
    sb.append(this.begin.getYear() + "-");
    if (this.begin.getMonthValue() < 10) {
      sb.append("0");
    }
    sb.append(this.begin.getMonthValue() + "-");
    if (this.begin.getDayOfMonth() < 10) {
      sb.append("0");
    }
    sb.append(this.begin.getDayOfMonth());
    sb.append("-to-");
    sb.append(this.end.getYear() + "-");
    if (this.end.getMonthValue() < 10) {
      sb.append("0");
    }
    sb.append(this.end.getMonthValue() + "-");
    if (this.end.getDayOfMonth() < 10) {
      sb.append("0");
    }
    sb.append(this.end.getDayOfMonth());
    sb.append(mappingborder);
    sb.append("_" + oldKey);
    return sb.toString();
  }
}


