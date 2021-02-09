package com.nanhuacrab.pandora;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Objects;

public abstract class DefaultFactories implements Factories {

  private CubeMatries cubeMatries = new CubeMatries();
  private KeyMatries keyMatries = new KeyMatries();
  private Map<String, Box> boxes = Maps.newHashMap();

  @Override
  public CubeMatrix cubeMatrix(int dimensionSize) {
    return this.cubeMatries.cubeMatrx(dimensionSize);
  }

  @Override
  public KeyMatrix keyMatrix(int[] dimensionsSize) {
    return this.keyMatries.keyMatrix(dimensionsSize);
  }

  @Override
  public Box box(String code) {
    Box box = this.boxes.get(code);
    if (Objects.isNull(box)) {
      box = this.createBox(code);
      this.boxes.put(code, box);
    }
    return box;
  }

  protected abstract Box createBox(String code);
}
