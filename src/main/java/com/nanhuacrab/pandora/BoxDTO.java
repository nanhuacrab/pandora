package com.nanhuacrab.pandora;

public interface BoxDTO {

  String code();

  String description();

  DimensionDTO[] dimensions();

  MatchItemDTO[] matchItems();

  String separator();
}
