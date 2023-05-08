package se7.GUI;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import se7.test3.Control_List;

public class dragwin extends Stage {
  private double xOffset = 0;
  private double yOffset = 0;
  private double width = 180;
  private final ObservableList<Control_List> data =
      FXCollections.observableArrayList(
          new Control_List("John", "Doe"),
          new Control_List("Jane", "Doe"),
          new Control_List("Bob", "Smith")
      );

  public dragwin(Stage father_stage) {

    super(StageStyle.TRANSPARENT);

    this.initOwner(father_stage);
    // 创建无标题窗口
    this.setResizable(false);

    // 创建一个空的 Pane
    StackPane root = new StackPane();
    root.getStyleClass().add("root");
    VBox root1 = new VBox();
    root1.getStyleClass().add("root1");

    // 创建一个按钮，用于关闭窗口
    //Button closeButton = new Button("关闭");
    // 空间用于拖拽 Pane
    Pane pane = new Pane();
    pane.setPrefSize(width,20);

    //closeButton.setOnAction(event -> this.close());
    //    closeButton.setLayoutX(10);
    //    closeButton.setLayoutY(10);

    // 添加窗口拖拽事件监听器
    root.setOnMousePressed((MouseEvent event) -> {
      xOffset = event.getSceneX();
      yOffset = event.getSceneY();
    });
    root.setOnMouseDragged((MouseEvent event) -> {
      this.setX(event.getScreenX() - xOffset);
      this.setY(event.getScreenY() - yOffset);
    });

    // 设置Table 放入Pane1
    TableView<Control_List> tableView = new TableView<>();
    tableView.setMinWidth(width-20);
    tableView.setPrefWidth(160);
    // 设置calFactory工作内容
    Callback<TableColumn<Control_List, String>, TableCell<Control_List, String>> cellFactory = col -> {
      TableCell<Control_List, String> cell = new TableCell<>() {
        @Override
        protected void updateItem(String item, boolean empty) {
          super.updateItem(item, empty);
          setText(empty ? null : getString());
        }

        private String getString() {
          return getItem() == null ? "" : getItem().toString();
        }
      };

      cell.setOnMouseClicked(e -> {
        if (!cell.isEmpty()) {
          String value1 = cell.getItem();

          ((Callback<String, Void>) father_stage.getUserData()).call(value1);

        }
      });

      return cell;
    };

    TableColumn<Control_List, String> NameColumn = new TableColumn<>("Ctr_Name");
    NameColumn.setMinWidth(50); // 设置最小宽度
    NameColumn.setPrefWidth(width/2-10); // 设置首选宽度
    NameColumn.setCellValueFactory(cellData -> cellData.getValue().con_NameProperty());

    TableColumn<Control_List, String> IDColumn = new TableColumn<>("Ctr_ID");
    IDColumn.setMinWidth(50); // 设置最小宽度
    IDColumn.setPrefWidth(width/2-10); // 设置首选宽度
    IDColumn.setCellValueFactory(cellData -> cellData.getValue().con_IDProperty());
    IDColumn.setCellFactory(cellFactory);


    tableView.setItems(data);
    tableView.getColumns().addAll(NameColumn, IDColumn);
    tableView.getSelectionModel().setCellSelectionEnabled(true);
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    tableView.setFixedCellSize(20);
    double height = Math.min(data.size() * tableView.getFixedCellSize() + 25, 250); // 计算TableView的高度
    System.out.println(data.size() + "then" + tableView.getFixedCellSize());
    tableView.setPrefHeight(height); // 设置TableView的高度
    tableView.setEditable(false);

    Pane pane1 = new Pane();
    pane1.setPrefSize(width, height);
    pane1.getChildren().add(tableView);

    // 设置编辑功能 放入Pane2
    ToggleButton toggleButton = new ToggleButton("更改已关闭");

    Pane pane2 = new Pane();
    pane2.setPrefSize(width,20);
    pane2.getChildren().add(toggleButton);
    toggleButton.setOnAction(event -> {
      if (toggleButton.isSelected()) {
        toggleButton.setText("更改已开启");
        tableView.setEditable(true);
        NameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        NameColumn.setOnEditCommit(event1 -> {
          Control_List controlList = event1.getRowValue();
          controlList.setCon_Name(event1.getNewValue());
        });
        IDColumn.setEditable(false);
      } else {
        toggleButton.setText("更改已关闭");
        tableView.setEditable(false);
        NameColumn.setCellFactory(cellFactory);
      }
    });


    root1.getChildren().addAll(pane, pane1, pane2);
    root1.setPrefSize(width,height+100);

    root.getChildren().addAll(root1);

    Scene scene = new Scene(root, width, height+70, Color.TRANSPARENT);
    scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // 导入 CSS 文件
    this.setScene(scene);
  }

  private TableView<Control_List> table(){
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
    return tableView;
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

