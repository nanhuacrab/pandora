package com.nanhuacrab.pandora.tree.prefix;


import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nanhuacrab.pandora.Box;
import com.nanhuacrab.pandora.BoxDTO;
import com.nanhuacrab.pandora.DefaultBox;
import com.nanhuacrab.pandora.DefaultBoxDTO;
import com.nanhuacrab.pandora.Factories4Test;
import com.nanhuacrab.pandora.MatchItem;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;

public class PrefixTreeUnitTest {

  private static final class Configuration {
    private String box;
    private String expected;
    private ConfigurationDimensionValues[] dimensionValues;
  }

  private static final class ConfigurationDimensionValues {
    private Map<String, String> dimensionValues;
    private String expected;
  }

  @Test
  public void test4PrefixTree() throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    Factories4Test factories = new Factories4Test();

    InputStream stream =
        this.getClass().getClassLoader().getResourceAsStream("PrefixTreeUnitTest.test4PrefixTree.json");
    Configuration configuration =
        gson.fromJson(CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8)), Configuration.class);


    stream = this.getClass().getClassLoader().getResourceAsStream(configuration.box);
    BoxDTO boxDTO =
        gson.fromJson(CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8)), DefaultBoxDTO.class);

    Box box = new DefaultBox(boxDTO, factories);

    PrefixTree prefixTree = new PrefixTree(box);

    String actual = gson.toJson(prefixTree.root());
    stream = this.getClass().getClassLoader().getResourceAsStream(configuration.expected);
    String expected = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
    Assert.assertEquals(expected, actual);

    for (ConfigurationDimensionValues dimensionValues : configuration.dimensionValues) {
      MatchItem matchItem = prefixTree.match(dimensionValues.dimensionValues);
      actual = Objects.isNull(matchItem) ? StringUtils.EMPTY : gson.toJson(matchItem.data());
      stream = this.getClass().getClassLoader().getResourceAsStream(dimensionValues.expected);
      expected = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
      Assert.assertEquals(expected, actual);
    }

  }

}
