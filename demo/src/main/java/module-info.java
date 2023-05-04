module se7 {
  requires javafx.controls;
  requires javafx.fxml;

  opens se7 to javafx.fxml;
  exports se7;
}