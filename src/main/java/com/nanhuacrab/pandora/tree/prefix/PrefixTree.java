package com.nanhuacrab.pandora.tree.prefix;

import com.google.common.collect.Maps;
import com.nanhuacrab.pandora.Box;
import com.nanhuacrab.pandora.MatchItem;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Stack;

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

  /**
   * 叶子节点对应的配置信息
   */
  private Map<Node, MatchItem> matchItems = Maps.newHashMap();

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

        this.matchItems.put(parent, matchItem);

        for (Node n : nodes) {
          n.maxMatchCount = Math.max(n.maxMatchCount, maxMatchCount);
        }

      }
    }
  }

  public MatchItem match(Map<String, String> dimensionValues) {
    String[] values = this.box.dimensionValues(dimensionValues);
    int dimensionSize = this.box.dimensionSize();

    Stack<Node>[] results = new Stack[dimensionSize + 1]; // 栈数组，[i] 表示 最大匹配i个维度值的 栈
    for (int i = 0; i < results.length; i++) {
      results[i] = new Stack();
    }
    results[dimensionSize].push(this.root); // 根，默认全部匹配

    for (int i = dimensionSize; i >= 0; i--) {
      Stack<Node> stack = results[i];

      while (!stack.empty()) { // 弹栈
        Node node = stack.pop();

        if (node.leaf()) { // 如果是叶子节点，说明找到
          return this.matchItems.get(node);
        }

        String dimensionValue = values[node.deep]; // 需要匹配的维度值
        Node child = node.children.get(dimensionValue);

        if (null != child) { // 精确匹配
          results[child.maxMatchCount].push(child); // 压栈，最大匹配 maxMatchCount 个维度值的 栈
        }

        if (null != node.wildcardNode) { // 通配 匹配
          results[node.wildcardNode.maxMatchCount].push(node.wildcardNode); // 压栈，最大匹配 maxMatchCount 个维度值的 栈
        }

      }
    }

    return null;
  }

}
