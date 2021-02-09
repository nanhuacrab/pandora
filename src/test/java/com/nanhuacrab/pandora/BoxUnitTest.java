package com.nanhuacrab.pandora;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class BoxUnitTest {

  private static final class TestMatchConfiguration {
    private Box4Test box;
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
      BoxUnitTest.TestMatchConfiguration configuration =
          gson.fromJson(CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8)),
              BoxUnitTest.TestMatchConfiguration.class);

      Box box = new Box(configuration.box, factories);
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
}
