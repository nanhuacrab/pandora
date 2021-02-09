package com.nanhuacrab.pandora;

/**
 * 配置项
 */
public interface MatchItem {

  String configuration();

  DefaultBox box();

  String[] keys();

  MatchItemDTO data();
}
