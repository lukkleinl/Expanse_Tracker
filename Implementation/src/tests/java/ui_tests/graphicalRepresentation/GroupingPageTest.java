package ui_tests.graphicalRepresentation;

import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Transaction;
import transactions.grouping.GroupingBuilder;
import GUI.Main.TestUser;
import GUI.GraphicalPages.GraphicalRepresentation_Bar;
import GUI.GraphicalPages.GraphicalRepresentation_Pie;
import user.User;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

public class GroupingPageTest {
    public static void main(String[] args) {

        String selectedDate = "2020/01/03";
        String selectedDate_e = "2020/01/03";

    /*
            System.out.println("Substr 0,4" + s.substring(0,4));
            System.out.println("Substr 5,6" + s.substring(5,7));
            System.out.println("Substr 8,9" + s.substring(8,10));
    */

        User user = TestUser.getTestUser();

        Map<String, CustomContainer<Transaction>> orga =
                new GroupingBuilder()
                        .allAccs(user)
                        .category()
                        .daily()
                        .userdefined(
                                ZonedDateTime.of(
                                        Integer.valueOf(selectedDate.substring(0, 4)),
                                        Integer.valueOf(selectedDate.substring(5, 7)),
                                        Integer.valueOf(selectedDate.substring(8, 10)),
                                        0,
                                        0,
                                        0,
                                        0,
                                        ZoneId.of("UTC")),
                                ZonedDateTime.of(
                                        Integer.valueOf(selectedDate.substring(0, 4)),
                                        Integer.valueOf(selectedDate.substring(5, 7)),
                                        Integer.valueOf(selectedDate.substring(8, 10)),
                                        0,
                                        0,
                                        0,
                                        0,
                                        ZoneId.of("UTC"))
                                        .plusHours(24))
                        .organize();

        try {

            GraphicalRepresentation_Bar bar = new GraphicalRepresentation_Bar(user);
            bar.draw("2020", "01", "03");

            GraphicalRepresentation_Pie pie = new GraphicalRepresentation_Pie(user);
            pie.draw("2020", "01", "03");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Map size" + orga.size() + " Entry set:" + orga.entrySet().size());

        for (String key : orga.keySet()) {

            System.out.println(key);
            for (CustomIterator<Transaction> it = orga.get(key).getIterator();
                 it.hasNext();
                 ((CustomIterator) it).next()) {
                System.out.println(it.element());
            }
        }
    }
}
