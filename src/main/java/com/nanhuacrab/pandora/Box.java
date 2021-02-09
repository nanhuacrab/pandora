package com.nanhuacrab.pandora;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

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
    private final Set<String> keys = Sets.newHashSet();
    private final Box ownerBox;

    public MyMatchItem(MatchItemDTO matchItem, Box box) {
      this.ownerBox = box;
      this.id = matchItem.id();
      this.description = matchItem.description();
      this.configuration = matchItem.configuration();

      for (int i = 0; i < this.ownerBox.dimensions.length; i++) {

      }

    }

    public int id() {
      return this.id;
    }

    public String description() {
      return this.description;
    }

    public DimensionValue[] dimensionValue() {
      return this.dimensionValue;
    }

    public String configuration() {
      return this.configuration;
    }

    Box box();

    Set<String> keys();
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

  public Box(int id, String code, String description, DimensionDTO[] dimensions, MatchItemDTO[] matchItems,
      Factories factories) {
    this(id, code, description, dimensions, matchItems, SEPARATOR, factories);
  }

  public Box(int id, String code, String description, DimensionDTO[] dimensions, MatchItemDTO[] matchItems,
      String separator, Factories factories) {
    this.id = id;
    this.code = code;
    this.description = description;

    this.dimensions = new Dimension[dimensions.length];
    for (int i = 0; i < this.dimensions.length; i++) {
      MyDimension dimension = new MyDimension(dimensions[i], this);
      this.dimensions[i] = dimension;
      this.dimensionsByCode.put(dimension.code, dimension);
    }

    this.matchItems = new MatchItem[matchItems.length];
    for (int i = 0; i < this.matchItems.length; i++) {
      this.matchItems[i] = new MyMatchItem(matchItems[i], this);
    }

    this.separator = separator;
    this.factories = factories;

    this.cubeMatrix = this.factories.cubeMatrix(this.dimensions.length);
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
