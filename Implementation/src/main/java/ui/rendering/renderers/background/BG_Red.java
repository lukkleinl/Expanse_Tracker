package ui.rendering.renderers.background;

import java.awt.Color;
import ui.rendering.renderers.TableColors;

/**
 * Class for making the background red.
 *
 * @author Michael Watholowitsch
 */
public class BG_Red extends BackgroundTableColorRenderer {

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
    super.background = Color.RED;
  }
}
