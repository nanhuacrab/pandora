package com.nanhuacrab.pandora;

public class Box4Test implements BoxDTO {

  private DimensionDTO4Test[] dimensions;
  private MatchItemDTO4Test[] matchItems;
  private String code;
  private String description;
  private String separator;

  @Override
  public String code() {
    return this.code;
  }

  @Override
  public String description() {
    return this.description;
  }

  @Override
  public DimensionDTO[] dimensions() {
    return this.dimensions;
  }

  @Override
  public MatchItemDTO[] matchItems() {
    return this.matchItems;
  }

  @Override
  public String separator() {
    return this.separator;
  }
}
