package com.nanhuacrab.pandora;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Map;
import java.util.Set;

/**
 * 魔方
 */
public class Box {

  private static final class MyDimension implements Dimension {
    private final int id;
    private final String code;
    private final String description;
    private final boolean nullable;
    private final int sequence;
    private final Box ownerBox;

    private MyDimension(DimensionDTO dimension, Box box) {
      this.ownerBox = box;
      this.id = dimension.id();
      this.code = dimension.code();
      this.description = dimension.description();
      this.nullable = dimension.nullable();
      this.sequence = dimension.sequence();
    }

    @Override
    public int id() {
      return this.id;
    }

    @Override
    public String code() {
      return this.code;
    }

    @Override
    public String description() {
      return this.description;
    }

    @Override
    public boolean nullable() {
      return this.nullable;
    }

    @Override
    public int sequence() {
      return this.sequence;
    }

    @Override
    public Box box() {
      return this.ownerBox;
    }
  }

  private static final class MyMatchItem implements MatchItem {
    private final int id;
    private final String description;
    // private final DimensionValue[] dimensionValue;
    private final String configuration;
    private final String[] keys;
    private final Box ownerBox;

    public MyMatchItem(MatchItemDTO matchItem, Box box) {
      this.ownerBox = box;
      this.id = matchItem.id();
      this.description = matchItem.description();
      this.configuration = matchItem.configuration();

      int[] dimensionsSize = new int[this.ownerBox.dimensions.length];
      Set<String>[] dimensionsValues = new Set[this.ownerBox.dimensions.length];
      for (int i = 0; i < this.ownerBox.dimensions.length; i++) {
        String dimensionCode = this.ownerBox.dimensions[i].code();
        Set<String> dimensionValues = matchItem.dimensionValue().get(dimensionCode);
        if (CollectionUtils.isEmpty(dimensionValues)) {
          dimensionsValues[i] = null;
          dimensionsSize[i] = 0;
        } else {
          dimensionsValues[i] = dimensionValues;
          dimensionsSize[i] = dimensionValues.size();
        }
      }
      this.keys = this.ownerBox.factories.keyMatrix(dimensionsSize).keys(this.ownerBox.separator, dimensionsValues);
    }

    @Override
    public int id() {
      return this.id;
    }

    @Override
    public String description() {
      return this.description;
    }

    @Override
    public String configuration() {
      return this.configuration;
    }

    @Override
    public Box box() {
      return this.ownerBox;
    }

    @Override
    public String[] keys() {
      return this.keys;
    }

  }

  private final int id;
  private final String code;
  private final Dimension[] dimensions;
  private final Map<String, MyDimension> dimensionsByCode = Maps.newHashMap();
  private final MatchItem[] matchItems;
  private final String description;
  public static final String SEPARATOR = ",";
  private final CubeMatrix cubeMatrix;
  private final String separator;
  private final Map<String, MatchItem> matchItemWithKeys = Maps.newHashMap();
  private final Factories factories;

  public Box(BoxDTO box, Factories factories) {

    this.factories = factories;
    this.id = box.id();
    this.code = box.code();
    this.description = box.description();
    this.separator = box.separator();

    this.dimensions = new Dimension[box.dimensions().length];
    for (int i = 0; i < this.dimensions.length; i++) {
      MyDimension dimension = new MyDimension(box.dimensions()[i], this);
      this.dimensions[i] = dimension;
      this.dimensionsByCode.put(dimension.code, dimension);
    }

    this.matchItems = new MatchItem[box.matchItems().length];
    for (int i = 0; i < this.matchItems.length; i++) {
      this.matchItems[i] = new MyMatchItem(box.matchItems()[i], this);
    }

    this.cubeMatrix = this.factories.cubeMatrix(this.dimensions.length);

    for (MatchItem matchItem : this.matchItems) {
      for (String key : matchItem.keys()) {
        this.matchItemWithKeys.put(key,matchItem);
      }
    }
  }

  public int id() {
    return this.id;
  }

  public String code() {
    return this.code;
  }

  public String description() {
    return this.description;
  }

  public Dimension[] dimensions() {
    return this.dimensions;
  }

  public MatchItem[] matchItems() {
    return this.matchItems;
  }

  public MatchItem match(Map<String, String> dimensionValues) {
    if (dimensionValues.size() != this.dimensions.length) {
      return null;
    }

    String[] values = new String[this.dimensions.length];
    for (int i = 0; i < this.dimensions.length; i++) {
      Dimension dimension = this.dimensions[i];
      if (!dimensionValues.containsKey(dimension.code())) {
        return null;
      }
      values[i] = dimensionValues.get(dimension.code());
    }

    String[] keys = this.cubeMatrix.generateKeys(values, this.separator);

    for (String key : keys) {
      if (this.matchItemWithKeys.containsKey(key)) {
        return this.matchItemWithKeys.get(key);
      }
    }

    return null;
  }

}
