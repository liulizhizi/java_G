package se7;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class packagedargwin extends Application {
  private double xOffset = 0;
  private double yOffset = 0;

  private Stage stage;

  public packagedargwin() {
    // 创建无标题窗口
    this.stage = new Stage();
    this.stage.initStyle(StageStyle.UNDECORATED);

    this.stage.setResizable(false);

    // 创建一个空的 Pane
    Pane pane = new Pane();

    // 创建一个按钮，用于关闭窗口
    Button closeButton = new Button("关闭");
    closeButton.setOnAction(event -> this.stage.close());
    closeButton.setLayoutX(10);
    closeButton.setLayoutY(10);

    // 添加窗口拖拽事件监听器
    pane.setOnMousePressed((MouseEvent event) -> {
      xOffset = event.getSceneX();
      yOffset = event.getSceneY();
    });
    pane.setOnMouseDragged((MouseEvent event) -> {
      this.stage.setX(event.getScreenX() - xOffset);
      this.stage.setY(event.getScreenY() - yOffset);
    });

    // 将按钮和 Pane 添加到场景中
    pane.getChildren().add(closeButton);
    Scene scene = new Scene(pane, 300, 200);
    scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // 导入 CSS 文件
    scene.setFill(Color.TRANSPARENT);
    this.stage.setScene(scene);
  }

  public void show() {
    this.stage.show();
  }

  public void close() {
    this.stage.close();
  }

  public final boolean isShowing() {
    return this.stage.isShowing();
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // 在 start() 方法中不需要实现任何逻辑
    packagedargwin pa = new packagedargwin();
    pa.show();
  }
}
