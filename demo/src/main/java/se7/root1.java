package se7;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class root1 extends Application {

  private Label filePathLabel;

  @Override
  public void start(Stage primaryStage) {
    VBox root = new VBox();
    root.setSpacing(10);
    root.setPadding(new Insets(10));

    Button chooseFileButton = new Button("选择文件");
    chooseFileButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
          filePathLabel.setText(selectedFile.getAbsolutePath());
        }
      }
    });

    filePathLabel = new Label();

    root.getChildren().addAll(chooseFileButton, filePathLabel);

    Scene scene = new Scene(root, 400, 300);
    primaryStage.setScene(scene);
    primaryStage.setTitle("文件选择器");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}



