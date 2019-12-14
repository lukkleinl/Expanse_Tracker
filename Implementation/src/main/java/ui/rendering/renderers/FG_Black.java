package ui.rendering.renderers;

import java.awt.Color;

/**
 * Class for making the foreground black.
 *
 * @author Michael Watholowitsch
 */
public class FG_Black extends AbstractTableColorRenderer {

  /**
   * Constructor
   *
   * @param renderer the wrapped renderer
   */
  public FG_Black(final TableColors renderer) {
    super(renderer);
  }

  @Override
  public void render() {
    super.decRenderer.render();
    super.decRenderer.foreground = Color.BLACK;
  }
}
