package com.nanhuacrab.pandora;

public class MatrixGenerator {

  private final int dimensionSize;
  private final int matrixSize;
  private final int[][] matrix;
  private final int[][] orderedMatrix;
  private final int[] matrixPriorities;
  private final int[] matrixPrioritySize; // 维度数量 matrixPrioritySize[i] 标识 维度优先级为i的数量
  private final int[] matrixPriorityStartIndex;

  public MatrixGenerator(int dimensionSize) {
    /* 假设 dimensionSize = 3 */
    this.dimensionSize = dimensionSize;
    this.matrixSize = 1 << dimensionSize;
    this.orderedMatrix = new int[this.matrixSize][this.dimensionSize];
    this.matrixPriorities = new int[this.matrixSize]; // 优先级

    this.matrix = new int[this.matrixSize][this.dimensionSize]; // 8 * 3 的二维数组
    for (int i = 0; i < matrix.length; i++) {
      matrix[i] = new int[dimensionSize];
    }

    this.matrixPrioritySize = new int[this.dimensionSize + 1];
    this.matrixPrioritySize[0] = this.matrixSize;

    this.matrixPriorityStartIndex = new int[this.dimensionSize + 1];
  }

  /**
   *
   * */
  public void generate() {
    this.generatorMatrix();
    this.sort();
  }

  int[][] matrix() {
    return this.matrix;
  }

  int[][] orderedMatrix() {
    return this.orderedMatrix;
  }

  int[] matrixPrioritySize() {
    return this.matrixPrioritySize;
  }

  int[] matrixPriorityStartIndex() {
    return this.matrixPriorityStartIndex;
  }

  private void generatorMatrix() {
    int loopInnerStep = this.matrixSize; // 遍历参数的内部循环步长 初始值8
    int loopInnerSize = loopInnerStep >> 1; // 内部循环次数 初始值4
    int loopSize = 1;

    for (int dimensionIndex = 0; dimensionIndex < this.dimensionSize; dimensionIndex++) {

      int i = 0, max = loopInnerSize;

      for (int j = 0; j < loopSize; j++) {

        for (; i < max; i++) {
          this.matrix[i][dimensionIndex] = 1;
          int priority = this.matrixPriorities[i];
          this.matrixPrioritySize[priority]--;

          priority++;
          this.matrixPriorities[i] = priority;
          this.matrixPrioritySize[priority]++;
        }

        i += loopInnerSize;
        max += loopInnerStep;
      }

      loopInnerStep >>= 1;
      loopInnerSize >>= 1;
      loopSize <<= 1;
    }
  }

  private void sort() {
    int startIndex = 0;
    int[] startIndexs = new int[this.dimensionSize + 1];;
    for (int i = this.matrixPrioritySize.length - 1; i >= 0; i--) {
      this.matrixPriorityStartIndex[i] = startIndex;
      startIndexs[i] = startIndex;
      startIndex += this.matrixPrioritySize[i];
    }

    for (int i = 0, j = 1; i < this.matrix.length - 1; i++, j++) {
      int priority = this.matrixPriorities[i];
      int index = startIndexs[priority];
      this.orderedMatrix[index] = this.matrix[i];
      startIndexs[priority]++;
    }

  }

}
