package ui_tests.listPages;

import accounts.Account;import javax.swing.JFrame;
import swe_IteratorPattern.CustomContainer;
import swe_IteratorPattern.CustomIterator;
import ui.listPages.AccountListPage;
import ui_tests.TestUser;
import user.User;

public class AccountListPageVIEW {
  public static void main(String args[]){
    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    User user = TestUser.getTestUser();
    CustomContainer<Account> accounts = user.getAccounts();
    CustomIterator<Account> it = accounts.getIterator();
    Account acc = it.next();
    AccountListPage page = new AccountListPage(user);
    frame.setVisible(true);
    page.configureFrame(frame);

  }
}
