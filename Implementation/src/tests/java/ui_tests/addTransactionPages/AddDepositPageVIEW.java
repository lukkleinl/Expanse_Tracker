package ui_tests.addTransactionPages;

import javax.swing.JFrame;
import ui.addTransactionPages.AddDepositPage;

public class AddDepositPageVIEW {
  public static void main(String args[]){
    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    AddDepositPage page = new AddDepositPage();
    page.configureFrame(frame);

  }

}
