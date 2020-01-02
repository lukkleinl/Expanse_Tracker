package ui_tests.listPages;

import javax.swing.JFrame;
import ui.listPages.AccountTypePage;
import ui_tests.TestUser;

public class AccountTypePageVIEW {
  public static void main(String args[]){
    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    AccountTypePage page = new AccountTypePage(TestUser.getTestUser());
    page.configureFrame(frame);
  }

}
