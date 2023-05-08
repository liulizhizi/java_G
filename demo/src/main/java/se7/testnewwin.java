package se7;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.util.Callback;
import se7.GUI.dragwin;
import se7.GUI.dragwin.Control_List;

public class testnewwin extends Application {

  private String callbackID;

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
    //Stage newWindow = new Stage();
    //    Scene newWindowScene = new Scene(newWindowRoot, 300, 200);
    //    newWindow.initStyle(StageStyle.UTILITY);
    //    newWindow.setScene(newWindowScene);
    dragwin pa = new dragwin(primaryStage);
    primaryStage.setUserData((Callback<String, Void>) newValue -> {
      // 在回调函数中更改参数值
      System.out.println("参数值更改为：" + newValue);
      this.callbackID = newValue;
      return null;
    });


    newWindowButton.setOnAction(event -> {
      if (pa.isShowing()) {
        pa.close();
        newWindowButton.setText("查看控件");
        System.out.println("Stage is open");
      } else {

        pa.show();
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

