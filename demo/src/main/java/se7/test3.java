package se7;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class test3 extends Application {

  private final ObservableList<Person> data =
      FXCollections.observableArrayList(
          new Person("John", "Doe"),
          new Person("Jane", "Doe"),
          new Person("Bob", "Smith")
      );

  @Override
  public void start(Stage primaryStage) {
    TableView<Person> tableView = new TableView<>();
    TableColumn<Person, String> firstNameColumn = new TableColumn<>("First Name");
    firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
    firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    firstNameColumn.setOnEditCommit(event -> {
      Person person = event.getRowValue();
      person.setFirstName(event.getNewValue());
    });

    TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
    lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    lastNameColumn.setOnEditCommit(event -> {
      Person person = event.getRowValue();
      person.setLastName(event.getNewValue());
    });


    tableView.setItems(data);
    tableView.getColumns().addAll(firstNameColumn, lastNameColumn);
    tableView.setEditable(true);
    tableView.getSelectionModel().setCellSelectionEnabled(true);

    StackPane root = new StackPane();
    root.getChildren().add(tableView);
    primaryStage.setScene(new Scene(root, 300, 250));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  public static class Person {
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;

    public Person(String firstName, String lastName) {
      this.firstName = new SimpleStringProperty(firstName);
      this.lastName = new SimpleStringProperty(lastName);
    }

    public String getFirstName() {
      return firstName.get();
    }

    public void setFirstName(String firstName) {
      this.firstName.set(firstName);
    }

    public SimpleStringProperty firstNameProperty() {
      return firstName;
    }

    public String getLastName() {
      return lastName.get();
    }

    public void setLastName(String lastName) {
      this.lastName.set(lastName);
    }

    public SimpleStringProperty lastNameProperty() {
      return lastName;
    }
  }
}
