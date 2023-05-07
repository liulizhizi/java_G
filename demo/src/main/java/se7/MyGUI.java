package se7;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyGUI extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    // 创建第一个窗口
    Stage parentStage = new Stage();
    parentStage.initStyle(StageStyle.DECORATED);
    parentStage.setTitle("Parent Stage");

    // 创建第二个窗口
    Stage childStage = new Stage();
    childStage.initStyle(StageStyle.DECORATED);
    childStage.setTitle("Child Stage");

    // 将第二个窗口关联到第一个窗口
    childStage.initOwner(parentStage);

    // 创建按钮来触发显示子窗口
    Button showChildBtn = new Button("Show Child");
    showChildBtn.setOnAction(e -> {
      childStage.show();
    });

    // 将按钮添加到第一个窗口
    VBox parentRoot = new VBox(10);
    parentRoot.setPadding(new Insets(10));
    parentRoot.getChildren().add(showChildBtn);
    parentStage.setScene(new Scene(parentRoot, 200, 100));
    parentStage.show();

    // 将一些内容添加到第二个窗口
    VBox childRoot = new VBox(10);
    childRoot.setPadding(new Insets(10));
    childRoot.getChildren().add(new Button("Button 1"));
    childRoot.getChildren().add(new Button("Button 2"));
    childStage.setScene(new Scene(childRoot, 200, 100));
  }

  public static void main(String[] args) {
    launch(args);
  }
}

