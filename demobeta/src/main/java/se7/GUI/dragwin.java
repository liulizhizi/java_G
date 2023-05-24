package se7.GUI;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import se7.Function.utils.AutoNumber;
import se7.Function.utils.ConfirmDialog;
import se7.Function.utils.ControlInfo;

public class dragwin extends Stage {
  private double xOffset = 0;
  private double yOffset = 0;
  private double width = 180;
  private boolean next = false;
  private Stage father_stage;
  private final ObservableList<Control_List> data =
      FXCollections.observableArrayList(

      );
  private TableView<Control_List> tableView = new TableView<>();

  public dragwin(Stage father_stage) {

    super(StageStyle.TRANSPARENT);


    // 创建无标题窗口
    this.setResizable(false);
    this.father_stage = father_stage;
    this.initOwner(this.father_stage);

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
    // data.size()
    double height = Math.min( 3 * tableView.getFixedCellSize() + 25, 250); // 计算TableView的高度
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

  public void tableNewItem(String Con_Name, String Con_ID){
    data.add(new Control_List(Con_Name, Con_ID));
  }

  public void deleteItem(String Con_ID){
    Optional<Control_List> matchingControl = data.stream()
        .filter(cl -> cl.getCon_ID().equals(Con_ID)).findFirst();
    Control_List control = matchingControl.orElse(null);
    data.removeAll(control);
  }

  public List<ControlInfo> traversePane(Pane pane) {
    List<ControlInfo> controlInfoList = new ArrayList<>();
    for (Node node : pane.getChildren()) {
      if(node.getId() != null) {
        int lastIndexOfDash = node.getId().lastIndexOf("-");
        String CTRType = node.getId().substring(0, lastIndexOfDash);
        System.out.println(CTRType);
        switch (CTRType){
          case "notebook" -> {
            Optional<Control_List> matchingControl = data.stream()
                .filter(cl -> cl.getCon_ID().equals(node.getId())).findFirst();
            Control_List control = matchingControl.orElse(null);
            ControlInfo controlInfo = new ControlInfo(
                node.getId(),
                control.getCon_Name(),
                node.getLayoutX(),
                node.getLayoutY(),
                node.getLayoutBounds().getWidth(),
                node.getLayoutBounds().getHeight(),
                ((NoteBook)node).getText(),
                "",
                ""
            );
            controlInfoList.add(controlInfo);
          }
          case "linkbutton" -> {
            Optional<Control_List> matchingControl = data.stream()
                .filter(cl -> cl.getCon_ID().equals(node.getId())).findFirst();
            Control_List control = matchingControl.orElse(null);
            ControlInfo controlInfo = new ControlInfo(
                node.getId(),
                control.getCon_Name(),
                node.getLayoutX(),
                node.getLayoutY(),
                node.getLayoutBounds().getWidth(),
                node.getLayoutBounds().getHeight(),
                "",
                "",
                ((LinkButton)node).getLink()
            );
            controlInfoList.add(controlInfo);
          }
          case "linkimage" -> {
            Optional<Control_List> matchingControl = data.stream()
                .filter(cl -> cl.getCon_ID().equals(node.getId())).findFirst();
            Control_List control = matchingControl.orElse(null);
            ControlInfo controlInfo = new ControlInfo(
                node.getId(),
                control.getCon_Name(),
                node.getLayoutX(),
                node.getLayoutY(),
                node.getLayoutBounds().getWidth(),
                node.getLayoutBounds().getHeight(),
                "",
                ((LinkImage)node).getImageString(),
                ((LinkImage)node).getLink()
            );
            controlInfoList.add(controlInfo);
          }
        }

      }
    }
    return controlInfoList;
  }

  public void saveControlInfoToCSV(List<ControlInfo> controlInfoList,String path){
    String Name;
    AutoNumber i = new AutoNumber();
    Name = "control.csv";
    while (new File(path+"\\"+Name).exists()){

      int autoNumber = i.getNextNumber();
      String str = String.valueOf(autoNumber);
      Name = "control_"+ str +".csv";
    }
    saveControlInfoToCSV( controlInfoList, path, Name);
  }
  public void saveControlInfoToCSV(List<ControlInfo> controlInfoList,String path, String Name) {

    if (new File(path+"\\"+Name).exists()){
      ConfirmDialog dialog = new ConfirmDialog("Pay attention! The file you saved has the same name as the file in the folder, and continuing will overwrite the file.");

      dialog.initOwner(this.father_stage);
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.showAndWait();

      if (dialog.isConfirmed()) {
        this.next = true;
      }
    }else{this.next = true;}
    if (this.next){
      System.out.println(path+"\\"+Name);
      Charset charset = Charset.forName("UTF-8");
      try (Writer writer = Files.newBufferedWriter(Paths.get(path+"\\"+Name),charset);
          CSVWriter csvWriter = new CSVWriter(writer)) {
        //String[] header = {"ID","Name", "X", "Y", "Width", "Height"};
        //      csvWriter.writeNext(header);
        for (ControlInfo controlInfo : controlInfoList) {
          int lastIndexOfDash = controlInfo.getID().lastIndexOf("-");
          String callbackCTR = controlInfo.getID().substring(0, lastIndexOfDash);
          System.out.println(callbackCTR);
          switch (callbackCTR){
            case "notebook" ->{String[] row = {
                controlInfo.getID(),
                controlInfo.getName(),
                Double.toString(controlInfo.getX()),
                Double.toString(controlInfo.getY()),
                Double.toString(controlInfo.getWidth()),
                Double.toString(controlInfo.getHeight()),
                controlInfo.getContent(),
                "",
                ""
            };
              csvWriter.writeNext(row);}
            case "linkbutton"->{String[] row = {
                controlInfo.getID(),
                controlInfo.getName(),
                Double.toString(controlInfo.getX()),
                Double.toString(controlInfo.getY()),
                Double.toString(controlInfo.getWidth()),
                Double.toString(controlInfo.getHeight()),
                "",
                controlInfo.getLink(),
                ""

            };
              csvWriter.writeNext(row);}
            case "linkimage"->{String[] row = {
                controlInfo.getID(),
                controlInfo.getName(),
                Double.toString(controlInfo.getX()),
                Double.toString(controlInfo.getY()),
                Double.toString(controlInfo.getWidth()),
                Double.toString(controlInfo.getHeight()),
                "",
                controlInfo.getLink(),
                controlInfo.getImageString()
            };
              csvWriter.writeNext(row);}

          }


        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.out.println("ss"+next);
    this.next = false;
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

