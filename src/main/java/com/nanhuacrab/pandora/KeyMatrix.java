package com.nanhuacrab.pandora;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;

public class KeyMatrix {

  private final int matrixSize;
  private final int dimensionSize;
  private final int[] dimensionsSize;
  private final String[][] matrix;
  private static final char[] DIMENSION_START_SYMBOLS;
  private final String[] symbols;
  private final int symbolsSize;


  static {
    int startSymbol = (int) 'A';
    int endSymbol = (int) 'Z';

    DIMENSION_START_SYMBOLS = new char[endSymbol - startSymbol + 1];

    int symbol = startSymbol;
    int i = 0;
    while (symbol <= endSymbol) {
      DIMENSION_START_SYMBOLS[i++] = (char) symbol;
      symbol++;
    }
  }

  public KeyMatrix(int[] dimensionsSize) {

    this.dimensionSize = dimensionsSize.length;
    this.dimensionsSize = new int[this.dimensionSize];

    int matrixSize = 1;
    int symbolsSize = 0;
    for (int i = 0; i < this.dimensionSize; i++) {
      int size = Math.max(dimensionsSize[i], 1);
      this.dimensionsSize[i] = size;
      matrixSize *= size;
      symbolsSize += size;
    }
    this.matrixSize = matrixSize;
    this.symbolsSize = symbolsSize;

    this.matrix = new String[this.matrixSize][this.dimensionSize];
    this.symbols = new String[symbolsSize];

    this.initializeMatrix();
  }

  private void initializeMatrix() {
    int step = this.matrixSize;
    int symbolIndex = 0;

    for (int j = 0; j < this.dimensionSize; j++) {

      String[] dimensionValues = this.dimensionValues(j, this.dimensionsSize[j]);
      step /= dimensionValues.length;

      for (String dimensionValue : dimensionValues) {
        this.symbols[symbolIndex++] = dimensionValue;
      }

      for (int i = 0; i < matrixSize;) {
        for (String dimensionValue : dimensionValues) {
          for (int x = 0; x < step; x++) {
            matrix[i][j] = dimensionValue;
            i++;
          }
        }
      }

    }
  }

  private String[] dimensionValues(int index, int size) {
    String[] dimensionValues = new String[size];
    for (int i = 0; i < size; i++) {
      dimensionValues[i] = String.format("%s%s", DIMENSION_START_SYMBOLS[index], i);
    }
    return dimensionValues;
  }

  String[] symbols() {
    return this.symbols;
  }

  public String[][] matrix() {
    return this.matrix;
  }

  public String[] keys(Set<String>[] dimensionsValues) {
    return this.keys(DefaultBox.SEPARATOR, dimensionsValues);
  }

  private Map<String, String> symbolWithDimensionValues(Set<String>[] dimensionsValues){

    Map<String, String> symbolWithDimensionValues = Maps.newHashMap();
    int symbolIndex = 0;
    for (int i = 0; i < dimensionsValues.length; i++) {
      if (CollectionUtils.isEmpty(dimensionsValues[i])) {
        symbolWithDimensionValues.put(this.symbols[symbolIndex++], StringUtils.EMPTY);
        continue;
      }
      for (String dimensionsValue : dimensionsValues[i]) {
        symbolWithDimensionValues.put(this.symbols[symbolIndex++],
                ObjectUtils.defaultIfNull(dimensionsValue, StringUtils.EMPTY));
      }
    }

    return symbolWithDimensionValues;
  }

  private static final String[][] EMPTH_KEY_MATRIX = new String[0][0];
  public String[][] keyMatrix(Set<String>[] dimensionsValues) {
    if (dimensionsValues.length != this.dimensionSize) {
      return EMPTH_KEY_MATRIX;
    }

    Map<String, String> symbolWithDimensionValues = this.symbolWithDimensionValues(dimensionsValues);

    String[][] keyMatrix = new String[this.matrix.length][this.dimensionSize];

    for (int i = 0; i < this.matrix.length; i++ ) {
      for ( int j = 0; j < this.dimensionSize; j++) {
        String symbol = this.matrix[i][j];
        keyMatrix[i][j] = symbolWithDimensionValues.get(symbol);
      }
    }

    return keyMatrix;
  }

  public String[] keys(String separator, Set<String>[] dimensionsValues) {
    if (dimensionsValues.length != this.dimensionSize) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    }

    Map<String, String> symbolWithDimensionValues = this.symbolWithDimensionValues(dimensionsValues);

    String[] keys = new String[this.matrixSize];
    int keyIndex = 0;
    for (String[] symbols : this.matrix) {
      String key = StringUtils.EMPTY;
      for (String symbol : symbols) {
        key += symbolWithDimensionValues.get(symbol) + separator;
      }
      keys[keyIndex++] = key;
    }

    return keys;
  }

}
