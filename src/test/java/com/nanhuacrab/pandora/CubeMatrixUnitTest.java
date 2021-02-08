package com.nanhuacrab.pandora;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CubeMatrixUnitTest {

  private static final class TestGenerateKeysConfiguration {
    private String[] dimensionValues;
    private String expected;
  }

  @Test
  public void testGenerateKeys() throws IOException {

    Gson gson = new Gson();

    InputStream stream = this.getClass().getClassLoader().getResourceAsStream("testGenerateKeys.json");
    TestGenerateKeysConfiguration[] configurations = gson.fromJson(
        CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8)), TestGenerateKeysConfiguration[].class);

    for (TestGenerateKeysConfiguration configuration : configurations) {

      CubeMatries cubeMatries = new CubeMatries();
      CubeMatrix cubeMatrix = cubeMatries.cubeMatrx(3);
      String[] keys = cubeMatrix.generateKeys(configuration.dimensionValues);
      String actual = gson.toJson(keys);

      System.out.println(configuration.expected);
      stream = this.getClass().getClassLoader().getResourceAsStream(configuration.expected);
      String expected = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));

      Assert.assertEquals(expected, actual);
    }

  }

}
