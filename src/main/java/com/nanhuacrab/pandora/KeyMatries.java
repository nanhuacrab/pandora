package com.nanhuacrab.pandora;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

public class KeyMatries {

  private Map<String, KeyMatrix> keyMatries = Maps.newHashMap();

  public KeyMatrix keyMatrix(int[] dimensionsSize) {
    String key = this.key(dimensionsSize);
    KeyMatrix keyMatrix = this.keyMatries.get(key);
    if (Objects.isNull(keyMatrix)) {
      keyMatrix = new KeyMatrix(dimensionsSize);
      this.keyMatries.put(key, keyMatrix);
    }
    return keyMatrix;
  }

  private String key(int[] dimensionsSize) {
    String key = StringUtils.EMPTY;
    for (int dimensionSize : dimensionsSize) {
      key += dimensionSize + "_";
    }
    return key;
  }

}
