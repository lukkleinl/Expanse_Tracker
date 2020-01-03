package ui.graphicalRepresentation;

import iteration.CustomContainer;
import iteration.CustomIterator;
import transactions.Transaction;
import transactions.grouping.GroupingBuilder;
import ui.TestUser;
import user.User;

import java.util.Map;

public class Testing_Grouping {

    public static void main(String[] args) {

        User user = TestUser.getTestUser();

        Map<String, CustomContainer<Transaction>> orga =
                new GroupingBuilder().allAccs(user).daily().organize();

        try {

            GraphicalRepresentation_Bar bar = new GraphicalRepresentation_Bar(user);
            bar.draw("2020","01","03");

            GraphicalRepresentation_Pie pie = new GraphicalRepresentation_Pie(user);
            pie.draw("2020","01","03");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

            System.out.println("Map size" + orga.size() + " Entry set:" + orga.entrySet().size());


            for (String key : orga.keySet()) {

                System.out.println(key);
                for (CustomIterator<Transaction> it = orga.get(key).getIterator(); it.hasNext(); ((CustomIterator) it).next()) {
                    System.out.println(it.element());
                }

            }


        }

}