package ui_tests.addTransactionPages;

import javax.swing.JFrame;
import ui.addTransactionPages.AddPayoutPage;

public class AddPayoutPageVIEW {
  public static void main(String args[]){
    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    AddPayoutPage page = new AddPayoutPage();
    page.configureFrame(frame);

  }
}
