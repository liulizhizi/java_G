package se7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Callback1 extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    // 创建一个按钮，点击按钮打开子窗口
    Button button = new Button("打开子窗口");
    button.setOnAction(event -> {
      // 创建子窗口
      Stage childStage = new Stage();
      childStage.setTitle("子窗口");

      // 创建子窗口中的控件
      Button childButton = new Button("更改参数");
      childButton.setOnAction(event1 -> {
        // 更改参数值
        int newValue = 100;

        // 调用回调函数，将更改后的参数值传递给父窗口
        ((Callback<Integer, Void>) primaryStage.getUserData()).call(newValue);

        // 关闭子窗口
        childStage.close();
      });

      // 将子窗口中的控件添加到 StackPane 中
      StackPane childRoot = new StackPane(childButton);
      Scene childScene = new Scene(childRoot, 200, 200);

      // 设置子窗口的场景，并显示窗口
      childStage.setScene(childScene);
      childStage.show();
    });

    // 创建主窗口中的控件
    StackPane root = new StackPane(button);
    Scene scene = new Scene(root, 400, 400);

    // 将回调函数设置为主窗口的 UserData 属性
    primaryStage.setUserData((Callback<Integer, Void>) newValue -> {
      // 在回调函数中更改参数值
      System.out.println("参数值更改为：" + newValue);
      return null;
    });

    // 设置主窗口的场景，并显示窗口
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

