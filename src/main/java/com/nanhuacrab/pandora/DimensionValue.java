package com.nanhuacrab.pandora;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class DimensionValue {
  private final Dimension dimension;
  private final Set<String> valueSet;
  private final String[] values;

  public DimensionValue(Dimension dimension, String values, String separator) {
    this(dimension, Splitter.on(separator).splitToList(values));
  }

  public DimensionValue(Dimension dimension, List<String> values) {
    this(dimension, values.stream().toArray(String[]::new));
  }

  public DimensionValue(Dimension dimension, String[] values) {
    this.dimension = dimension;
    this.values = values;
    this.valueSet = Sets.newHashSet(this.values());
  }

  public Dimension dimension() {
    return this.dimension;
  }

  public String[] values() {
    return this.values;
  }

  public boolean containsValue(String value) {
    return this.valueSet.contains(value);
  }
}
