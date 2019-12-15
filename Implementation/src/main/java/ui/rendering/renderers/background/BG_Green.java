package ui.rendering.renderers.background;

import java.awt.Color;
import ui.rendering.renderers.TableColors;

/**
 * Class for making the background green.
 *
 * @author Michael Watholowitsch
 */
public class BG_Green extends BackgroundTableColorRenderer {

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
    super.background = Color.GREEN;
  }
}
