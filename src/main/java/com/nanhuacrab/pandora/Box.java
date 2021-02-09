package com.nanhuacrab.pandora;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 魔方
 */
public class Box {

  private static final class MyDimension implements Dimension {
    private final Box ownerBox;
    private final DimensionDTO data;

    private MyDimension(DimensionDTO dimension, Box box) {
      this.ownerBox = box;
      this.data = dimension;
    }

    @Override
    public String code(){
      return this.data.code();
    }

    private String description() {
      return this.data.description();
    }

    private boolean nullable(){
      return this.data.nullable();
    }

    @Override
    public DimensionDTO data() {
      return this.data;
    }

    @Override
    public Box box() {
      return this.ownerBox;
    }
  }

  private static final class MyMatchItem implements MatchItem {
    // private final DimensionValue[] dimensionValue;
    private final String configuration;
    private final String[] keys;
    private final Box ownerBox;
    private final MatchItemDTO data;

    public MyMatchItem(MatchItemDTO matchItem, Box box) {
      this.ownerBox = box;
      this.data = matchItem;

      this.configuration = matchItem.configuration();

      int[] dimensionsSize = new int[this.ownerBox.dimensionsAll.length];
      Set<String>[] dimensionsValues = new Set[this.ownerBox.dimensionsAll.length];
      for (int i = 0; i < this.ownerBox.dimensionsAll.length; i++) {
        String dimensionCode = this.ownerBox.dimensionsAll[i].code();
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

    @Override
    public MatchItemDTO data(){
      return this.data;
    }

  }

  private final Dimension[] dimensionsAll;
  private final Dimension[] dimensionsWithNullable; // 配置可为空的维度
  private final Dimension[] dimensionsWithNotNull; // 配置不可为空的维度

  private final Map<String, MyDimension> dimensionsByCode = Maps.newHashMap();
  private final MatchItem[] matchItems;
  public static final String SEPARATOR = ",";
  private final CubeMatrix cubeMatrix;
  private final Map<String, MatchItem> matchItemWithKeys = Maps.newHashMap();
  private final Factories factories;
  private final BoxDTO data;
  private final String code;
  private final String separator;

  public Box(BoxDTO box, Factories factories) {

    this.factories = factories;
    this.data = box;
    this.code = box.code();
    this.separator = box.separator();

    int dimensionsSize = box.dimensions().length;
    List<Dimension> dimensionsWithNullable = Lists.newArrayListWithCapacity(dimensionsSize);
    List<Dimension> dimensionsWithNotNull = Lists.newArrayListWithCapacity(dimensionsSize);
    this.dimensionsAll = new Dimension[box.dimensions().length];
    for (int i = 0; i < dimensionsSize; i++) {
      MyDimension dimension = new MyDimension(box.dimensions()[i], this);
      if(dimension.nullable()) {
        dimensionsWithNullable.add(dimension);
      }else{
        dimensionsWithNotNull.add(dimension);
      }
      this.dimensionsByCode.put(dimension.code(), dimension);
    }
    this.dimensionsWithNullable = dimensionsWithNullable.stream().toArray(Dimension[]::new);
    this.dimensionsWithNotNull = dimensionsWithNotNull.stream().toArray(Dimension[]::new);

    System.arraycopy(this.dimensionsWithNotNull,0,this.dimensionsAll,0,this.dimensionsWithNotNull.length);
    System.arraycopy(this.dimensionsWithNullable,0,this.dimensionsAll,this.dimensionsWithNotNull.length,this.dimensionsWithNullable.length);

    this.matchItems = new MatchItem[box.matchItems().length];
    for (int i = 0; i < this.matchItems.length; i++) {
      this.matchItems[i] = new MyMatchItem(box.matchItems()[i], this);
    }

    this.cubeMatrix = this.factories.cubeMatrix(this.dimensionsWithNullable.length);

    for (MatchItem matchItem : this.matchItems) {
      for (String key : matchItem.keys()) {
        this.matchItemWithKeys.put(key,matchItem);
      }
    }
  }

  public String code() {
    return this.code;
  }

  public String description() {
    return this.data.description();
  }

  public Dimension[] dimensions() {
    return this.dimensionsAll;
  }

  public MatchItem[] matchItems() {
    return this.matchItems;
  }

  public MatchItem match(Map<String, String> dimensionValues) {

    String prefix = StringUtils.EMPTY;
    for (Dimension dimension : this.dimensionsWithNotNull) {
      if (!dimensionValues.containsKey(dimension.code())) {
        return null;
      }
      prefix += dimensionValues.get(dimension.code()) + this.separator;
    }

    String[] values = new String[this.dimensionsWithNullable.length];
    for (int i = 0; i < this.dimensionsWithNullable.length; i++) {
      Dimension dimension = this.dimensionsWithNullable[i];
      if (!dimensionValues.containsKey(dimension.code())) {
        return null;
      }
      values[i] = dimensionValues.get(dimension.code());
    }

    String[] keys = this.cubeMatrix.generateKeys(values, this.separator);

    for (String key : keys) {
      String keyWithPrefix = prefix+key;
      if (this.matchItemWithKeys.containsKey(keyWithPrefix)) {
        return this.matchItemWithKeys.get(keyWithPrefix);
      }
    }

    return null;
  }

}
