package com.nanhuacrab.pandora;

/**
 * 配置项
 */
public interface MatchItem {

  int id();

  String description();

  String configuration();

  Box box();

  String[] keys();
}
