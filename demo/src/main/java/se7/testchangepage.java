package se7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class testchangepage extends Application {

  @Override
  public void start(Stage stage) {
    TabPane tabPane = new TabPane();

    Tab tab1 = new Tab("Page 1");
    tab1.setClosable(false);
    StackPane page1 = new StackPane();
    Label label1 = new Label("This is page 1.");
    Button button1 = new Button("Go to page 2");
    button1.setOnAction(event -> {
      tabPane.getSelectionModel().select(1);
    });
    page1.getChildren().addAll(label1, button1);
    tab1.setContent(page1);

    Tab tab2 = new Tab("Page 2");
    tab2.setClosable(false);
    StackPane page2 = new StackPane();
    Label label2 = new Label("This is page 2.");
    Button button2 = new Button("Go to page 1");
    button2.setOnAction(event -> {
      tabPane.getSelectionModel().select(0);
    });
    page2.getChildren().addAll(label2, button2);
    tab2.setContent(page2);

    tabPane.getTabs().addAll(tab1, tab2);

    VBox root = new VBox(10);
    HBox buttonBox = new HBox(10);
    Button backButton = new Button("Back");
    backButton.setOnAction(event -> {
      int selectedIndex = tabPane.getSelectionModel().getSelectedIndex();
      if (selectedIndex > 0) {
        tabPane.getSelectionModel().select(selectedIndex - 1);
      }
    });
    Button nextButton = new Button("Next");
    nextButton.setOnAction(event -> {
      int selectedIndex = tabPane.getSelectionModel().getSelectedIndex();
      if (selectedIndex < tabPane.getTabs().size() - 1) {
        tabPane.getSelectionModel().select(selectedIndex + 1);
      }
    });
    buttonBox.getChildren().addAll(backButton, nextButton);
    root.getChildren().addAll(tabPane, buttonBox);

    Scene scene = new Scene(root, 400, 400);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}