package com.nanhuacrab.pandora;

import java.util.List;
import java.util.Map;

public interface Box {

  BoxDTO data();

  /**
   * 唯一标识符
   * */
  String code();

  /**
   * 说明
   * */
  String description();

  /**
   * 维度
   * */
  Dimension[] dimensions();

  /**
   * 匹配项
   * */
  MatchItem[] matchItems();

  /**
   * 匹配 纬度值 的配置项
   * */
  MatchItem match(Map<String, String> dimensionValues);

  /**
   * 检查是否有重复 key 的配置项
   * */
  Map<String, List<MatchItem>> checkDuplicationMatchItem();
}
