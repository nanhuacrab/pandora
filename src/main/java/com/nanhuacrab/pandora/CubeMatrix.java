package com.nanhuacrab.pandora;

import org.apache.commons.lang3.ArrayUtils;

/**
 * CubeMatrix
 */
public class CubeMatrix {
  private final int dimensionSize;
  private final int[][] matrix;

  public CubeMatrix(int dimensionSize, int[][] matrix) {
    this.dimensionSize = dimensionSize;
    this.matrix = matrix;
  }

  public CubeMatrix(int dimensionSize) {
    this.dimensionSize = dimensionSize;

    MatrixGenerator matrixGenerator = new MatrixGenerator(this.dimensionSize);
    matrixGenerator.generate();
    this.matrix = matrixGenerator.orderedMatrix();
  }

  public int dimensionSize() {
    return this.dimensionSize;
  }

  int[][] matrix() {
    return this.matrix;
  }

  String[] generateKeys(String[] dimensionValues) {
    return this.generateKeys(dimensionValues, Box.SEPARATOR);
  }

  String[] generateKeys(String[] dimensionValues, String separator) {
    if (dimensionValues.length != this.dimensionSize) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    }

    String[] keys = new String[this.matrix.length];

    for (int i = 0; i < keys.length; i++) {
      String key = "";
      for (int j = 0; j < this.dimensionSize; j++) {
        if (1 == this.matrix[i][j]) {
          key += dimensionValues[j];
        }
        key += separator;
      }
      keys[i] = key;
    }

    return keys;
  }

}
