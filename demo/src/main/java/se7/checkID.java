package se7;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class checkID extends Application {

  private VBox root;

  @Override
  public void start(Stage primaryStage) {
    // 创建一个VBox作为根节点
    root = new VBox();
    root.setSpacing(10);
    root.setPadding(new Insets(10));

    // 创建一个Label和一个TextField，用于输入要查询的控件ID
    Label idLabel = new Label("控件ID：");
    TextField idField = new TextField();
    idField.setId("idF");
    idField.setPromptText("请输入控件ID");

    // 创建一个Button，当用户点击该Button时，会查询控件ID是否已经存在
    Button checkButton = new Button("查询");
    checkButton.setOnAction(event -> {
      String controlId = idField.getText();
      if (controlId == null || controlId.isEmpty()) {
        showResult("请输入控件ID");
        return;
      }
      if (isControlIdExist(controlId)) {
        showResult("控件ID已经存在");
      } else {
        showResult("控件ID不存在");
      }
    });

    // 将Label、TextField和Button添加到VBox中
    root.getChildren().addAll(idLabel, idField, checkButton);

    // 创建一个Scene，并将VBox作为根节点添加到Scene中
    Scene scene = new Scene(root, 600, 400);

    // 设置Stage的Scene，并显示Stage
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private boolean isControlIdExist(String controlId) {
    // 在这里添加控件ID是否已经存在的判断逻辑，这里只是一个示例
    return root.lookup("#" + controlId) != null;
  }

  private void showResult(String result) {
    // 在这里添加显示查询结果的逻辑，这里只是一个示例
    Label resultLabel = new Label(result);
    root.getChildren().add(resultLabel);
  }

  public static void main(String[] args) {
    launch(args);
  }

}