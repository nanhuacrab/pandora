package com.nanhuacrab.pandora;

public class DefaultDimensionDTO implements DimensionDTO {
  private String code;
  private String description;
  private boolean nullable;

  private DefaultDimensionDTO() {

  }

  public DefaultDimensionDTO(String code, String description, boolean nullable) {
    this.code = code;
    this.description = description;
    this.nullable = nullable;
  }

  @Override
  public String code() {
    return this.code;
  }

  @Override
  public String description() {
    return this.description;
  }

  @Override
  public boolean nullable() {
    return this.nullable;
  }
}
