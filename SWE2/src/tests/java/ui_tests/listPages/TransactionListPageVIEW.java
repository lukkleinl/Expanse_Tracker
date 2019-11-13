package ui_tests.listPages;

import accounts.BankAccount;
import javax.swing.JFrame;
import transactions.DepositCategory;
import transactions.PayoutCategory;
import ui.listPages.TransactionListPage;
import user.User;

public class TransactionListPageVIEW {
  public static void main(String args[]) {

    //TEST DATA

    BankAccount testbankacc = new BankAccount("pauls acc", "BAWAG", 1000);
    User testUser = new User(1, "paul", "kraft", "qwerty");

    for (int i = 0; i < 100; i++) {
      if (i < 50) {
        try {
          testUser.deposit(DepositCategory.SALARY, i % 3 + 1000, "desc" + i, testbankacc);
        } catch (Exception e) {
        }
      } else {
        try {
          testUser.payOut(PayoutCategory.FOOD, i % 3, "desc" + i, testbankacc);
        } catch (Exception e) {
        }
      }
    }
    //TEST DATA END

    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    TransactionListPage page = new TransactionListPage(testbankacc, testUser);
    page.configureFrame(frame);
    frame.setVisible(true);

  }

}
