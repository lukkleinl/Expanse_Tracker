package ui.graphicalRepresentation;

import iteration.CustomContainer;
import iteration.CustomIterator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.experimental.categories.Categories;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.categories.DepositCategory;
import transactions.categories.PayoutCategory;
import user.User;

import java.util.Map;
import java.util.Set;

public class GraphicalRepresentation_Bar {

    User user;

   CustomContainer<Transaction> listOfTransactions;


    public void draw() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        double specificCategory_Payout = 0;
        double specificCategory_Deposit = 0;

        for(CustomIterator it = listOfTransactions.getIterator();it.hasNext();it.next()) {


            if(it.element() instanceof Payout)
                specificCategory_Payout+=((Payout) it.element()).getAmount();
            else
                specificCategory_Deposit+=((Deposit)it.element()).getAmount();


            dataset.addValue(specificCategory_Deposit,((Deposit)it.element()).getCategory(),"Deposit");
            dataset.addValue(-specificCategory_Payout,((Payout)it.element()).getCategory(),"Payout");
            

        }


    }
}
