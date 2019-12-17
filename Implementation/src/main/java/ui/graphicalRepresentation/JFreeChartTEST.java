package ui.graphicalRepresentation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class JFreeChartTEST {

    private static void pie() {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("One", new Integer(10));
        pieDataset.setValue("Two", new Integer(20));
        pieDataset.setValue("Three", new Integer(30));
        pieDataset.setValue("Four", new Integer(40));
        pieDataset.setValue("Five", new Integer(50));

        JFreeChart chart = ChartFactory.createPieChart("Test Chart", pieDataset);
        ChartFrame frame = new ChartFrame("Test Pie Chart",chart);
        frame.setSize(450,500);
        frame.setVisible(true);
    }

    private static void bar() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Population in 2005
        dataset.addValue(10, "USA", "2005");
        dataset.addValue(15, "India", "2005");
        dataset.addValue(20, "China", "2005");

        // Population in 2010
        dataset.addValue(15, "USA", "2010");
        dataset.addValue(20, "India", "2010");
        dataset.addValue(25, "China", "2010");

        // Population in 2015
        dataset.addValue(20, "USA", "2015");
        dataset.addValue(25, "India", "2015");
        dataset.addValue(30, "China", "2015");

        JFreeChart chart = ChartFactory.createBarChart("Test Chart",
                "Year",
                "Population in Million",
                dataset
        );
        ChartFrame frame = new ChartFrame("Test Bar Chart",chart);
        frame.setSize(450,500);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        pie();
        bar();
    }

}
