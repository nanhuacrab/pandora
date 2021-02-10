package com.nanhuacrab.pandora.metrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.common.collect.Maps;
import com.nanhuacrab.pandora.Box;
import com.nanhuacrab.pandora.CubeMatries;
import com.nanhuacrab.pandora.DefaultBox;

import java.util.Map;

public class DefaultMetrics implements Metrics {

  private final MetricRegistry metrics = new MetricRegistry();
  private final Map<String, Meter> meters = Maps.newConcurrentMap();
  private final Map<String, Timer> timers = Maps.newConcurrentMap();

  @Override
  public void registryBoxMetrics(Box box) {
    this.registryBoxDimensionSizeGauge(box);
    this.registryBoxMatchItemSizeGauge(box);
    this.registryBoxMatchCountMeter(box);
    this.registryBoxMatchDurationTimer(box);

    if (DefaultBox.class == box.getClass()) {
      DefaultBox b = (DefaultBox) box;
      this.registryBoxMatchItemKeySizeGauge(b);
    }
  }

  private void registryBoxMatchDurationTimer(Box box) {
    String name = this.boxMatchDurationTimerName(box);
    this.registerTimer(name);
  }

  private void registryBoxMatchItemKeySizeGauge(DefaultBox box) {
    String name = MetricRegistry.name(Box.class, box.code(), "MatchItemKeySize", "Gauge");
    this.metrics.register(name, (Gauge<Integer>) box::matchItemKeySize);
  }

  private void registryBoxDimensionSizeGauge(Box box) {
    String name = MetricRegistry.name(Box.class, box.code(), "DimensionSize", "Gauge");
    this.metrics.register(name, (Gauge<Integer>) box::dimensionSize);
  }

  private void registryBoxMatchItemSizeGauge(Box box) {
    String name = MetricRegistry.name(Box.class, box.code(), "MatchItemSize", "Gauge");
    this.metrics.register(name, (Gauge<Integer>) box::matchItemSize);
  }

  private String boxMatchCountMeterName(Box box) {
    String name = MetricRegistry.name(Box.class, box.code(), "MatchCount", "Meter");
    return name;
  }

  private void registryBoxMatchCountMeter(Box box) {
    String name = this.boxMatchCountMeterName(box);
    this.registerMeter(name);
  }

  @Override
  public void registryCubeMatrixMetrics(CubeMatries cubeMatries) {
    String name = MetricRegistry.name(Box.class, "CubeMatrieSize", "Gauge");
    this.metrics.register(name, (Gauge<Integer>) cubeMatries::cubeMatrieSize);
  }

  @Override
  public Meter boxMatchCountMeter(Box box) {
    String name = this.boxMatchCountMeterName(box);
    return this.meters.get(name);
  }

  @Override
  public Timer boxMatchDurationTimer(Box box) {
    String name = this.boxMatchDurationTimerName(box);
    return this.timers.get(name);
  }

  private String boxMatchDurationTimerName(Box box) {
    String name = MetricRegistry.name(Box.class, box.code(), "MatchDuration", "Timer");
    return name;
  }

  private void registerTimer(String name) {
    Timer timer = metrics.timer(name);
    this.timers.put(name, timer);
  }

  private void registerMeter(String name) {
    this.registerMeter(name, this.metrics.meter(name));
  }

  private void registerMeter(String name, Meter meter) {
    this.metrics.register(name, meter);
    this.meters.put(name, meter);
  }

  @Override
  public void removeBoxMetrics(Box box) {

  }
}
