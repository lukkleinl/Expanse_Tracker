package ui_tests.graphicalRepresentation;

import gui.graphical_pages.GraphicalRepresentation_Bar;
import gui.graphical_pages.GraphicalRepresentation_Pie;
import gui.main.TestUser;
import iteration.CustomContainer;
import iteration.CustomIterator;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import mongoDB.WriteOperation;
import patterns.observing.Database;
import transactions.Transaction;
import transactions.grouping.GroupingBuilder;
import user.User;

public class GroupingPageTest {
    public static void main(String[] args) throws Exception {

        String selectedDate = "2020/01/03";
        String selectedDate_e = "2020/01/03";

    /*
            System.out.println("Substr 0,4" + s.substring(0,4));
            System.out.println("Substr 5,6" + s.substring(5,7));
            System.out.println("Substr 8,9" + s.substring(8,10));
    */
        Database database=new Database();
        User user = TestUser.getTestUser("sv","dav","v","av",database);

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

        WriteOperation write_operation=new WriteOperation();
        write_operation.clearDatabase();
    }
}
