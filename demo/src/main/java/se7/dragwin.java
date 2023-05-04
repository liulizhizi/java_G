package se7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class dragwin extends Application {
  private double xOffset = 0;
  private double yOffset = 0;

  @Override
  public void start(Stage primaryStage) throws Exception {
    // 创建一个空的 Pane
    Pane pane = new Pane();

    // 创建一个按钮，用于关闭窗口
    Button closeButton = new Button("关闭");
    closeButton.setOnAction(event -> primaryStage.close());
    closeButton.setLayoutX(10);
    closeButton.setLayoutY(10);

    // 设置无标题窗口样式
    primaryStage.initStyle(StageStyle.UNDECORATED);

    // 添加窗口拖拽事件监听器
    pane.setOnMousePressed((MouseEvent event) -> {
      xOffset = event.getSceneX();
      yOffset = event.getSceneY();
    });
    pane.setOnMouseDragged((MouseEvent event) -> {
      primaryStage.setX(event.getScreenX() - xOffset);
      primaryStage.setY(event.getScreenY() - yOffset);
    });

    // 将按钮和 Pane 添加到场景中
    pane.getChildren().add(closeButton);
    Scene scene = new Scene(pane, 300, 200);
    scene.setFill(Color.TRANSPARENT);
    primaryStage.setScene(scene);

    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

