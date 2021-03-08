package com.nanhuacrab.pandora.tree.prefix;

import com.google.common.collect.Maps;
import com.nanhuacrab.pandora.Box;
import com.nanhuacrab.pandora.MatchItem;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 前缀数
 */
public class PrefixTree {

  public static class Node {
    /**
     * 假设 node代表根，children
     */
    private Map<String, Node> children = Maps.newHashMap();
    private Node wildcardNode; // 通配节点
    private final int deep; // 树深度
    private int maxMatchCount; // 最大可能匹配的数量
    private final String path;

    /*
     * 是否叶子节点，可以通过 children 和 wildcardNode 来判断是否 leaf
     */
    public boolean leaf() {
      if (MapUtils.isNotEmpty(this.children)) {
        return false;
      }
      if (Objects.nonNull(this.wildcardNode)) {
        return false;
      }
      return true;
    }

    private MatchItem matchItem; // 叶子节点对应的配置信息

    private Node() {
      this("ROOT", null);
    }

    private Node(String path, Node parent) {

      if (Objects.isNull(parent)) {
        this.path = ObjectUtils.defaultIfNull(path, StringUtils.EMPTY);
        this.deep = 0;
      } else {
        this.path = String.format("%s->%s", parent.path, ObjectUtils.defaultIfNull(path, StringUtils.EMPTY));
        this.deep = parent.deep + 1;
      }
    }
  }

  private Node root;
  private final Box box;

  public PrefixTree(Box box) {
    this.box = box;
    this.root = new Node();
    this.buildTree();
  }

  Node root() {
    return this.root;
  }

  private void buildTree() {
    for (MatchItem matchItem : this.box.matchItems()) {
      for (String[] dimensionValues : matchItem.keyMatrix()) {

        Node[] nodes = new Node[this.box.dimensionSize()];
        int maxMatchCount = 0;
        Node parent = this.root;

        for (int i = 0; i < dimensionValues.length; i++) {
          String dimensionValue = dimensionValues[i];
          if (!this.box.emptySymbol().equals(dimensionValue)) { // 精确匹配 children
            maxMatchCount++;
            nodes[i] = parent.children.get(dimensionValue);
          } else {
            nodes[i] = parent.wildcardNode; // 通配匹配 wildcardNode
          }

          if (null == nodes[i]) { // 新建
            nodes[i] = new Node(dimensionValue, parent); // 设置树的深度
            if (!this.box.emptySymbol().equals(dimensionValue)) {
              parent.children.put(dimensionValue, nodes[i]);
            } else {
              parent.wildcardNode = nodes[i];
            }
          }

          parent = nodes[i];
        }

        for (Node n : nodes) {
          n.maxMatchCount = Math.max(n.maxMatchCount, maxMatchCount);
        }
      }
    }
  }


}
