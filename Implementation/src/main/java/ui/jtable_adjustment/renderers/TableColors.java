package ui.jtable_adjustment.renderers;

import java.awt.Color;
import java.awt.Font;

/**
 * Common definition for the layout-renderers.
 *
 * @author Michael Watholowitsch
 */
public interface TableColors {

  /**
   * Dynamically changes the background and foreground color.
   */
  void render();

  /**
   * @return the background color
   */
  Color getBG();

  /**
   * @return the foreground color
   */
  Color getFG();

  /**
   * @return the Font
   */
  Font getFont();
}






