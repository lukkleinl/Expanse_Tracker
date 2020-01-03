package ui.listPages;

import ui.main.AbstractPage;
import user.User;

import javax.swing.*;

public class SummaryPage extends AbstractPage {
  private JTable transactionTable;
  private JScrollPane scrollPane;

  private volatile boolean backWanted;

  private User user;

  private String periodStart;
  private String periodEnd;

  public SummaryPage(User user, String periodStart, String periodEnd) {
    this.user = user;

    createComponents();
  }

  @Override
  protected void resetTitle(JFrame frame) {
    frame.setTitle("Summary - Page");
  }

  @Override
  protected void createComponents() {}
}
