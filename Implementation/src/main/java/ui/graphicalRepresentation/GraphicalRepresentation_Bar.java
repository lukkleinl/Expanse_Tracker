package ui.graphicalRepresentation;

import iteration.CustomContainer;
import iteration.CustomIterator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.experimental.categories.Categories;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.categories.DepositCategory;
import transactions.categories.PayoutCategory;
import transactions.grouping.GroupingBuilder;
import user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphicalRepresentation_Bar {

    User user;
   CustomContainer<Transaction> listOfTransactions;

    public GraphicalRepresentation_Bar(User user) {
        this.user = user;
        this.listOfTransactions = listOfTransactions;
    }


    public void draw(CustomContainer<Transaction> listOfTransactions) {

        this.listOfTransactions = listOfTransactions;

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Set<String> payoutCategorys = user.getCategories(new PayoutCategory());

        Map<String,Float> combinedPayoutsOfCategorys = new HashMap<>();


        for(CustomIterator it = listOfTransactions.getIterator();it.hasNext();it.next()) {



            if(it.element() instanceof Payout){

                String category = ((Payout)it.element()).getCategory();

               if(combinedPayoutsOfCategorys.containsKey(category)){
                    combinedPayoutsOfCategorys.put(category,
                 combinedPayoutsOfCategorys.get(category) + ((Payout) it.element()).getAmount());
                }
                else
                   combinedPayoutsOfCategorys.put(category,((Payout) it.element()).getAmount());

            }

            else {

                String category = ((Deposit)it.element()).getCategory();

                if(combinedPayoutsOfCategorys.containsKey(category)){
                    combinedPayoutsOfCategorys.put(category,
                            combinedPayoutsOfCategorys.get(category) + ((Deposit) it.element()).getAmount());
                }
                else
                    combinedPayoutsOfCategorys.put(category,((Deposit) it.element()).getAmount());


            }

            for(String category : combinedPayoutsOfCategorys.keySet()) {
                dataset.addValue(combinedPayoutsOfCategorys.get(category),category,(payoutCategorys.contains(category)) ? "Payout" : "Deposit");
            }

        }

        JFreeChart chart = ChartFactory.createBarChart("Test Chart",
                "Money",
                "Payout/Deposit",
                dataset
        );



        ChartFrame frame = new ChartFrame("Test Bar Chart",chart);
        frame.setSize(1000,500);
        frame.setVisible(true);


    }

    // needs Month as '01' .. '02' ..
    public  CustomContainer<Transaction> getMonthly(int year, int month) throws Exception{

        if(month>12)
            throw new IndexOutOfBoundsException("That is not a valid Month!");

        Map<String, CustomContainer<Transaction>> orga =
                new GroupingBuilder().allAccs(user).monthly().organize();


        for(String key : orga.keySet()) {
            if (key.contains(""+year) && key.contains(month + "M"))
                return orga.get(key);
        }

        throw new NoSuchFieldException("Given Month in Given Year not present");
    }
    public CustomContainer<Transaction> getYearly(int year) throws Exception{

        if(year<=0)
            throw new IndexOutOfBoundsException("No Banks before Jesus!");

        Map<String, CustomContainer<Transaction>> orga =
                new GroupingBuilder().allAccs(user).monthly().organize();


        for(String key : orga.keySet()) {
            if (key.contains(""+year))
                return orga.get(key);
        }

        throw new NoSuchFieldException("Given Year not present");
    }

}
