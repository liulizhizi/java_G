package se7;

import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.stage.Window;
import se7.GUI.NoteBook;

public class se7Application extends Application {
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
    Spinner<Double> spinner1 = new Spinner<>(0.0, 10000,200, 0.1 );
    spinner1.setEditable(true);
    spinner1.setLayoutX(500);
    spinner1.setLayoutY(200);
    Spinner<Double> spinner2 = new Spinner<>(0.0, 10000,200, 0.1 );
    spinner2.setEditable(true);
    spinner2.setLayoutX(500);
    spinner2.setLayoutY(230);

    AnchorPane hb = new AnchorPane();
    hb.prefHeight(400);
    hb.prefWidth(600);

    hb.getChildren().addAll(button, label1, spinner1, spinner2);
    for (int i = 0; i < 10; i++) {
      String str = String.valueOf(i);
      NoteBook notebook = new NoteBook("notebook" + str, str);
      notebook.setLayoutX(0);
      notebook.setLayoutY(0);




      Circle circle = new Circle(5, Color.WHITE);
      circle.setLayoutX(-10);
      circle.setLayoutY(-10);
      circle.setStroke(Color.BLACK);
      circle.setStrokeWidth(2);
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
        double newWidth = lastWidth + event.getSceneX() - lastX;
        double newHeight = lastHeight + event.getSceneY() - lastY;
        notebook.setPrefSize(newWidth, newHeight);
        circle.setLayoutX(notebook.getLayoutX() + notebook.getWidth());
        circle.setLayoutY(notebook.getLayoutY() + notebook.getHeight());
        event.consume();
      });

      // Handle the mouse pressed event on the root pane
      notebook.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
        lastX = event.getSceneX();
        lastY = event.getSceneY();
        diffX = lastX - notebook.getLayoutX();
        diffY = lastY - notebook.getLayoutY();
        event.consume();
      });

      // Handle the mouse dragged event on the root pane
      notebook.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
        double newX = event.getSceneX() - diffX;
        double newY = event.getSceneY() - diffY;
        notebook.setLayoutX(newX);
        notebook.setLayoutY(newY);
        circle.setLayoutX(newX + notebook.getWidth());
        circle.setLayoutY(newY + notebook.getHeight());
        event.consume();


      });

    }

    //button.setOnMouseClicked(mouseEvent -> {
    //      NoteBook nameTxt = new NoteBook ("设置提示语句，文本框获取焦点以后自动消失", "apple");
    //      nameTxt.setLayoutX(100);
    //      nameTxt.setLayoutY(100);
    //      nameTxt.prefHeight(220);//设置文本框宽
    //      nameTxt.prefWidth(480);//设置文本框高
    //      nameTxt.setPromptText("请输入姓名");//设置提示语句，文本框获取焦点以后自动消失
    //      hb.getChildren().addAll(nameTxt);
    //
    //    });

    Scene scene = new Scene(hb, 500, 500);
    stage.setScene(scene);
    Platform.runLater(()->{
      Node notebook1 = scene.lookup("#notebook1");
      NoteBook noteBook1 = (NoteBook) notebook1;
      System.out.println(noteBook1);
      //notebook1.layoutXProperty().bind(spinner1.valueProperty());
      //      notebook1.layoutYProperty().bind(spinner2.valueProperty());
      noteBook1.setLayoutX(100);
      noteBook1.setLayoutY(100);
      noteBook1.setPrefWidth(200);
      noteBook1.setPrefHeight(200);


    });


    stage.setTitle("Hello!");

    stage.show();
  }




  public static void main(String[] args) {
    launch();
  }
}