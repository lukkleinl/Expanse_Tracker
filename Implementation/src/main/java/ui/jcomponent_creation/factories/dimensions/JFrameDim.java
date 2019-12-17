package ui.jcomponent_creation.factories.dimensions;

import java.awt.Dimension;

public enum JFrameDim {
  // TODO - add more ranges
  HEIGHT(800),
  WIDTH(1200);

  private final int pixels;

  private JFrameDim(final int pixels) {
    this.pixels = pixels;
  }

  public int pixels() {
    return pixels;
  }

  public static Dimension getDimension() {
    return new Dimension(WIDTH.pixels, HEIGHT.pixels);
  }
}
