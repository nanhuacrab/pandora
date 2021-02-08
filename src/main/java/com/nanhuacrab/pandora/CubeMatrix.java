package com.nanhuacrab.pandora;

/**
 * CubeMatrix
 */
public abstract class CubeMatrix {
  private final int dimensionSize;

  public CubeMatrix(int dimensionSize) {
    this.dimensionSize = dimensionSize;
  }

  public int dimensionSize() {
    return this.dimensionSize;
  }
}
