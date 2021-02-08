package com.nanhuacrab.pandora;

/**
 * 配置项
 * */
public class MatchItem {
  private int id;
  private String description;
  private DimensionValue[] dimensionValue;
  private String configuration;

  public int id() {
    return this.id;
  }

  public String description() {
    return this.description;
  }

  public DimensionValue[] dimensionValue(){
    return this.dimensionValue;
  }

  public String configuration(){
    return this.configuration;
  }
}
