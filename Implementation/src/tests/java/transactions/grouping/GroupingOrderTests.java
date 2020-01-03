package transactions.grouping;

import java.time.ZonedDateTime;
import java.util.Map;
import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Transaction;
import user.User;

public class GroupingOrderTests {
  private static final int accounts = 2;
  private static final int transactions = 500;

  public static void main(final String[] args) throws Exception {
    GroupingBuilder gb = new GroupingBuilder();
    User user = GroupingTestUser.newTestUserWith(accounts);
    Map<String, CustomContainer<Transaction>> map = null;

    for (int i = 0; i < transactions; i++) {
      user.applyAndSaveTransaction(GroupingTestUser.newTransaction(i),
          GroupingTestUser.randomAccount());
    }
    // setup done

    try {
      // change grouping method here
      gb.allAccs(user);
      gb.yearly().category();

      // grouping
      map = gb.organize();

      // check if times are ordered
      checkIfTimesOrdered(map);

      // output, only if all times were correctly ordered by above method
      output(map);

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  private static void checkIfTimesOrdered(final Map<String, CustomContainer<Transaction>> map)
      throws Exception {
    ZonedDateTime earlier;
    for (String s : map.keySet()) {
      earlier = new GroupingTestUser.ChronologicTimes().earliest();

      for (CustomIterator<Transaction> it = map.get(s).getIterator(); it.hasNext(); it.next()) {
        if (earlier.isAfter(it.element().getCreationDate())) {
          throw new Exception("Date ordering failed!");
        }
        earlier = it.element().getCreationDate();
      }
    }
  }

  private static void output(final Map<String, CustomContainer<Transaction>> map) {
    StringBuilder sb = new StringBuilder();
    for (String key : map.keySet()) {
      sb.append("\n" + key + "\n");
      for (CustomIterator<Transaction> it = map.get(key).getIterator(); it.hasNext(); it.next()) {
        sb.append("Creation: " + it.element().getFormattedCreationDate() + ", ");
        sb.append("ID=" + it.element().getID() + ", ");
        sb.append("Category=" + it.element().getCategory() + "\n");
      }
    }
    System.out.println(sb.toString());
  }
}


