package ui.rendering.renderers;

import java.awt.Color;
import java.awt.Font;

/**
 * Provides a basic color scheme for JTables. Necessary as base since it sets the font of the text.
 *
 * @author Michael Watholowitsch
 */
public final class BasicCell_Font extends TableColors {
  private final Font font;

  /**
   * Constructor
   *
   * @param font the desired text-font
   */
  public BasicCell_Font(final Font font) {
    this.font = font;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void render() {
    super.background = Color.WHITE;
    super.foreground = Color.BLACK;
  }

  /* ---------- getters ---------- */

  @Override
  public Color getBG() {
    return super.background;
  }

  @Override
  public Color getFG() {
    return super.foreground;
  }

  @Override
  public Font getFont() {
    return font;
  }

  /**
   * Override for testing purposes.
   */
  @Override
  public String toString() {
    return "Foreground: " + super.foreground + "\nBackground: " + super.background + "\nFont: " + font;
  }
}




