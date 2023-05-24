package se7.Function.utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class CSVReader {
  public static ObservableList<String[]> readCSV(String filePath) throws IOException {
    ObservableList<String[]> data = FXCollections.observableArrayList();
    BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
    String row;
    while ((row = csvReader.readLine()) != null) {
      String[] fields = row.split(",");
      data.add(fields);
    }
    csvReader.close();
    return data;
  }
}