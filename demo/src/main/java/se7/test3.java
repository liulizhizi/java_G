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

  private final ObservableList<Control_List> data =
      FXCollections.observableArrayList(
          new Control_List("John", "Doe"),
          new Control_List("Jane", "Doe"),
          new Control_List("Bob", "Smith")
      );

  @Override
  public void start(Stage primaryStage) {
    TableView<Control_List> tableView = new TableView<>();
    TableColumn<Control_List, String> firstNameColumn = new TableColumn<>("Ctr_Name");
    firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().con_NameProperty());
    firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    firstNameColumn.setOnEditCommit(event -> {
      Control_List controlList = event.getRowValue();
      controlList.setCon_Name(event.getNewValue());
    });

    TableColumn<Control_List, String> lastNameColumn = new TableColumn<>("Ctr_ID");
    lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().con_IDProperty());
    lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    lastNameColumn.setOnEditCommit(event -> {
      Control_List controlList = event.getRowValue();
      controlList.setCon_ID(event.getNewValue());
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

  public static class Control_List {
    private final SimpleStringProperty Con_Name;
    private final SimpleStringProperty Con_ID;

    public Control_List(String Con_Name, String Con_ID) {
      this.Con_Name = new SimpleStringProperty(Con_Name);
      this.Con_ID = new SimpleStringProperty(Con_ID);
    }

    public String getCon_Name() {
      return Con_Name.get();
    }

    public void setCon_Name(String con_Name) {
      this.Con_Name.set(con_Name);
    }

    public SimpleStringProperty con_NameProperty() {
      return Con_Name;
    }

    public String getCon_ID() {
      return Con_ID.get();
    }

    public void setCon_ID(String con_ID) {
      this.Con_ID.set(con_ID);
    }

    public SimpleStringProperty con_IDProperty() {
      return Con_ID;
    }
  }
}
