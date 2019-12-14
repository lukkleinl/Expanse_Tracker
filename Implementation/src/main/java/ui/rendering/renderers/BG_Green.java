package ui.rendering.renderers;

import java.awt.Color;

/**
 * Class for making the background green.
 *
 * @author Michael Watholowitsch
 */
public class BG_Green extends AbstractTableColorRenderer {

  /**
   * Constructor
   *
   * @param renderer the wrapped renderer
   */
  public BG_Green(final TableColors renderer) {
    super(renderer);
  }

  @Override
  public void render() {
    super.decRenderer.render();
    super.decRenderer.background = Color.GREEN;
  }
}
