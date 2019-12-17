package ui.jcomponent_creation.factories;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import exceptions.SWE_RuntimeException;
import ui.jcomponent_creation.LocalDefaultTableModel;

public abstract class JComponentFactory {

  public static JComponentFactory getFactory(final String type) {
    if (type == null) throw new SWE_RuntimeException("Cannot evaluate which factory to get !");

    if (type.equalsIgnoreCase("Transaction")) return new TransactionJComponentFactory();
    if (type.equalsIgnoreCase("Account")) return new AccountJComponentFactory();

    throw new SWE_RuntimeException("Requested unknown Factory !");
  }


  // implementations of createJTable were created with help of  with https://stackoverflow.com/a/8372821
  public abstract JTable createJTable(final LocalDefaultTableModel model);
  public abstract JScrollPane createJScrollPane(final JTable table);
  public abstract JFrame createJFrame(final JScrollPane pane);

}
