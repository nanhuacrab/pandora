package com.nanhuacrab.pandora;

/**
 * 魔方
 */
public class Box {
  private int id;
  private String code;
  private Dimension[] dimensions;
  private MatchItem[] matchItems;
  private String description;

  public int id() {
    return this.id;
  }

  public String code() {
    return this.code;
  }

  public String description() {
    return this.description;
  }

  public Dimension[] dimensions() {
    return this.dimensions;
  }

  public MatchItem[] matchItems() {
    return this.matchItems;
  }
}
