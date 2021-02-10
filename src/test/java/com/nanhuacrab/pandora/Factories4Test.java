package com.nanhuacrab.pandora;

import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import com.nanhuacrab.pandora.metrics.Metrics;

public class Factories4Test extends DefaultFactories {

  private static final class EmptyMetrics implements Metrics {

    @Override
    public void registryBoxMetrics(Box box) {

    }

    @Override
    public void registryCubeMatrixMetrics(CubeMatries cubeMatries) {

    }

    @Override
    public Meter boxMatchCountMeter(Box box) {
      return null;
    }

    @Override
    public Timer boxMatchDurationTimer(Box box) {
      return null;
    }

    @Override
    public void removeBoxMetrics(Box box) {

    }
  }

  private EmptyMetrics emptyMetrics = new EmptyMetrics();

  @Override
  protected DefaultBox createBox(String code) {
    return null;
  }

  @Override
  public Metrics metrics() {
    return emptyMetrics;
  }

}
