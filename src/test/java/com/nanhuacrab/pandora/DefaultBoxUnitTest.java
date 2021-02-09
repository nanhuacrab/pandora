package com.nanhuacrab.pandora;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultBoxUnitTest {

  private static final class TestMatchConfiguration {
    private DefaultBoxDTO box;
    private TestMatchConfigurationTestCase[] testCases;
  }

  private static final class TestMatchConfigurationTestCase {
    private Map<String, String> dimensionsValues;
    private String expected;
  }

  @Test
  public void testMatch() throws IOException {
    Gson gson = new Gson();
    Factories4Test factories = new Factories4Test();

    InputStream stream = this.getClass().getClassLoader().getResourceAsStream("testMatch.json");
    String[] configurations =
        gson.fromJson(CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8)), String[].class);

    for (String c : configurations) {

      stream = this.getClass().getClassLoader().getResourceAsStream(c);
      DefaultBoxUnitTest.TestMatchConfiguration configuration =
          gson.fromJson(CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8)),
              DefaultBoxUnitTest.TestMatchConfiguration.class);

      Box box = new DefaultBox(configuration.box, factories);
      System.out
          .println(String.format("---------------- box: %s %s ---------------------", box.code(), box.description()));

      for (TestMatchConfigurationTestCase testCase : configuration.testCases) {
        MatchItem matchedItem = box.match(testCase.dimensionsValues);
        String actual = matchedItem.configuration();
        System.out.println("+actual");
        System.out.println(actual);
        String expected = testCase.expected;
        Assert.assertEquals(expected, actual);
      }

    }

  }

  private final static class TestCheckDuplicationMatchItemsConfiguration {
    private DefaultBoxDTO box;
    private String expected;
  }

  @Test
  public void testCheckDuplicationMatchItems() throws IOException {
    Gson gson = new Gson();
    Factories4Test factories = new Factories4Test();

    InputStream stream = this.getClass().getClassLoader().getResourceAsStream("testCheckDuplicationMatchItems.json");
    String[] configurations =
        gson.fromJson(CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8)), String[].class);

    for (String c : configurations) {
      stream = this.getClass().getClassLoader().getResourceAsStream(c);
      DefaultBoxUnitTest.TestCheckDuplicationMatchItemsConfiguration configuration =
          gson.fromJson(CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8)),
              DefaultBoxUnitTest.TestCheckDuplicationMatchItemsConfiguration.class);

      Box box = new DefaultBox(configuration.box, factories);
      System.out
          .println(String.format("---------------- box: %s %s ---------------------", box.code(), box.description()));

      Map<String, List<MatchItemDTO>> duplicationMatchItems = Maps.newHashMap();

      box.checkDuplicationMatchItems().entrySet().forEach(o -> duplicationMatchItems.put(o.getKey(),
          o.getValue().stream().map(x -> x.data()).collect(Collectors.toList())));
      String actual = gson.toJson(duplicationMatchItems);

      stream = this.getClass().getClassLoader().getResourceAsStream(configuration.expected);
      String expected = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
      Assert.assertEquals(expected, actual);
    }
  }

}
