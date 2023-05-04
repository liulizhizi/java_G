package se7;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import se7.GUI.NoteBook;

public class test1 extends Application {
  private double lastX, lastY;
  private double lastWidth, lastHeight;
  private double diffX, diffY;
  @Override
  public void start(Stage stage) throws IOException {
    //FXMLLoader fxmlLoader = new FXMLLoader(se7Application.class.getResource("GUI/hello-view.fxml"));
    //Parent root = fxmlLoader.load();
    //Scene scene = new Scene(root, 320, 240);
    Button button = new Button("create NoteBook");

    button.setOnAction(e -> {
      getHostServices().showDocument("D:/360MoveData/Users/13521/Desktop/cankao.txt");
    });
    button.setLayoutX(10);
    button.setLayoutY(600);

    Label label1 = new Label("Name:");

    Pane hb = new Pane();
    hb.prefHeight(400);
    hb.prefWidth(600);

    hb.getChildren().addAll(button, label1);
    for (int i = 0; i < 10; i++) {
      String str = String.valueOf(i);
      NoteBook notebook = new NoteBook("notebook" + str, str);
      notebook.setLayoutX(0);
      notebook.setLayoutY(0);


      Circle circle = new Circle(10, Color.WHITE);
      circle.setCenterX(100);
      circle.setCenterY(100);
      circle.setStroke(Color.BLACK);
      circle.setStrokeWidth(2);


      DoubleBinding textAreaX = circle.centerXProperty().subtract(notebook.widthProperty());
      DoubleBinding textAreaY = circle.centerYProperty().subtract(notebook.heightProperty());

      notebook.layoutXProperty().bind(textAreaX);
      notebook.layoutYProperty().bind(textAreaY);

      hb.getChildren().addAll(circle, notebook);

      circle.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
        lastX = event.getSceneX();
        lastY = event.getSceneY();
        lastWidth = notebook.getWidth();
        lastHeight = notebook.getHeight();
        diffX = lastX - notebook.getLayoutX();
        diffY = lastY - notebook.getLayoutY();
        event.consume();
      });

      // Handle the mouse dragged event on the resize rectangle
      circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
        circle.setCenterX(event.getSceneX());
        circle.setCenterY(event.getSceneY());
        event.consume();
      });

    }

    Scene scene = new Scene(hb, 500, 500);
    stage.setScene(scene);

    stage.setTitle("Hello!");

    stage.show();
  }




  public static void main(String[] args) {
    launch();
  }
}