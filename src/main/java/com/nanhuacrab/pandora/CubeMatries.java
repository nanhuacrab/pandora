package com.nanhuacrab.pandora;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;

public class CubeMatries {
  private Map<Integer, CubeMatrix> cubeMatries = Maps.newHashMap();

  public CubeMatries() {
    this.loadDefaultCubeMatries();
  }

  public int cubeMatrieSize() {
    return this.cubeMatries.size();
  }

  private void loadDefaultCubeMatries() {
    Gson gson = new Gson();
    for (int i = 1; i <= 6; i++) {
      String fileName = String.format("CubeMatrix_%s.json", i);
      InputStream stream = this.getClass().getClassLoader().getResourceAsStream(fileName);
      int[][] matrix = gson.fromJson(new InputStreamReader(stream, Charsets.UTF_8), int[][].class);
      this.cubeMatries.put(i, new CubeMatrix(i, matrix));
    }
  }

  public CubeMatrix cubeMatrx(int dimensionSize) {
    CubeMatrix cubeMatrix = this.cubeMatries.get(dimensionSize);
    if (Objects.isNull(cubeMatrix)) {
      cubeMatrix = new CubeMatrix(dimensionSize);
      this.cubeMatries.put(dimensionSize, cubeMatrix);
    }
    return cubeMatrix;
  }
}
