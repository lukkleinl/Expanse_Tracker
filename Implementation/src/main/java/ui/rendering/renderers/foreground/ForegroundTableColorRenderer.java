package ui.rendering.renderers.foreground;

import java.awt.Color;
import java.awt.Font;
import ui.rendering.renderers.TableColors;

/**
 * Used as base for all foreground color renderers.
 *
 * @author Michael Watholowitsch
 */
public abstract class ForegroundTableColorRenderer implements TableColors {
  protected final TableColors decRenderer;
  protected Color foreground;

  /**
   * Constructor
   *
   * @param renderer the wrapped renderer
   */
  public ForegroundTableColorRenderer(final TableColors renderer) {
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
    return decRenderer.getBG();
  }

  /**
   * Overrides {@linkplain TableColors.getFG()} to avoid implementing it in every concrete Renderer.
   */
  @Override
  public final Color getFG() {
    return foreground;
  }

  /**
   * Override for testing purposes.
   */
  @Override
  public String toString() {
    return "\nForeground: " + foreground + decRenderer.toString();
  }
}




