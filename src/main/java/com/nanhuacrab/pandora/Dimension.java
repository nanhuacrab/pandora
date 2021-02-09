package com.nanhuacrab.pandora;

/**
 * 维度
 */
public interface Dimension {

  int id();

  String code();

  String description();

  boolean nullable();

  int sequence();

  Box box();
}
