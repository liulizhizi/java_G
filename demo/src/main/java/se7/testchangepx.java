package se7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class testchangepx extends Application {

  private TextArea textArea;

  @Override
  public void start(Stage stage) {
    // Create the components
    textArea = new TextArea();
    textArea.setStyle("-fx-font-size: 16px;");
    Slider slider = new Slider(10, 40, 16);
    Label label = new Label("Font Size:");

    // Bind the slider to the text area font size
    slider.valueProperty().addListener((observable, oldValue, newValue) -> {
      textArea.setStyle("-fx-font-size: " + newValue.intValue() + "px;");
    });

    // Create the layout
    BorderPane root = new BorderPane();
    root.setTop(label);
    root.setCenter(textArea);
    root.setBottom(slider);

    // Create the scene and show the stage
    Scene scene = new Scene(root, 400, 400);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}