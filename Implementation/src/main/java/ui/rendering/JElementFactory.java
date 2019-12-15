package ui.rendering;

import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import transactions.Deposit;
import transactions.Payout;
import ui.rendering.renderers.TableColors;

// class created with https://stackoverflow.com/a/8372821
@SuppressWarnings("serial")
public class JElementFactory {
  private final DefaultTableModel dtm;
  private final int FRAME_WIDTH = 800;
  private final int FRAME_HEIGHT = 600;
  private final int SCROLL_PANE_INDENT = 50;

  @SuppressWarnings({"unchecked","rawtypes"})
  public JElementFactory(final String[] columnNames) {
    dtm = new DefaultTableModel(columnNames, 0) {
      @Override
      public Class getColumnClass(final int column) {
        return String.class;
      }
      @Override
      public boolean isCellEditable(final int row, final int column) {
        return false;
      }
    };
  }

  public void addRow(final String[] row) {
    dtm.addRow(row);
  }

  public JFrame createJFrame(final JScrollPane pane) {
    JFrame frame = new JFrame();

    frame.setLayout(null);
    frame.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

    if (pane != null) {
      frame.add(pane);
    }

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    return frame;
  }

  public JScrollPane createJScrollPane(final JTable table) {
    JScrollPane jsp = (table != null ? new JScrollPane(table) : new JScrollPane());

    // TODO

    jsp.setBounds(SCROLL_PANE_INDENT, SCROLL_PANE_INDENT, FRAME_WIDTH - 2*SCROLL_PANE_INDENT, FRAME_HEIGHT - 3*SCROLL_PANE_INDENT);
    jsp.setVisible(true);

    return jsp;
  }

  public JTable createJTable(final TableColors defaultrenderer, final TableColors posRenderer, final TableColors negRenderer) {
    JTable tab = new JTable(dtm) {
      @Override
      public Component prepareRenderer(final TableCellRenderer renderer, final int row, final int column) {
        Component comp = super.prepareRenderer(renderer, row, column);

        if (!this.isRowSelected(row) && row > 0) {
          // keeps the original colors
          comp.setBackground(this.getBackground());
          comp.setForeground(this.getForeground());
          comp.setFont(this.getFont());

          int modelRow = this.convertRowIndexToModel(row);
          String type = (String) this.getModel().getValueAt(modelRow, 0);

          // TODO - maybe different mechanism for choosing
          if ((negRenderer != null) && type.equals(new Payout(null,0,null,null).getSimpleName())) {
            comp.setBackground(negRenderer.getBG());
            comp.setForeground(negRenderer.getFG());
            comp.setFont(negRenderer.getFont());
          }
          else if ((posRenderer != null) && type.equals(new Deposit(null,0,null,null).getSimpleName())) {
            comp.setBackground(posRenderer.getBG());
            comp.setForeground(posRenderer.getFG());
            comp.setFont(posRenderer.getFont());
          }
          else if (defaultrenderer != null) {
            comp.setBackground(defaultrenderer.getBG());
            comp.setForeground(defaultrenderer.getFG());
            comp.setFont(defaultrenderer.getFont());
          }
        }

        return comp;
      }
    };
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
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < dtm.getRowCount(); i++) {
      for (int j = 0; j < dtm.getColumnCount(); j++) {
        sb.append(dtm.getValueAt(i,j) + " ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
