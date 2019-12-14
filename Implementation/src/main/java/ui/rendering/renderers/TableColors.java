package ui.rendering.renderers;

import java.awt.Color;
import java.awt.Font;

/**
 * Common definition for the layout-renderers.
 *
 * @author Michael Watholowitsch
 */
public abstract class TableColors {
  protected Color foreground;
  protected Color background;

  /**
   * Constructor
   */
  public TableColors() { }

  /**
   * Dynamically changes the background and foreground color.
   */
  public abstract void render();

  /* ---------- getters ---------- */

  /**
   * @return the background color
   */
  public abstract Color getBG();

  /**
   * @return the foreground color
   */
  public abstract Color getFG();

  /**
   * @return the Font
   */
  public abstract Font getFont();
}






