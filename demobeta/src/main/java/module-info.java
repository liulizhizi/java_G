module se7 {
  requires javafx.controls;
  requires javafx.fxml;
  requires com.opencsv;
  requires java.desktop;

  opens se7 to javafx.fxml;
  exports se7;
  exports se7.Function.utils;
  opens se7.Function.utils to javafx.fxml;
}