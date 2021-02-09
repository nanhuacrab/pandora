package com.nanhuacrab.pandora;

public class DefaultBoxDTO implements BoxDTO {

  private DefaultDimensionDTO[] dimensions;
  private DefaultMatchItemDTO[] matchItems;
  private String code;
  private String description;
  private String separator;

  private DefaultBoxDTO() {

  }

  public DefaultBoxDTO(String code, String separator, String description, DefaultDimensionDTO[] dimensions,
      DefaultMatchItemDTO[] matchItems) {
    this.code = code;
    this.separator = separator;
    this.description = description;
    this.dimensions = dimensions;
    this.matchItems = matchItems;
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
