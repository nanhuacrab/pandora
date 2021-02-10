package com.nanhuacrab.pandora;

import com.google.common.collect.Maps;
import com.nanhuacrab.pandora.metrics.DefaultMetrics;
import com.nanhuacrab.pandora.metrics.Metrics;

import java.util.Map;
import java.util.Objects;

public abstract class DefaultFactories implements Factories {

  private CubeMatries cubeMatries = new CubeMatries();
  private KeyMatries keyMatries = new KeyMatries();
  private Metrics metrics = new DefaultMetrics();
  private Map<String, DefaultBox> boxes = Maps.newHashMap();

  @Override
  public CubeMatrix cubeMatrix(int dimensionSize) {
    return this.cubeMatries.cubeMatrx(dimensionSize);
  }

  @Override
  public Metrics metrics() {
    return this.metrics;
  }

  @Override
  public KeyMatrix keyMatrix(int[] dimensionsSize) {
    return this.keyMatries.keyMatrix(dimensionsSize);
  }

  @Override
  public DefaultBox box(String code) {
    DefaultBox box = this.boxes.get(code);
    if (Objects.isNull(box)) {
      box = this.createBox(code);
      this.boxes.put(code, box);
    }
    return box;
  }

  protected abstract DefaultBox createBox(String code);
}
