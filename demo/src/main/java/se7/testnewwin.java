package se7;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class testnewwin extends Application {

  @Override
  public void start(Stage primaryStage) {
    // 创建一个VBox，用于放置Button
    VBox root = new VBox();
    root.setSpacing(10);
    root.setAlignment(Pos.CENTER);

    // 创建一个Button，当用户点击该Button时，会生成一个无标题窗口
    Button newWindowButton = new Button("查看控件");
    BorderPane newWindowRoot = new BorderPane();
    newWindowRoot.setStyle("-fx-background-color: white;");

    // 创建一个矩形，用于演示新窗口的效果
    Rectangle rectangle = new Rectangle(100, 100, Color.RED);
    rectangle.setStroke(Color.BLACK);
    rectangle.setStrokeWidth(2);
    newWindowRoot.setCenter(rectangle);

    // 创建一个新的Stage，并设置其Scene和样式
    Stage newWindow = new Stage();
    Scene newWindowScene = new Scene(newWindowRoot, 300, 200);
    newWindow.initStyle(StageStyle.UTILITY);
    newWindow.setScene(newWindowScene);

    newWindowButton.setOnAction(event -> {
      if (newWindow.isShowing()) {
        newWindow.close();
        newWindowButton.setText("查看控件");
        System.out.println("Stage is open");
      } else {
        newWindow.show();
        newWindowButton.setText("关闭查看");
        System.out.println("Stage is not open");
      }
    });

    // 将Button添加到VBox中
    root.getChildren().add(newWindowButton);

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

