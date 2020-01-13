package ui_tests.addAccountPages;

import gui.addAccountPages.AddDebitAccountPage;
import javax.swing.JFrame;
import ui_tests.TestUser;

public class AddDebitAccountPageVIEW {
  public static void main(String args[]) {

    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    AddDebitAccountPage addp = new AddDebitAccountPage(TestUser.getTestUser());
    addp.configureFrame(frame);

  }

}
