package ui.jcomponent_creation.factories;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import ui.jcomponent_creation.LocalDefaultTableModel;
import ui.jcomponent_creation.factories.dimensions.AccountTableDim;
import ui.jcomponent_creation.factories.dimensions.JFrameDim;

public class AccountJComponentFactory extends JComponentFactory {

  AccountJComponentFactory() {}

  @Override
  public JTable createJTable(final LocalDefaultTableModel model) {
    final JTable tab = new JTable(model);

    // TODO - if coloring needed, override prepareRenderer of tab

    // TODO - modify mouselistener
    tab.addMouseListener(null);

    tab.setRowHeight(70);
    tab.setLayout(null);
    tab.getTableHeader().setReorderingAllowed(false);
    tab.getTableHeader().setResizingAllowed(false);
    tab.setOpaque(true);
    tab.doLayout();
    tab.repaint();
    tab.revalidate();
    tab.setVisible(true);

    return tab;
  }

  @Override
  public JFrame createJFrame(final JScrollPane pane) {
    final JFrame frame = new JFrame();

    frame.setLayout(null);

    // TODO - set bounds
    frame.setSize(JFrameDim.WIDTH.pixels(), JFrameDim.HEIGHT.pixels());

    if (pane != null) {
      frame.add(pane);
    }

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    return frame;
  }

  @Override
  public JScrollPane createJScrollPane(final JTable table) {
    JScrollPane jsp = (table != null ? new JScrollPane(table) : new JScrollPane());
    jsp.setBounds(AccountTableDim.getRectangle());
    jsp.setVisible(true);
    return jsp;
  }
}


