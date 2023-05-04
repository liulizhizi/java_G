package se7;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class testclc extends Application {

  @Override
  public void start(Stage primaryStage) {
    // 创建一个StackPane作为根节点
    StackPane root = new StackPane();

    // 创建一个ImageView来显示图片
    ImageView imageView = new ImageView();
    imageView.setImage(new Image("D:\\360MoveData\\Users\\13521\\Desktop\\IMDP_Group8-main\\IMDP\\img\\20230417_173502_010.jpeg"));

    // 给ImageView设置点击事件，当用户点击图片时，会弹出文件选择器让用户选择要打开的文件
    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        getHostServices().showDocument("D:/360MoveData/Users/13521/Desktop/cankao.txt");



      }
    });

    // 将ImageView添加到StackPane中
    root.getChildren().add(imageView);

    // 创建一个Scene，并将StackPane作为根节点添加到Scene中
    Scene scene = new Scene(root, 600, 400);

    // 设置Stage的Scene，并显示Stage
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}

