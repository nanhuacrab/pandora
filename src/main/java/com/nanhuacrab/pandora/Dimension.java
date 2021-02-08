package com.nanhuacrab.pandora;

/**
 * 维度
 * */
public class Dimension {
  private int id;
  private String code;
  private String description;
  private boolean nullable;
  private int sequence;

  public int id() {
    return this.id;
  }

  public String code() {
    return this.code;
  }

  public String description() {
    return this.description;
  }

  public boolean nullable(){
    return this.nullable;
  }

  public int sequence() {
    return this.sequence;
  }
}
