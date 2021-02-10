package com.nanhuacrab.pandora.metrics;

import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import com.nanhuacrab.pandora.Box;
import com.nanhuacrab.pandora.CubeMatries;

public interface Metrics {

  /**
   * 注册 box Metrics
   */
  void registryBoxMetrics(Box box);

  /**
   * 注册 CubeMatrix Metrics
   */
  void registryCubeMatrixMetrics(CubeMatries cubeMatries);

  /**
   * 查找匹配次数 Meter
   */
  Meter boxMatchCountMeter(Box box);

  /**
   *
   * */
  Timer boxMatchDurationTimer(Box box);


  /**
   * 
   */
  void removeBoxMetrics(Box box);

}
