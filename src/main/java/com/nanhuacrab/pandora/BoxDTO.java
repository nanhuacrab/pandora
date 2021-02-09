package com.nanhuacrab.pandora;

public interface BoxDTO {
  int id();

  String code();

  String description();

  DimensionDTO[] dimensions();

  MatchItemDTO[] matchItems();

  String separator();
}
