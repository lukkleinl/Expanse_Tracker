package ui.jcomponent_creation.factories.dimensions;

import java.awt.Rectangle;

public enum AccountTableDim {
  // TODO - add more ranges
  LEFT_OFFSET(100),
  TOP_OFFSET(150),
  HEIGHT(370),
  WIDTH(975);

  private final int pixels;

  private AccountTableDim(final int pixels) {
    this.pixels = pixels;
  }

  public int pixels() {
    return pixels;
  }

  public static Rectangle getRectangle() {
    return new Rectangle(LEFT_OFFSET.pixels,TOP_OFFSET.pixels,WIDTH.pixels,HEIGHT.pixels);
  }
}
