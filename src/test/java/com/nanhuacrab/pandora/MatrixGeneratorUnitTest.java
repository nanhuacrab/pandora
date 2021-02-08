package com.nanhuacrab.pandora;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MatrixGeneratorUnitTest {

  @Test
  public void testGenerator() throws IOException {
    Gson gson = new Gson();
    for (int dimensionSize = 1; dimensionSize <= 5; dimensionSize++) {
      MatrixGenerator matrixGenerator = new MatrixGenerator(dimensionSize);
      matrixGenerator.generate();
      int[][] matrix = matrixGenerator.orderedMatrix();
      String actual = gson.toJson(matrix);



      System.out.println(String.format("---------------- dimension size: %s ---------------------", dimensionSize));
      System.out.println("+未排序的 matrix：");
      System.out.println(gson.toJson(matrixGenerator.matrix()));
      System.out.println("+每个优先级的数量：");
      int[] matrixPrioritySize = matrixGenerator.matrixPrioritySize();
      for (int i = 0; i < matrixPrioritySize.length; i++) {
        System.out.println(String.format("优先级为 %s 的数量 %s", i, matrixPrioritySize[i]));
      }
      System.out.println("+每个优先级的坐标范围：");
      int[] matrixPriorityStartIndex = matrixGenerator.matrixPriorityStartIndex();
      for (int i = matrixPriorityStartIndex.length - 1; i > 0; i--) {
        System.out.println(
            String.format("优先级为 %s 的坐标范围 %s~%s", i, matrixPriorityStartIndex[i], matrixPriorityStartIndex[i - 1] - 1));
      }
      System.out.println(String.format("优先级为 0 的坐标范围 %s~%s", matrixPriorityStartIndex[0], matrixPriorityStartIndex[0]));



      String fileName = String.format("CubeMatrix_%s.json", dimensionSize);
      System.out.println(fileName);
      InputStream stream = this.getClass().getClassLoader().getResourceAsStream(fileName);
      String expected = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));

      Assert.assertEquals(expected, actual);
    }

  }

}
