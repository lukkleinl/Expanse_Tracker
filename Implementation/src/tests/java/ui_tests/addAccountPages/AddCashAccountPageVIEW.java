package ui_tests.addAccountPages;

import javax.swing.JFrame;
import GUI.AddAccountPages.AddCashAccountPage;
import ui_tests.TestUser;

public class AddCashAccountPageVIEW {
  public static void main(String args[]) {

    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    AddCashAccountPage addp = new AddCashAccountPage(TestUser.getTestUser());
    addp.configureFrame(frame);

  }

}
