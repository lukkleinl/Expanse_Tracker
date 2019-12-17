package ui.jcomponent_creation;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class LocalDefaultTableModel extends DefaultTableModel {

  public LocalDefaultTableModel(final String[] columnNames) {
    super(columnNames, 0);
  }

  @Override
  public Class getColumnClass(final int column) {
    return String.class;
  }
  @Override
  public boolean isCellEditable(final int row, final int column) {
    return false;
  }
}
