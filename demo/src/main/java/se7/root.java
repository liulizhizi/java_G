package se7;

import java.io.File;
import java.util.Arrays;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class root extends Application {

  private TextField currentDirectoryField;
  private TreeView<String> fileTreeView;

  @Override
  public void start(Stage primaryStage) {
    BorderPane root = new BorderPane();
    root.setPadding(new Insets(10));

    // 创建顶部面板
    HBox topPanel = new HBox();
    topPanel.setSpacing(10);
    topPanel.setPadding(new Insets(10, 0, 0, 0));

    Label currentDirectoryLabel = new Label("当前目录:");
    currentDirectoryField = new TextField(System.getProperty("user.home"));
    currentDirectoryField.setEditable(false);

    Button chooseDirectoryButton = new Button("选择目录");
    chooseDirectoryButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        if (selectedDirectory != null) {
          currentDirectoryField.setText(selectedDirectory.getAbsolutePath());
          updateTreeView(selectedDirectory);// 选择目录
        }
      }
    });

    topPanel.getChildren().addAll(currentDirectoryLabel, currentDirectoryField, chooseDirectoryButton);

    // 创建目标树
    fileTreeView = new TreeView<String>();
    fileTreeView.setShowRoot(false);

    // 将顶部面板和目标树添加到根面板
    root.setTop(topPanel);
    root.setCenter(fileTreeView);

    // 显示窗口
    Scene scene = new Scene(root, 800, 600);
    primaryStage.setScene(scene);
    primaryStage.setTitle("文件资源管理器");
    primaryStage.show();

    // 初始化目标树
    updateTreeView(new File(System.getProperty("user.home")));
  }

  /**
   * 更新目标树
   *
   * @param directory
   *            要显示的目录
   */
  private void updateTreeView(File directory) {
    TreeItem<String> rootItem = new TreeItem<String>(directory.getAbsolutePath());

    // 添加子目录
    File[] subDirectories = directory.listFiles(file -> file.isDirectory());
    Arrays.sort(subDirectories);
    for (File subDirectory : subDirectories) {
      TreeItem<String> subDirectoryItem = new TreeItem<String>(subDirectory.getName());
      rootItem.getChildren().add(subDirectoryItem);
    }

    // 添加文件
    File[] files = directory.listFiles(file -> !file.isDirectory());
    Arrays.sort(files);
    for (File file : files) {
      TreeItem<String> fileItem = new TreeItem<String>(file.getName());
      rootItem.getChildren().add(fileItem);
    }

    fileTreeView.setRoot(rootItem);
  }

  public static void main(String[] args) {
    launch(args);
  }

}