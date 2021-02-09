package com.nanhuacrab.pandora;

public interface Factories {
  CubeMatrix cubeMatrix(int dimensionSize);

  Box box(String code);
}
