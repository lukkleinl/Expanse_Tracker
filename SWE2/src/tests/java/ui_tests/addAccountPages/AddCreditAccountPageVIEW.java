package ui_tests.addAccountPages;

import javax.swing.JFrame;
import ui.addAccountPages.AddCreditAccountPage;

public class AddCreditAccountPageVIEW {
  public static void main(String args[]) {

    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    AddCreditAccountPage addp = new AddCreditAccountPage();
    addp.configureFrame(frame);

  }

}