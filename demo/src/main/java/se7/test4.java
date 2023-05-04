package se7;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import se7.GUI.NoteBook;

public class test4 extends Application {

  private TableView<Person> table;
  private Popup popup;

  @Override
  public void start(Stage primaryStage) {

    Button openButton = new Button("Open");
    openButton.setOnAction(event -> {
      Platform.runLater(()->{
        showPopup();
      });
    });

    HBox hbox = new HBox(openButton);
    hbox.setAlignment(Pos.CENTER);
    hbox.setPadding(new Insets(10));
    hbox.setSpacing(10);

    BorderPane root = new BorderPane();
    root.setCenter(hbox);

    Scene scene = new Scene(root, 400, 300);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Floating Window Example");
    primaryStage.show();
  }

  private void showPopup() {
    if (popup == null) {
      popup = new Popup();

      ObservableList<Person> data = FXCollections.observableArrayList(
          new Person("John", "Doe"),
          new Person("Jane", "Doe"),
          new Person("Bob", "Smith"),
          new Person("Alice", "Johnson")
      );

      TableColumn<Person, String> firstNameColumn = new TableColumn<>("First Name");
      firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
      firstNameColumn.setPrefWidth(150);

      TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
      lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
      lastNameColumn.setPrefWidth(150);

      table = new TableView<>(data);
      table.getColumns().addAll(firstNameColumn, lastNameColumn);

      StackPane stackPane = new StackPane(table);
      stackPane.setPadding(new Insets(10));
      stackPane.setStyle("-fx-background-color: white; -fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 5px;");

      popup.getContent().add(stackPane);
      popup.setAutoHide(true);
    }

    popup.show(table.getScene().getWindow());
  }

  public static void main(String[] args) {
    launch(args);
  }

  public static class Person {

    private final String firstName;
    private final String lastName;

    public Person(String firstName, String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
    }

    public String getFirstName() {
      return firstName;
    }

    public String getLastName() {
      return lastName;
    }
  }
}