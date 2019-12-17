package ui.jcomponent_creation.factories.dimensions;

import java.awt.Rectangle;

public enum TransactionTableDim {
  // TODO - add more ranges
  LEFT_OFFSET(225),
  TOP_OFFSET(100),
  HEIGHT(450),
  WIDTH(900);

  private final int pixels;

  private TransactionTableDim(final int pixels) {
    this.pixels = pixels;
  }

  public int pixels() {
    return pixels;
  }

  public static Rectangle getRectangle() {
    return new Rectangle(LEFT_OFFSET.pixels,TOP_OFFSET.pixels,WIDTH.pixels,HEIGHT.pixels);
  }
}
