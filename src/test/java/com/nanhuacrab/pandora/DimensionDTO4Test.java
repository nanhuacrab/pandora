package com.nanhuacrab.pandora;

public class DimensionDTO4Test implements DimensionDTO {
  private int id;
  private String code;
  private String description;
  private boolean nullable;
  private int sequence;

  @Override
  public int id() {
    return this.id;
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

  @Override
  public int sequence() {
    return this.sequence;
  }
}
