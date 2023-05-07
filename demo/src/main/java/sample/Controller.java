package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import se7.Main;

public class Controller extends Main {

  @FXML
  private TableView<Main.Element> tableView1;

  @FXML
  private TableColumn<Main.Element, String> elementColumn;

  @FXML
  private TableColumn<Main.Element, String> idColumn;

  @FXML
  private Label label2;

  public void initialize() {
    // 将表格列与JavaFX控件和ID的二元组绑定
    elementColumn.setCellValueFactory(cellData -> cellData.getValue().getElement());
    idColumn.setCellValueFactory(cellData -> cellData.getValue().getId());

    // 向表格中添加JavaFX控件和ID的二元组
    for (Element element : Main.ELEMENTS) {
      tableView1.getItems().add(element);
    }

    // 设置表格的选择监听器
    tableView1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        // 更新标签的文本
        label2.setText(String.format("Selected element: %s, ID: %s", newSelection.getElement(), newSelection.getId()));
      }
    });
  }
}
