package ui.main;

import javax.swing.JFrame;

/**
 * Interface needed for the GUI. Every user interface page will implement this interface.
 *
 * @author Patrick Gmasz
 */

public interface InterfacePage {


  /**
   * This method updates the given JFrame with every components, and also sets indicators to default.
   *
   * @param frame The JFrame, which components will be updated
   */
  void configureFrame(JFrame frame);
}
