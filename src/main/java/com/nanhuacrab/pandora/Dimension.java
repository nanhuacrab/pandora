package com.nanhuacrab.pandora;

/**
 * 维度
 */
public interface Dimension {
  DefaultBox box();
  String code();
  DimensionDTO data();
}
