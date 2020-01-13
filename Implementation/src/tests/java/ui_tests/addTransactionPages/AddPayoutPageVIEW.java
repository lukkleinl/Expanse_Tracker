package ui_tests.addTransactionPages;

import gui.addTransactionPages.AddPayoutPage;
import javax.swing.JFrame;
import user.User;

public class AddPayoutPageVIEW {
  public static void main(String args[]){
    User testUser = new User("1", "paul", "kraft", "qwerty");
    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    AddPayoutPage page = new AddPayoutPage(testUser);
    page.configureFrame(frame);

  }
}
