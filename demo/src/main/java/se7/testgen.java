package se7;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class testgen extends Application {

  private int textAreaCount = 0;

  @Override
  public void start(Stage primaryStage) {
    // 创建一个VBox作为根节点
    VBox root = new VBox();
    root.setSpacing(10);
    root.setPadding(new Insets(10));

    // 创建一个Button，当用户点击该Button时，会生成一个新的TextArea
    Button generateButton = new Button("生成TextArea");
    generateButton.setOnAction(event -> {
      TextArea textArea = new TextArea();
      textArea.setId("TextArea" + textAreaCount++);
      root.getChildren().add(textArea);
    });

    // 将Button添加到VBox中
    root.getChildren().add(generateButton);

    // 创建一个Scene，并将VBox作为根节点添加到Scene中
    Scene scene = new Scene(root, 600, 400);

    // 设置Stage的Scene，并显示Stage
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
