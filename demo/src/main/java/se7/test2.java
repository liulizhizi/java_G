package se7;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class test2 extends Application {

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("File Explorer");

    TreeItem<String> rootItem = new TreeItem<>("Root");
    rootItem.setExpanded(true);

    // 获取计算机的所有根目录
    List<File> rootDirectories = Arrays.asList(File.listRoots());
    for (File root : rootDirectories) {
      TreeItem<String> rootDir = new TreeItem<>(root.getAbsolutePath());
      rootItem.getChildren().add(rootDir);
      createTree(rootDir, root);
    }

    TreeView<String> treeView = new TreeView<>(rootItem);

    VBox vbox = new VBox(treeView);
    vbox.setPadding(new Insets(10));
    primaryStage.setScene(new Scene(vbox, 400, 400));
    primaryStage.show();
  }

  private void createTree(TreeItem<String> parent, File file) {
    File[] files = file.listFiles();
    if (files == null) {
      return;
    }
    for (File childFile : files) {
      if (childFile.isDirectory()) {
        TreeItem<String> directory = new TreeItem<>(childFile.getName());
        parent.getChildren().add(directory);
        createTree(directory, childFile);
      } else {
        parent.getChildren().add(new TreeItem<>(childFile.getName()));
      }
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}