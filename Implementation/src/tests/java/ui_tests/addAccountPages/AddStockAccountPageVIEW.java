package ui_tests.addAccountPages;

import javax.swing.JFrame;
import GUI.AddAccountPages.AddStockAccountPage;
import ui_tests.TestUser;

public class AddStockAccountPageVIEW {
  public static void main(String args[]) {

    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    AddStockAccountPage addp = new AddStockAccountPage(TestUser.getTestUser());
    addp.configureFrame(frame);

  }
}
