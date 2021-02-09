package com.nanhuacrab.pandora;

import java.util.Set;

/**
 * 配置项
 */
public interface MatchItem {

  int id();

  String description();

  String configuration();

  Box box();

  Set<String> keys();
}
