package se7;

import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class test extends Application {
  private double lastX, lastY;
  private double lastWidth, lastHeight;
  private double diffX, diffY;

  @Override
  public void start(Stage primaryStage) throws Exception {
    Pane pane = new Pane();



    TextArea textArea = new TextArea();

    textArea.setPrefColumnCount(20);
    textArea.setPrefRowCount(5);
    Circle circle = new Circle(50, Color.RED);
    circle.setCenterX(100);
    circle.setCenterY(100);

    DoubleBinding textAreaX = circle.centerXProperty().subtract(textArea.widthProperty());
    DoubleBinding textAreaY = circle.centerYProperty().subtract(textArea.heightProperty());


    textArea.layoutXProperty().bind(textAreaX);
    textArea.layoutYProperty().bind(textAreaY);

    pane.getChildren().addAll(circle, textArea);
    circle.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
      lastX = event.getSceneX();
      lastY = event.getSceneY();
      lastWidth = textArea.getWidth();
      lastHeight = textArea.getHeight();
      diffX = lastX - textArea.getLayoutX();
      diffY = lastY - textArea.getLayoutY();
      event.consume();
    });

    // Handle the mouse dragged event on the resize rectangle
    circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
      double newWidth = lastWidth + event.getSceneX() - lastX;
      double newHeight = lastHeight + event.getSceneY() - lastY;

      circle.setCenterX(event.getSceneX());
      circle.setCenterY(event.getSceneY());
      event.consume();
    });

    Scene scene = new Scene(pane, 600, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
