package com.nanhuacrab.pandora;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

public class KeyMatrixUnitTest {

  private static final class TestKeysConfiguration {
    private int[] dimensionsSize;
    private TestKeysConfigurationTestCase[] testCases;
  }

  private static final class TestKeysConfigurationTestCase {
    private Set<String>[] dimensionsValues;
    private String expected;
  }

  @Test
  public void testKeys() throws IOException {
    Gson gson = new Gson();

    InputStream stream = this.getClass().getClassLoader().getResourceAsStream("testKeys.json");
    KeyMatrixUnitTest.TestKeysConfiguration[] configurations =
        gson.fromJson(CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8)),
            KeyMatrixUnitTest.TestKeysConfiguration[].class);

    for (KeyMatrixUnitTest.TestKeysConfiguration configuration : configurations) {
      KeyMatrix keyMatrix = new KeyMatrix(configuration.dimensionsSize);

      System.out.println(String.format("---------------- dimensions size: %s ---------------------",
          gson.toJson(configuration.dimensionsSize)));
      System.out.println("+symbols:");
      System.out.println(gson.toJson(keyMatrix.symbols()));
      System.out.println("+matrix:");
      System.out.println(gson.toJson(keyMatrix.matrix()));

      for (TestKeysConfigurationTestCase testCase : configuration.testCases) {
        System.out.println(String.format("---------------- testCase: %s ---------------------", testCase.expected));

        String[] keys = keyMatrix.keys(testCase.dimensionsValues);
        String actual = gson.toJson(keys);
        System.out.println("+keys:");
        System.out.println(actual);

        stream = this.getClass().getClassLoader().getResourceAsStream(testCase.expected);
        String expected = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));

        Assert.assertEquals(expected, actual);
      }

    }
  }

}
