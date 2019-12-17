package ui.jcomponent_creation.renderers;

import java.awt.Color;
import java.awt.Font;

/**
 * Acts as a base for defining JTable layout. Provides a font for JTables.
 *
 * @author Michael Watholowitsch
 */
public final class FontCell implements TableColors {
  private final Font font;

  /**
   * Constructor
   *
   * @param font the desired text-font
   */
  public FontCell(final Font font) {
    this.font = font;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void render() { }

  /* ---------- getters ---------- */

  @Override
  public Color getBG() {
    throw new UnsupportedOperationException("No Background defined !");
  }

  @Override
  public Color getFG() {
    throw new UnsupportedOperationException("No Foreground defined !");
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
    return "\nFont: " + font;
  }
}




