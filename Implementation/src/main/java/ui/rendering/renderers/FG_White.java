package ui.rendering.renderers;

import java.awt.Color;

/**
 * Class for making the foreground white.
 *
 * @author Michael Watholowitsch
 */
public class FG_White extends AbstractTableColorRenderer {

  /**
   * Constructor
   *
   * @param renderer the wrapped renderer
   */
  public FG_White(final TableColors renderer) {
    super(renderer);
  }

  @Override
  public void render() {
    super.decRenderer.render();
    super.decRenderer.foreground = Color.WHITE;
  }
}
