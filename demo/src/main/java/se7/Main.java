package se7;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Main extends Application {

  // 创建JavaFX控件和ID的二元组
  public static final ObservableList<Element> ELEMENTS = FXCollections.observableArrayList(
      new Element("Label", "#label1"),
      new Element("Button", "#button1"),
      new Element("TextField", "#textField1")
  );

  @Override
  public void start(Stage primaryStage) throws Exception{
    // 加载FXML文件并创建场景
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("JavaFX GUI");
    primaryStage.setScene(new Scene(root, 400, 275));

    // 获取FXML文件中的TableView和Label
    TableView<Element> tableView = (TableView<Element>) root.lookup("#tableView1");
    Label label = (Label) root.lookup("#label2");

    // 创建表格列
    TableColumn<Element, String> elementColumn = new TableColumn<>("Element");
    TableColumn<Element, String> idColumn = new TableColumn<>("ID");

    // 将表格列与JavaFX控件和ID的二元组绑定
    elementColumn.setCellValueFactory(new PropertyValueFactory<>("element"));
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

    // 将表格列添加到表格中
    tableView.getColumns().add(elementColumn);
    tableView.getColumns().add(idColumn);

    // 向表格中添加JavaFX控件和ID的二元组
    for (Element element : ELEMENTS) {
      tableView.getItems().add(element);
    }

    // 设置表格的选择监听器
    tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        // 更新标签的文本
        label.setText(String.format("Selected element: %s, ID: %s", newSelection.getElement(), newSelection.getId()));
      }
    });

    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }

  // 定义一个包含JavaFX控件和ID的元素类
  public static class Element {
    private StringProperty element;
    private StringProperty id;

    public Element(String element, String id) {
      this.element = new SimpleStringProperty(element);
      this.id = new SimpleStringProperty(id);
    }

    public StringProperty getElement() {
      return element;
    }

    public StringProperty getId() {
      return id;
    }

  }
}

