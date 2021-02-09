package com.nanhuacrab.pandora;

public class DimensionDTO4Test implements DimensionDTO {
  private String code;
  private String description;
  private boolean nullable;

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
