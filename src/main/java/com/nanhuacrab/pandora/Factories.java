package com.nanhuacrab.pandora;

import com.nanhuacrab.pandora.metrics.Metrics;

public interface Factories {

  CubeMatrix cubeMatrix(int dimensionSize);

  KeyMatrix keyMatrix(int[] dimensionsSize);

  Metrics metrics();

  DefaultBox box(String code);
}
