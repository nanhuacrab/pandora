package com.nanhuacrab.pandora;

import java.util.List;
import java.util.Map;

public interface Box {

  BoxDTO data();

  /**
   * 唯一标识符
   */
  String code();

  /**
   * 说明
   */
  String description();

  String emptySymbol();

  /**
   * 维度
   */
  Dimension[] dimensions();

  default int dimensionSize() {
    return this.dimensions().length;
  }

  /**
   * 匹配项
   */
  MatchItem[] matchItems();

  default int matchItemSize() {
    return this.matchItems().length;
  }

  /**
   * 匹配 纬度值 的配置项
   */
  MatchItem match(Map<String, String> dimensionValues);

  /**
   * 检查是否有重复 key 的配置项
   */
  Map<String, List<MatchItem>> checkDuplicationMatchItems();

  /**
   * 按顺序返回 各维度 的维度值
   */
  String[] dimensionValues(Map<String, String> dimensionValues);
}
