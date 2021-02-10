package com.nanhuacrab.pandora.performance;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.nanhuacrab.pandora.MatrixGenerator;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MatrixGeneratorPerformanceTest {

  private final int maxDimensionSize = 10;
  private final ExecutorService executorService = Executors.newFixedThreadPool(this.maxDimensionSize);
  private final MetricRegistry metrics = new MetricRegistry();
  private final ConsoleReporter reporter = ConsoleReporter.forRegistry(this.metrics).convertRatesTo(TimeUnit.SECONDS)
      .convertDurationsTo(TimeUnit.MILLISECONDS).build();

  private void randomSleep() {
    try {
      TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100, 200));
    } catch (InterruptedException e) {

    }
  }

  private void startThread(int i) {

    final MatrixGenerator matrixGenerator = new MatrixGenerator(i + 1);
    final Timer timer = metrics.timer(Integer.toString(i));

    this.executorService.execute(() -> {

      while (true) {
        try (final Timer.Context context = timer.time()) {
          matrixGenerator.generate();
        }
        this.randomSleep();
      }

    });
  }

  @Test
  public void testGenerate() throws IOException, InterruptedException {

    this.reporter.start(10, TimeUnit.SECONDS);

    for (int i = 0; i < maxDimensionSize; i++) {
      this.startThread(i);
    }

    TimeUnit.MINUTES.sleep(1);
  }

}
