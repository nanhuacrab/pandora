package com.nanhuacrab.pandora;

import java.util.Map;
import java.util.Set;

public class DefaultMatchItemDTO implements MatchItemDTO {

  private String description;
  private Map<String, Set<String>> dimensionValue;
  private String configuration;

  private DefaultMatchItemDTO() {

  }

  public DefaultMatchItemDTO(String description, String configuration, Map<String, Set<String>> dimensionValue) {
    this.description = description;
    this.configuration = configuration;
    this.dimensionValue = dimensionValue;
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
