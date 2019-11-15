package ui_tests.listPages;

import accounts.BankAccount;
import java.util.Date;
import javax.swing.JFrame;
import transactions.Deposit;
import transactions.DepositCategory;
import transactions.Payout;
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
          Deposit deposit =  new Deposit(new Date(), i % 3 + 1000, DepositCategory.SALARY, "desc" + i);
          testUser.addTransaction(deposit, testbankacc);
        } catch (Exception e) {
        }
      } else {
        try {
          Payout payout = new Payout(new Date(), i % 3, PayoutCategory.FOOD,"dec" + i);
          testUser.addTransaction(payout, testbankacc);
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
