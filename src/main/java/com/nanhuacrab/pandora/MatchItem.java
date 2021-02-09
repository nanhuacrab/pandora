package com.nanhuacrab.pandora;

/**
 * 配置项
 */
public interface MatchItem {

  String configuration();

  Box box();

  String[] keys();

  MatchItemDTO data();
}
