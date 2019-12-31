package ui_tests.listPages;

import javax.swing.JFrame;
import accounts.BankAccount;
import transactions.Transaction;
import transactions.TransactionCreator;
import ui.listPages.TransactionListPage;
import user.User;

public class TransactionListPageVIEW {
  public static void main(final String args[]) {

    //TEST DATA

    BankAccount testbankacc = new BankAccount("pauls acc", "BAWAG", 1000);
    User testUser = new User(1, "paul", "kraft", "qwerty");

    for (int i = 0; i < 100; i++) {
      if (i < 50) {
        try {
          Transaction deposit = TransactionCreator.newTransaction("SALARY", i % 3 + 1000, "desc" + i, testUser.getCategoryStore());
          testUser.applyAndSaveTransaction(deposit, testbankacc);
        } catch (Exception e) {
        }
      } else {
        try {
          Transaction payout = TransactionCreator.newTransaction("FOOD", i % 3 + 1000, "dec" + i, testUser.getCategoryStore());
          testUser.applyAndSaveTransaction(payout, testbankacc);
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
