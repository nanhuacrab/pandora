package com.nanhuacrab.pandora;

import java.util.Map;
import java.util.Set;

public class MatchItemDTO4Test implements MatchItemDTO {

  private int id;
  private String description;
  private Map<String, Set<String>> dimensionValue;
  private String configuration;

  @Override
  public int id() {
    return this.id;
  }

  @Override
  public String description() {
    return this.description;
  }

  @Override
  public Map<String, Set<String>> dimensionValue() {
    return this.dimensionValue;
  }

  @Override
  public String configuration() {
    return this.configuration;
  }
}
