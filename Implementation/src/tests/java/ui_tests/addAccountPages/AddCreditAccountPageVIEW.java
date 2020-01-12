package ui_tests.addAccountPages;

import javax.swing.JFrame;
import GUI.AddAccountPages.AddCreditAccountPage;
import ui_tests.TestUser;

public class AddCreditAccountPageVIEW {
  public static void main(String args[]) {

    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    AddCreditAccountPage addp = new AddCreditAccountPage(TestUser.getTestUser());
    addp.configureFrame(frame);

  }

}
