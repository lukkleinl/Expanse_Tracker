package ui.jcomponent_creation.renderers.foreground;

import java.awt.Color;
import ui.jcomponent_creation.renderers.TableColors;

/**
 * Class for making the foreground white.
 *
 * @author Michael Watholowitsch
 */
public class FG_White extends ForegroundTableColorRenderer {

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
    super.foreground = Color.WHITE;
  }
}
