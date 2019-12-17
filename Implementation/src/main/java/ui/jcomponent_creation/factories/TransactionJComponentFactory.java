package ui.jcomponent_creation.factories;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import ui.jcomponent_creation.LocalDefaultTableModel;
import ui.jcomponent_creation.factories.dimensions.JFrameDim;
import ui.jcomponent_creation.factories.dimensions.TransactionTableDim;

@SuppressWarnings("serial")
public class TransactionJComponentFactory extends JComponentFactory {

  TransactionJComponentFactory() { }

  @Override
  public JTable createJTable(final LocalDefaultTableModel model) {
    final JTable tab = new JTable(model) {
      @Override
      public Component prepareRenderer(final TableCellRenderer renderer, final int row, final int column) {
        Component comp = super.prepareRenderer(renderer, row, column);

        if (!this.isRowSelected(row)) {
          // keeps the original colors
          comp.setBackground(this.getBackground());
          comp.setForeground(this.getForeground());
          comp.setFont(this.getFont());

          int modelRow = this.convertRowIndexToModel(row);
          String type = (String) this.getModel().getValueAt(modelRow, 0);

          // TODO - maybe different mechanism for choosing
          if (type.equalsIgnoreCase("PAYOUT")) {
            comp.setBackground(Color.RED);
            comp.setForeground(Color.BLACK);
          }
          else if (type.equalsIgnoreCase("DEPOSIT")) {
            comp.setBackground(Color.GREEN);
            comp.setForeground(Color.BLACK);
          }
        }

        return comp;
      }
    };

    // TODO - modify mouselistener
    tab.addMouseListener(null);

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
    frame.setSize(JFrameDim.getDimension());

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
    jsp.setBounds(TransactionTableDim.getRectangle());
    jsp.setVisible(true);
    return jsp;
  }
}






