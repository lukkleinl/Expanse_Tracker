package ui_tests.addTransactionPages;

import javax.swing.JFrame;
import ui.addTransactionPages.AddDepositPage;
import user.User;

public class AddDepositPageVIEW {
  public static void main(String args[]){
    User testUser = new User("1", "paul", "kraft", "qwerty");
    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    AddDepositPage page = new AddDepositPage(testUser);
    page.configureFrame(frame);

  }

}
