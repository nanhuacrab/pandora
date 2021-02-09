package com.nanhuacrab.pandora;

public interface Factories {

  CubeMatrix cubeMatrix(int dimensionSize);

  KeyMatrix keyMatrix(int[] dimensionsSize);

  DefaultBox box(String code);
}
