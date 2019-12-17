package ui.jtable_adjustment.renderers.background;

import java.awt.Color;
import java.awt.Font;
import ui.jtable_adjustment.renderers.TableColors;

/**
 * Used as base for all background color renderers.
 *
 * @author Michael Watholowitsch
 */
public abstract class BackgroundTableColorRenderer implements TableColors {
  protected final TableColors decRenderer;
  protected Color background;

  /**
   * Constructor
   *
   * @param renderer the wrapped renderer
   */
  public BackgroundTableColorRenderer(final TableColors renderer) {
    decRenderer = renderer;
  }

  /**
   * Overrides {@linkplain TableColors.getFont()} to avoid implementing it in every concrete Renderer.
   */
  @Override
  public final Font getFont() {
    return decRenderer.getFont();
  }

  /**
   * Overrides {@linkplain TableColors.getBG()} to avoid implementing it in every concrete Renderer.
   */
  @Override
  public final Color getBG() {
    return background;
  }

  /**
   * Overrides {@linkplain TableColors.getFG()} to avoid implementing it in every concrete Renderer.
   */
  @Override
  public final Color getFG() {
    return decRenderer.getFG();
  }

  /**
   * Override for testing purposes.
   */
  @Override
  public String toString() {
    return "\nBackground: " + background + decRenderer.toString();
  }
}




