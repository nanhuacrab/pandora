package com.nanhuacrab.pandora;

import java.util.Map;
import java.util.Set;

public interface MatchItemDTO {
  int id();

  String description();

  Map<String, Set<String>> dimensionValue();

  String configuration();
}
