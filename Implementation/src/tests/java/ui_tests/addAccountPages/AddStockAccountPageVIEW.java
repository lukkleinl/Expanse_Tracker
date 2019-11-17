package ui_tests.addAccountPages;

import javax.swing.JFrame;
import ui.addAccountPages.AddStockAccountPage;

public class AddStockAccountPageVIEW {
  public static void main(String args[]) {

    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    AddStockAccountPage addp = new AddStockAccountPage();
    addp.configureFrame(frame);

  }
}
