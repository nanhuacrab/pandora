package com.nanhuacrab.pandora;

public interface Factories {

  CubeMatrix cubeMatrix(int dimensionSize);

  KeyMatrix keyMatrix(int[] dimensionsSize);

  Box box(String code);
}
