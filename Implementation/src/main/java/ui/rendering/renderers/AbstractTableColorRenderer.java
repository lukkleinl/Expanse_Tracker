package ui.rendering.renderers;

import java.awt.Color;
import java.awt.Font;

/**
 * Used as Base for all concrete Renderers.
 *
 * @author Michael Watholowitsch
 */
public abstract class AbstractTableColorRenderer extends TableColors {
  protected final TableColors decRenderer;

  /**
   * Constructor
   *
   * @param renderer the wrapped renderer
   */
  public AbstractTableColorRenderer(final TableColors renderer) {
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
    return decRenderer.background;
  }

  /**
   * Overrides {@linkplain TableColors.getFG()} to avoid implementing it in every concrete Renderer.
   */
  @Override
  public final Color getFG() {
    return decRenderer.foreground;
  }

  /**
   * Override for testing purposes.
   */
  @Override
  public String toString() {
    return "Foreground: " + decRenderer.foreground + "\nBackground: " + decRenderer.background + "\nFont: " + decRenderer.getFont();
  }
}




