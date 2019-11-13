package ui_tests.addAccountPages;

import javax.swing.JFrame;
import ui.addAccountPages.AddDebitAccountPage;

public class AddDebitAccountPageVIEW {
  public static void main(String args[]) {

    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    AddDebitAccountPage addp = new AddDebitAccountPage();
    addp.configureFrame(frame);

  }

}
