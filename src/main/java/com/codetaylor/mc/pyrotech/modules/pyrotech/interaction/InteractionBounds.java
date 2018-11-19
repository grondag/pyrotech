package com.codetaylor.mc.pyrotech.modules.pyrotech.interaction;

public class InteractionBounds {

  public static final InteractionBounds INFINITE = new InteractionBounds(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
  public static final InteractionBounds BLOCK_FACE = new InteractionBounds(0, 0, 1, 1);

  private final double minX;
  private final double maxX;
  private final double minY;
  private final double maxY;

  public InteractionBounds(double minX, double minY, double maxX, double maxY) {

    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
  }

  public boolean contains(double x, double y) {

    if (x > this.minX && x < this.maxX) {

      return (y > this.minY && y < this.maxY);

    } else {
      return false;
    }
  }
}
