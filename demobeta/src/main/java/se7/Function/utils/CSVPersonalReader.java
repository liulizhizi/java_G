package se7.Function.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVPersonalReader{
  private static final char DELIMITER = ',';
  private static final char QUOTE = '"';

  public static List<String[]> parseCSV(String filePath) throws IOException {
    List<String[]> rows = new ArrayList<>();
    List<String> incompleteRow = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("E:\\java_G\\demo(2)\\demo\\control.csv"),
        StandardCharsets.UTF_8))) {
      String line;
      StringBuilder currentField = new StringBuilder();
      boolean insideQuotedField = false;

      while ((line = reader.readLine()) != null) {
        for (int i = 0; i < line.length(); i++) {
          char c = line.charAt(i);

          if (c == DELIMITER && !insideQuotedField) {
            currentField.append(c);
          } else if (c == QUOTE) {
            insideQuotedField = !insideQuotedField;
            currentField.append(c);
          } else {
            currentField.append(c);
          }

          if (i == line.length() - 1 && insideQuotedField) {
            // 当前行以引号结束，字段未完成
            currentField.append('\n');
            incompleteRow.add(currentField.toString());
            currentField.setLength(0); // 重置字段变量
          }
        }

        if (!insideQuotedField) {
          // 当前行完成，将字段添加到行列表
          incompleteRow.add(currentField.toString());
          rows.add(incompleteRow.toArray(new String[0]));
          incompleteRow.clear();
          currentField.setLength(0); // 重置字段变量
        }
      }

      return rows;
    }
  }

  public static void main(String[] args) {
    try {
      List<String[]> rows = parseCSV("E:\\java_G\\demo(2)\\demo\\control.csv");

      for (String[] row : rows) {

        String result = String.join("", row);
        String[] array = result.split(",");

        // 创建 ArrayList 并将字符串数组的元素添加到 ArrayList 中
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(array));

        // 输出 ArrayList
        for (String element : arrayList) {
          System.out.println(element);
        }


      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
