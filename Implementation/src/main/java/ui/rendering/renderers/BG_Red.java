package ui.rendering.renderers;

import java.awt.Color;

/**
 * Class for making the background red.
 *
 * @author Michael Watholowitsch
 */
public class BG_Red extends AbstractTableColorRenderer {

  /**
   * Constructor
   *
   * @param renderer the wrapped renderer
   */
  public BG_Red(final TableColors renderer) {
    super(renderer);
  }

  @Override
  public void render() {
    super.decRenderer.render();
    super.decRenderer.background = Color.RED;
  }
}
