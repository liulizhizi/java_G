package se7;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;


import se7.Function.utils.ControlInfo;
import se7.GUI.LinkButton;
import se7.GUI.LinkImage;
import se7.GUI.NoteBook;
import se7.GUI.dragwin;
import se7.Function.utils.AutoNumber;
import se7.Function.utils.CSVPersonalReader;

public class Se7NoteBookWin extends Application {

  private double lastX, lastY;
  private double lastWidth, lastHeight;
  private double diffX, diffY;
  private final AutoNumber NoteBook_i = new AutoNumber();
  private final AutoNumber LinkButton_i = new AutoNumber();
  private final AutoNumber LinkImage_i = new AutoNumber();
  private String callbackID = null;
  private String callbackCTR;
  private String SaveName;
  private List<ControlInfo> pa_save = FXCollections.observableArrayList();

  private final BorderPane root = new BorderPane(); //该程序的根节点
  private final HBox TopRoot = new HBox(); //
  private final Pane LCreateRoot = new Pane();
  private final ScrollPane scrollPane = new ScrollPane(LCreateRoot);
  private final VBox RControlRoot = new VBox();
  private dragwin pa;

  private final Button newWindowButton = new Button("查看控件");
  private final ToggleButton editable = new ToggleButton("编辑已关闭");
  private final ToggleButton buttonChange = new ToggleButton("param");
  private final Button buttonCNB = new Button("NoteBook");
  private final Button buttonCLI = new Button("LinkImage");
  private final Button buttonCLB = new Button("LinkButton");
  private final Button buttonSave = new Button("Save");
  private final Button buttonLoad = new Button("Load");
  private final Label SaveLabel = new Label("as");
  private final TextField SaveField = new TextField("control");

  private final Label spinnerLabel1 = new Label("setLayoutX:");
  private final Spinner<Double> spinner1 = new Spinner<>(0.0, 10000,200, 0.1 );
  private final Label spinnerLabel2 = new Label("setLayoutY:");
  private final Spinner<Double> spinner2 = new Spinner<>(0.0, 10000,200, 0.1 );
  private final Label spinnerLabel3 = new Label("setWidth:");
  private final Spinner<Double> spinner3 = new Spinner<>(0.0, 10000,200, 0.1 );
  private final Label spinnerLabel4 = new Label("setHeight:");
  private final Spinner<Double> spinner4 = new Spinner<>(0.0, 10000,200, 0.1 );
  private final Label imageViewLabel = new Label("setImage:");
  private final Button buttonSetImage = new Button("select image");
  private final TextField imageViewTF = new TextField("");

  private final Label imageLinkLabel = new Label("setLink:"); //与LinkButton共用的控制器
  private final Button buttonSetImageLink = new Button("select file");
  private final TextField imageLinkTF = new TextField("");
  private final Button buttondelete = new Button("delete");


  private final Scene sceneN = new Scene(root, 600, 400);
  private final Stage subStage = new Stage();

  @Override
  public void start(Stage primaryStage) throws IOException  {


    //Part1 创建一个VBox，用于放置createRoot和上方控件

    root.setTop(TopRoot);

    root.setRight(RControlRoot);

    root.setCenter(scrollPane);

    // 设置BorderPane的对齐方式

    BorderPane.setAlignment(TopRoot, Pos.CENTER);
    BorderPane.setAlignment(RControlRoot, Pos.CENTER);

    // 上方控件区域与下方区域

    TopRoot.setSpacing(10);
    TopRoot.setAlignment(Pos.CENTER);
    TopRoot.getStyleClass().add("background-container");

    TopRoot.setPrefSize(760,56);
    RControlRoot.setPrefWidth(200);

    scrollPane.setFitToWidth(true); // 自适应宽度

    // 设置滚动模式为垂直滚动
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);




    //Part2 ChildStage

    pa = new dragwin(subStage);
    primaryStage.setUserData((Callback<String, Void>) newValue -> {
      // 在回调函数中更改参数值

      this.callbackID = newValue;
      System.out.println("被指定的控件为" + this.callbackID);
      return null;
    });

    // 创建一个Button，当用户点击该Button时，会显示窗口

    newWindowButton.setLayoutX(10);
    newWindowButton.setLayoutY(300);

    newWindowButton.setOnAction(event -> {
      if (pa.isShowing()) {
        pa.close();
        newWindowButton.setText("查看控件");
        System.out.println("Stage is open");
      } else {

        pa.show();
        newWindowButton.setText("关闭查看");
        System.out.println("Stage is not open");
      }
    });

    // 将Button添加到上方控件中
    TopRoot.getChildren().addAll(newWindowButton, buttonCNB, buttonCLB, buttonCLI, buttonChange, buttonLoad, editable, buttonSave, SaveLabel, SaveField);

    //下方的Create内容 皆受到这个控件影响


    //Part3.1 Create NoteBook


    buttonCNB.setOnAction(e -> {
      int autoNumber = NoteBook_i.getNextNumber();
      String str = String.valueOf(autoNumber);
      NoteBook notebook = new NoteBook("notebook-" + str, str);
      notebook.setEditable(false);
      pa.tableNewItem("notebook-" + str, "notebook-" + str);

      Circle circle = new Circle(5, Color.WHITE);
      circle.setLayoutX(-10);
      circle.setLayoutY(-10);
      circle.setStroke(Color.BLACK);
      circle.setStrokeWidth(2);
      circle.setOpacity(0);
      LCreateRoot.getChildren().addAll(circle, notebook);

      EventHandler<MouseEvent> circleEventP = event -> {
        lastX = event.getSceneX();
        lastY = event.getSceneY();
        lastWidth = notebook.getWidth();
        lastHeight = notebook.getHeight();
        diffX = lastX - notebook.getLayoutX();
        diffY = lastY - notebook.getLayoutY();
        event.consume();
      };


      EventHandler<MouseEvent> circleEventD = event -> {
        double newWidth = lastWidth + event.getSceneX() - lastX;
        double newHeight = lastHeight + event.getSceneY() - lastY;
        notebook.setPrefSize(newWidth, newHeight);
        circle.setLayoutX(notebook.getLayoutX() + notebook.getWidth());
        circle.setLayoutY(notebook.getLayoutY() + notebook.getHeight());
        event.consume();
      };


      EventHandler<MouseEvent> notebookEventP = event -> {
        this.callbackID = notebook.getId();
        lastX = event.getSceneX();
        lastY = event.getSceneY();
        diffX = lastX - notebook.getLayoutX();
        diffY = lastY - notebook.getLayoutY();
        event.consume();
      };

      // Handle the mouse dragged event on the root pane
      EventHandler<MouseEvent> notebookEventD = event -> {
        double newX = event.getSceneX() - diffX;
        double newY = event.getSceneY() - diffY;
        notebook.setLayoutX(newX);
        notebook.setLayoutY(newY);
        circle.setLayoutX(newX + notebook.getWidth());
        circle.setLayoutY(newY + notebook.getHeight());
        event.consume();
      };
      circle.addEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
      circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
      notebook.addEventFilter(MouseEvent.MOUSE_PRESSED, notebookEventP);
      notebook.addEventFilter(MouseEvent.MOUSE_DRAGGED, notebookEventD);

      editable.addEventHandler(ActionEvent.ACTION, event -> {
        if (editable.isSelected()) {
          circle.addEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
          circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
          notebook.addEventFilter(MouseEvent.MOUSE_PRESSED, notebookEventP);
          notebook.addEventFilter(MouseEvent.MOUSE_DRAGGED, notebookEventD);

        } else {
          circle.removeEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
          circle.removeEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
          notebook.removeEventFilter(MouseEvent.MOUSE_PRESSED, notebookEventP);
          notebook.removeEventFilter(MouseEvent.MOUSE_DRAGGED, notebookEventD);

        }
      });
      buttonChange.addEventHandler(ActionEvent.ACTION, event -> {
        if (buttonChange.isSelected()) {
          circle.removeEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
          circle.removeEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
          notebook.removeEventFilter(MouseEvent.MOUSE_PRESSED, notebookEventP);
          notebook.removeEventFilter(MouseEvent.MOUSE_DRAGGED, notebookEventD);

        } else {
          circle.addEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
          circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
          notebook.addEventFilter(MouseEvent.MOUSE_PRESSED, notebookEventP);
          notebook.addEventFilter(MouseEvent.MOUSE_DRAGGED, notebookEventD);

        }
      });

    });
    // Part3.2 Create LinkButton

    buttonCLB.setOnAction(e ->{
      int autoNumber = LinkButton_i.getNextNumber();
      String str = String.valueOf(autoNumber);
      LinkButton linkbutton = new LinkButton("linkbutton-" + str, str);
      pa.tableNewItem("linkbutton-" + str, "linkbutton-" + str);

      Circle circle = new Circle(5, Color.WHITE);
      circle.setLayoutX(-10);
      circle.setLayoutY(-10);
      circle.setStroke(Color.BLACK);
      circle.setStrokeWidth(2);
      circle.setOpacity(0);
      LCreateRoot.getChildren().addAll(circle, linkbutton);

      EventHandler<MouseEvent> circleEventP = event -> {
        lastX = event.getSceneX();
        lastY = event.getSceneY();
        lastWidth = linkbutton.getWidth();
        lastHeight = linkbutton.getHeight();
        diffX = lastX - linkbutton.getLayoutX();
        diffY = lastY - linkbutton.getLayoutY();
        event.consume();
      };


      EventHandler<MouseEvent> circleEventD = event -> {
        double newWidth = lastWidth + event.getSceneX() - lastX;
        double newHeight = lastHeight + event.getSceneY() - lastY;
        linkbutton.setPrefSize(newWidth, newHeight);
        circle.setLayoutX(linkbutton.getLayoutX() + linkbutton.getWidth());
        circle.setLayoutY(linkbutton.getLayoutY() + linkbutton.getHeight());
        event.consume();
      };


      EventHandler<MouseEvent> linkbuttonEventP = event -> {
        this.callbackID = linkbutton.getId();
        lastX = event.getSceneX();
        lastY = event.getSceneY();
        diffX = lastX - linkbutton.getLayoutX();
        diffY = lastY - linkbutton.getLayoutY();
        event.consume();
      };

      // Handle the mouse dragged event on the root pane
      EventHandler<MouseEvent> linkbuttonEventD = event -> {
        double newX = event.getSceneX() - diffX;
        double newY = event.getSceneY() - diffY;
        linkbutton.setLayoutX(newX);
        linkbutton.setLayoutY(newY);
        circle.setLayoutX(newX + linkbutton.getWidth());
        circle.setLayoutY(newY + linkbutton.getHeight());
        event.consume();
      };
      circle.addEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
      circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
      linkbutton.addEventFilter(MouseEvent.MOUSE_PRESSED, linkbuttonEventP);
      linkbutton.addEventFilter(MouseEvent.MOUSE_DRAGGED, linkbuttonEventD);

      editable.addEventHandler(ActionEvent.ACTION, event -> {
        if (editable.isSelected()) {
          circle.addEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
          circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
          linkbutton.addEventFilter(MouseEvent.MOUSE_PRESSED, linkbuttonEventP);
          linkbutton.addEventFilter(MouseEvent.MOUSE_DRAGGED, linkbuttonEventD);

        } else {
          circle.removeEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
          circle.removeEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
          linkbutton.removeEventFilter(MouseEvent.MOUSE_PRESSED, linkbuttonEventP);
          linkbutton.removeEventFilter(MouseEvent.MOUSE_DRAGGED, linkbuttonEventD);

        }
      });
      buttonChange.addEventHandler(ActionEvent.ACTION, event -> {
        if (buttonChange.isSelected()) {
          circle.removeEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
          circle.removeEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
          linkbutton.removeEventFilter(MouseEvent.MOUSE_PRESSED, linkbuttonEventP);
          linkbutton.removeEventFilter(MouseEvent.MOUSE_DRAGGED, linkbuttonEventD);

        } else {
          circle.addEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
          circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
          linkbutton.addEventFilter(MouseEvent.MOUSE_PRESSED, linkbuttonEventP);
          linkbutton.addEventFilter(MouseEvent.MOUSE_DRAGGED, linkbuttonEventD);

        }
      });
    });
    // Part3.3 Create LinkImage

    buttonCLI.setOnAction(e ->{
      int autoNumber = LinkImage_i.getNextNumber();
      String str = String.valueOf(autoNumber);
      String image_path = Paths.get("picture.png").toAbsolutePath().toString();
      LinkImage linkimage = new LinkImage("linkimage-" + str, new Image(image_path));
      linkimage.setPreserveRatio(true);
      linkimage.setSmooth(false);
      linkimage.setImageString(image_path);
      pa.tableNewItem("linkimage-" + str, "linkimage-" + str);

      Circle circle = new Circle(5, Color.WHITE);
      circle.setLayoutX(-10);
      circle.setLayoutY(-10);
      circle.setStroke(Color.BLACK);
      circle.setStrokeWidth(2);
      circle.setOpacity(0);
      LCreateRoot.getChildren().addAll(circle, linkimage);

      EventHandler<MouseEvent> circleEventP = event -> {
        lastX = event.getSceneX();
        lastY = event.getSceneY();
        lastWidth = linkimage.getFitWidth();
        lastHeight = linkimage.getFitHeight();
        diffX = lastX - linkimage.getLayoutX();
        diffY = lastY - linkimage.getLayoutY();
        event.consume();
      };


      EventHandler<MouseEvent> circleEventD = event -> {
        double newWidth = lastWidth + event.getSceneX() - lastX;
        double newHeight = lastHeight + event.getSceneY() - lastY;
        linkimage.setFitWidth(newWidth);
        linkimage.setFitHeight(newHeight);
        circle.setLayoutX(linkimage.getLayoutX() + linkimage.getFitWidth());
        circle.setLayoutY(linkimage.getLayoutY() + linkimage.getFitHeight());
        event.consume();
      };


      EventHandler<MouseEvent> linkimageEventP = event -> {
        this.callbackID = linkimage.getId();
        lastX = event.getSceneX();
        lastY = event.getSceneY();
        diffX = lastX - linkimage.getLayoutX();
        diffY = lastY - linkimage.getLayoutY();
        event.consume();
      };

      // Handle the mouse dragged event on the root pane
      EventHandler<MouseEvent> linkimageEventD = event -> {
        double newX = event.getSceneX() - diffX;
        double newY = event.getSceneY() - diffY;
        linkimage.setLayoutX(newX);
        linkimage.setLayoutY(newY);
        circle.setLayoutX(newX + linkimage.getFitWidth());
        circle.setLayoutY(newY + linkimage.getFitHeight());
        event.consume();
      };
      circle.addEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
      circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
      linkimage.addEventFilter(MouseEvent.MOUSE_PRESSED, linkimageEventP);
      linkimage.addEventFilter(MouseEvent.MOUSE_DRAGGED, linkimageEventD);

      editable.addEventHandler(ActionEvent.ACTION, event -> {
        if (editable.isSelected()) {
          circle.addEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
          circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
          linkimage.addEventFilter(MouseEvent.MOUSE_PRESSED, linkimageEventP);
          linkimage.addEventFilter(MouseEvent.MOUSE_DRAGGED, linkimageEventD);

        } else {
          circle.removeEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
          circle.removeEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
          linkimage.removeEventFilter(MouseEvent.MOUSE_PRESSED, linkimageEventP);
          linkimage.removeEventFilter(MouseEvent.MOUSE_DRAGGED, linkimageEventD);

        }
      });
      buttonChange.addEventHandler(ActionEvent.ACTION, event -> {
        if (buttonChange.isSelected()) {
          circle.removeEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
          circle.removeEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
          linkimage.removeEventFilter(MouseEvent.MOUSE_PRESSED, linkimageEventP);
          linkimage.removeEventFilter(MouseEvent.MOUSE_DRAGGED, linkimageEventD);

        } else {
          circle.addEventFilter(MouseEvent.MOUSE_PRESSED,circleEventP);
          circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
          linkimage.addEventFilter(MouseEvent.MOUSE_PRESSED, linkimageEventP);
          linkimage.addEventFilter(MouseEvent.MOUSE_DRAGGED, linkimageEventD);

        }
      });
    });
    // Part4 Load module


    buttonLoad.setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("选择文件");
      FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
      fileChooser.getExtensionFilters().add(extFilter);

      File selectedFile = fileChooser.showOpenDialog(primaryStage);
      if (selectedFile != null) {
        try {
          readCSV(selectedFile.getAbsolutePath());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    });


    // Part5 更改信息模块

    spinnerLabel1.setVisible(false);
    spinnerLabel1.setPrefWidth(120);
    spinner1.setEditable(true);
    spinner1.setPrefWidth(80);
    spinner1.setVisible(false);

    spinnerLabel2.setVisible(false);
    spinnerLabel2.setPrefWidth(120);
    spinner2.setEditable(true);
    spinner2.setPrefWidth(80);
    spinner2.setVisible(false);

    spinnerLabel3.setVisible(false);
    spinnerLabel3.setPrefWidth(120);
    spinner3.setEditable(true);
    spinner3.setPrefWidth(80);
    spinner3.setVisible(false);

    spinnerLabel4.setVisible(false);
    spinnerLabel4.setPrefWidth(120);
    spinner4.setEditable(true);
    spinner4.setPrefWidth(80);
    spinner4.setVisible(false);

    imageViewLabel.setVisible(false);
    imageViewLabel.setPrefWidth(120);
    buttonSetImage.setVisible(false);
    imageViewTF.setVisible(false);
    imageLinkLabel.setVisible(false);
    imageLinkLabel.setPrefWidth(120);
    buttonSetImageLink.setVisible(false);
    imageLinkTF.setVisible(false);

    buttondelete.setVisible(false);

    HBox sp1 = new HBox(spinnerLabel1, spinner1);
    HBox sp2 = new HBox(spinnerLabel2, spinner2);
    HBox sp3 = new HBox(spinnerLabel3, spinner3);
    HBox sp4 = new HBox(spinnerLabel4, spinner4);
    HBox lbImage = new HBox(imageViewLabel, buttonSetImage);
    lbImage.setSpacing(10);
    VBox lbtImage = new VBox(lbImage, imageViewTF);
    lbtImage.setSpacing(10);
    HBox lbLink = new HBox(imageLinkLabel, buttonSetImageLink);
    lbLink.setSpacing(10);
    VBox lbtLink = new VBox(lbLink, imageLinkTF);
    lbtLink.setSpacing(10);

    RControlRoot.getChildren().addAll(sp1, sp2, sp3, sp4, lbtImage, lbtLink, buttondelete);
    RControlRoot.setSpacing(10);
    RControlRoot.getStyleClass().add("background-container");



    EventHandler<ActionEvent> buttonChangeAction = event -> {

      if(this.callbackID != null & buttonChange.isSelected()){
        int lastIndexOfDash = this.callbackID.lastIndexOf("-");
        callbackCTR = this.callbackID.substring(0, lastIndexOfDash);
        System.out.println(callbackCTR);
        switch (callbackCTR) {
          case "notebook" -> {
            RControlRoot.setPrefWidth(200);
            buttondelete.setVisible(true);
            Node node = sceneN.lookup("#" + this.callbackID);
            NoteBook noteBook1 = (NoteBook) node;
            System.out.println(noteBook1);
            spinnerLabel1.setVisible(true);
            spinnerLabel2.setVisible(true);
            spinnerLabel3.setVisible(true);
            spinnerLabel4.setVisible(true);
            spinner1.setVisible(true);
            double spinner1x = noteBook1.getLayoutX();
            SpinnerValueFactory<Double> valueFactory1 = new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0, 10000, spinner1x, 0.1);
            spinner1.setValueFactory(valueFactory1);
            spinner2.setVisible(true);
            double spinner2y = noteBook1.getLayoutY();
            SpinnerValueFactory<Double> valueFactory2 = new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0, 10000, spinner2y, 0.1);
            spinner2.setValueFactory(valueFactory2);
            spinner3.setVisible(true);
            double spinner3w = noteBook1.getWidth();
            SpinnerValueFactory<Double> valueFactory3 = new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0, 10000, spinner3w, 0.1);
            spinner3.setValueFactory(valueFactory3);
            spinner4.setVisible(true);
            double spinner4h = noteBook1.getHeight();
            SpinnerValueFactory<Double> valueFactory4 = new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0, 10000, spinner4h, 0.1);
            spinner4.setValueFactory(valueFactory4);
            noteBook1.layoutXProperty().bind(spinner1.valueProperty());
            noteBook1.layoutYProperty().bind(spinner2.valueProperty());
            noteBook1.prefWidthProperty().bind(spinner3.valueProperty());
            noteBook1.prefHeightProperty().bind(spinner4.valueProperty());
          }
          case "linkimage" -> {
            RControlRoot.setPrefWidth(200);
            buttondelete.setVisible(true);
            Node node = sceneN.lookup("#" + this.callbackID);
            LinkImage linkImage1 = (LinkImage) node;
            System.out.println(linkImage1);
            spinnerLabel1.setVisible(true);
            spinnerLabel2.setVisible(true);
            spinnerLabel3.setVisible(true);
            spinnerLabel4.setVisible(true);
            imageViewLabel.setVisible(true);
            buttonSetImage.setVisible(true);
            imageViewTF.setVisible(true);
            imageLinkLabel.setVisible(true);
            buttonSetImageLink.setVisible(true);
            imageLinkTF.setVisible(true);
            spinner1.setVisible(true);
            double spinner1x = linkImage1.getLayoutX();
            SpinnerValueFactory<Double> valueFactory1 = new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0, 10000, spinner1x, 0.1);
            spinner1.setValueFactory(valueFactory1);
            spinner2.setVisible(true);
            double spinner2y = linkImage1.getLayoutY();
            SpinnerValueFactory<Double> valueFactory2 = new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0, 10000, spinner2y, 0.1);
            spinner2.setValueFactory(valueFactory2);
            spinner3.setVisible(true);
            double spinner3w = linkImage1.getFitWidth();
            SpinnerValueFactory<Double> valueFactory3 = new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0, 10000, spinner3w, 0.1);
            spinner3.setValueFactory(valueFactory3);
            spinner4.setVisible(true);
            double spinner4h = linkImage1.getFitHeight();
            SpinnerValueFactory<Double> valueFactory4 = new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0, 10000, spinner4h, 0.1);
            spinner4.setValueFactory(valueFactory4);
            linkImage1.layoutXProperty().bind(spinner1.valueProperty());
            linkImage1.layoutYProperty().bind(spinner2.valueProperty());
            linkImage1.fitWidthProperty().bind(spinner3.valueProperty());
            linkImage1.fitHeightProperty().bind(spinner4.valueProperty());
            buttonSetImage.setOnAction(event1 -> {
              FileChooser fileChooser = new FileChooser();

              // 设置FileChooser的标题和可接受的文件类型
              fileChooser.setTitle("选择一张图片");
              fileChooser.getExtensionFilters().addAll(
                  new FileChooser.ExtensionFilter("图像文件", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp", "*.wbmp", "*.webp")
              );

              // 显示FileChooser对话框，等待用户选择文件
              File file = fileChooser.showOpenDialog(primaryStage);

              // 如果用户选择了文件，则输出文件路径
              if (file != null) {
                imageViewTF.setText(file.getAbsolutePath());

                linkImage1.setImage(new Image(file.getAbsolutePath()));
                linkImage1.setImageString(file.getAbsolutePath());

                System.out.println("选择的文件路径：" + file.getAbsolutePath());
              }
            });
            buttonSetImageLink.setOnAction(event2->{
              FileChooser fileChooser = new FileChooser();

              // 设置FileChooser的标题和可接受的文件类型
              fileChooser.setTitle("选择一个文件");
              fileChooser.getExtensionFilters().addAll(
                  new FileChooser.ExtensionFilter("所有文件", "*.*")
              );

              // 显示FileChooser对话框，等待用户选择文件
              File file = fileChooser.showOpenDialog(primaryStage);

              // 如果用户选择了文件，则打开文件作为连接
              if (file != null) {
                imageLinkTF.setText(file.getAbsolutePath());

                linkImage1.setOnMouseClicked(
                    event1 -> getHostServices().showDocument(file.getAbsolutePath()));

              }

            });
          }
          case "linkbutton" -> {
            RControlRoot.setPrefWidth(200);
            buttondelete.setVisible(true);
            Node node = sceneN.lookup("#" + this.callbackID);
            LinkButton linkButton1 = (LinkButton) node;
            System.out.println(linkButton1);
            spinnerLabel1.setVisible(true);
            spinnerLabel2.setVisible(true);
            spinnerLabel3.setVisible(true);
            spinnerLabel4.setVisible(true);
            imageLinkLabel.setVisible(true);
            buttonSetImageLink.setVisible(true);
            imageLinkTF.setVisible(true);
            spinner1.setVisible(true);
            double spinner1x = linkButton1.getLayoutX();
            SpinnerValueFactory<Double> valueFactory1 = new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0, 10000, spinner1x, 0.1);
            spinner1.setValueFactory(valueFactory1);
            spinner2.setVisible(true);
            double spinner2y = linkButton1.getLayoutY();
            SpinnerValueFactory<Double> valueFactory2 = new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0, 10000, spinner2y, 0.1);
            spinner2.setValueFactory(valueFactory2);
            spinner3.setVisible(true);
            double spinner3w = linkButton1.getWidth();
            SpinnerValueFactory<Double> valueFactory3 = new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0, 10000, spinner3w, 0.1);
            spinner3.setValueFactory(valueFactory3);
            spinner4.setVisible(true);
            double spinner4h = linkButton1.getHeight();
            SpinnerValueFactory<Double> valueFactory4 = new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0, 10000, spinner4h, 0.1);
            spinner4.setValueFactory(valueFactory4);
            linkButton1.layoutXProperty().bind(spinner1.valueProperty());
            linkButton1.layoutYProperty().bind(spinner2.valueProperty());
            linkButton1.prefWidthProperty().bind(spinner3.valueProperty());
            linkButton1.prefHeightProperty().bind(spinner4.valueProperty());
            buttonSetImageLink.setOnAction(event2->{
              FileChooser fileChooser = new FileChooser();

              // 设置FileChooser的标题和可接受的文件类型
              fileChooser.setTitle("选择一个文件");
              fileChooser.getExtensionFilters().addAll(
                  new FileChooser.ExtensionFilter("所有文件", "*.*")
              );

              // 显示FileChooser对话框，等待用户选择文件
              File file = fileChooser.showOpenDialog(primaryStage);

              // 如果用户选择了文件，则打开文件作为连接
              if (file != null) {
                imageLinkTF.setText(file.getAbsolutePath());

                linkButton1.setOnMouseClicked(
                    event1 -> getHostServices().showDocument(file.getAbsolutePath()));

              }

            });
          }

        }
        buttondelete.setOnAction(event1->{
          Node node = sceneN.lookup("#" + this.callbackID);
          int index = this.callbackID.indexOf("-");
          String result = this.callbackID.substring(index + 1);
          int number_i = Integer.parseInt(result);
          String nodeCTR = this.callbackID.substring(0, index);
          switch (nodeCTR){
            case "notebook" ->{
              NoteBook_i.releaseNumber(number_i);
            }
            case "linkbutton" ->{
              LinkButton_i.releaseNumber(number_i);
            }
            case "linkimage" ->{
              LinkImage_i.releaseNumber(number_i);
            }
          }

          LCreateRoot.getChildren().remove(node);
          pa.deleteItem(this.callbackID);

        });

      } else if (this.callbackID != null) {
        spinner1.setVisible(false);
        spinner2.setVisible(false);
        spinner3.setVisible(false);
        spinner4.setVisible(false);
        spinnerLabel1.setVisible(false);
        spinnerLabel2.setVisible(false);
        spinnerLabel3.setVisible(false);
        spinnerLabel4.setVisible(false);
        imageViewLabel.setVisible(false);
        buttonSetImage.setVisible(false);
        imageViewTF.setVisible(false);
        imageLinkLabel.setVisible(false);
        buttonSetImageLink.setVisible(false);
        imageLinkTF.setVisible(false);
        buttondelete.setVisible(false);
        notebookUnbind(LCreateRoot);
      }
    };
    buttonChange.setOnAction(buttonChangeAction);



    buttonCNB.setDisable(true);
    buttonCLB.setDisable(true);
    buttonCLI.setDisable(true);
    buttonChange.setDisable(true);
    buttonLoad.setDisable(true);

    editable.addEventHandler(ActionEvent.ACTION, event -> {
      if (editable.isSelected()) {
        editable.setText("编辑已开启");

        buttonCNB.setDisable(false);
        buttonCLB.setDisable(false);
        buttonCLI.setDisable(false);
        buttonChange.setDisable(false);
        buttonLoad.setDisable(false);

        notebookEdit(LCreateRoot, false);




      } else {
        editable.setText("编辑已关闭");
        buttonCNB.setDisable(true);
        buttonCLB.setDisable(true);
        buttonCLI.setDisable(true);
        buttonChange.setDisable(true);
        buttonLoad.setDisable(true);
        spinner1.setVisible(false);
        spinner2.setVisible(false);
        spinner3.setVisible(false);
        spinner4.setVisible(false);
        spinnerLabel1.setVisible(false);
        spinnerLabel2.setVisible(false);
        spinnerLabel3.setVisible(false);
        spinnerLabel4.setVisible(false);
        imageViewLabel.setVisible(false);
        buttonSetImage.setVisible(false);
        imageViewTF.setVisible(false);
        imageLinkLabel.setVisible(false);
        buttonSetImageLink.setVisible(false);
        imageLinkTF.setVisible(false);
        buttondelete.setVisible(false);
        notebookUnbind(LCreateRoot);

        notebookEdit(LCreateRoot, true);

      }
    });

    //Part6 SaveController


    buttonSave.setOnAction(e->{
      this.SaveName = SaveField.getText();
      this.pa_save= pa.traversePane(LCreateRoot);

      DirectoryChooser directoryChooser = new DirectoryChooser();
      directoryChooser.setTitle("Choose a Directory");

      // 显示文件夹选择器
      File selectedDirectory = directoryChooser.showDialog(primaryStage);

      // 如果用户选择了文件夹，则打印其路径
      if (selectedDirectory != null) {
        System.out.println(selectedDirectory.getAbsolutePath());
        System.out.println(this.pa_save.size()+"\\"+this.SaveName);
        if (this.SaveName.equals("control")||this.SaveName.equals("")) {
          pa.saveControlInfoToCSV(pa_save, selectedDirectory.getAbsolutePath());
        } else {
          pa.saveControlInfoToCSV(pa_save, selectedDirectory.getAbsolutePath(), this.SaveName+".csv");
        }
      }


    });

    sceneN.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styleNote.css")).toExternalForm());
    subStage.setScene(sceneN);
    subStage.setWidth(960);
    subStage.setHeight(720);
    subStage.setMinWidth(960);
    subStage.setMinHeight(720);
    subStage.initOwner(primaryStage);


    Button button1 = new Button("MainPage");
    button1.setPrefWidth(150);
    Button button2 = new Button("GPA");
    button2.setPrefWidth(150);
    Button button3 = new Button("GPA Review");
    button3.setPrefWidth(150);
    Button button4 = new Button("Achievement");
    button4.setPrefWidth(150);
    Button button5 = new Button("Achievement Review");
    button5.setPrefWidth(150);
    Button button6 = new Button("New Note");
    button6.setPrefWidth(150);

    pages myApp1 = new pages();
    Parent myAppRoot1 = myApp1.getRoot();

    gpaApplication myApp2 = new gpaApplication();
    Parent myAppRoot2 = myApp2.getRoot();

    reviewApplication myApp3 = new reviewApplication();
    Parent myAppRoot3 = myApp3.getRoot();

    achievementApplication myApp4 = new achievementApplication();
    Parent myAppRoot4 = myApp4.getRoot();

    achievementReviewApplication myApp5 = new achievementReviewApplication();
    Parent myAppRoot5 = myApp5.getRoot();


    BorderPane Broot = new BorderPane();

    VBox pageCTR =new VBox(button1, button2, button3, button4, button5, button6);
    pageCTR.setPrefWidth(170);
    pageCTR.setSpacing(30);
    pageCTR.setAlignment(Pos.CENTER);

    // 将四个按钮添加到左侧

    Broot.setLeft(pageCTR);
    BorderPane.setAlignment(pageCTR, Pos.CENTER_LEFT);
    Broot.setCenter(myAppRoot1);
    BorderPane.setAlignment(myAppRoot1, Pos.CENTER_RIGHT);

    // create scene 1

    button1.setOnAction(event -> {
      Broot.setCenter(myAppRoot1);
      BorderPane.setAlignment(myAppRoot1, javafx.geometry.Pos.CENTER);
    });

    // create scene 2

    button2.setOnAction(event -> {
      Broot.setCenter(myAppRoot2);
      BorderPane.setAlignment(myAppRoot2, javafx.geometry.Pos.CENTER);
    });

    // create scene 3

    button3.setOnAction(event -> {
      Broot.setCenter(myAppRoot3);
      BorderPane.setAlignment(myAppRoot3, javafx.geometry.Pos.CENTER);
    });

    // create scene 4

    button4.setOnAction(event -> {
      Broot.setCenter(myAppRoot4);
      BorderPane.setAlignment(myAppRoot4, javafx.geometry.Pos.CENTER);
    });

    // create scene 5

    button5.setOnAction(event -> {
      Broot.setCenter(myAppRoot5);
      BorderPane.setAlignment(myAppRoot5, javafx.geometry.Pos.CENTER);
    });

    // create noteBook
    button6.setOnAction(event -> {
      subStage.show();
    });

    Scene scene = new Scene(Broot);
    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm()); // 导入 CSS 文件
    primaryStage.setScene(scene);
    primaryStage.setWidth(960);
    primaryStage.setHeight(610);
    primaryStage.setResizable(false);
    primaryStage.show();
  }


  public void notebookEdit(Pane pane, boolean Boolean) {
    for (Node node : pane.getChildren()) {
      if (node instanceof Pane) {
        notebookEdit((Pane) node, Boolean);
      } else{
        String nodeID = node.getId();
        if(nodeID != null){
          int lastIndexOfDash = nodeID.lastIndexOf("-");
          String nodeCTR = nodeID.substring(0, lastIndexOfDash);
          System.out.println(nodeCTR);
          if (nodeCTR.equals("notebook")){
            NoteBook noteBook2 = (NoteBook) node;
            System.out.println(noteBook2);
            noteBook2.setEditable(Boolean);
          }
        }
      }
    }
  }

  public void notebookUnbind(Pane pane) {
    for (Node node : pane.getChildren()) {
      if (node instanceof Pane) {
        notebookUnbind((Pane) node);
      } else{
        String nodeID = node.getId();
        if(nodeID != null){
          int lastIndexOfDash = nodeID.lastIndexOf("-");
          String nodeCTR = nodeID.substring(0, lastIndexOfDash);
          System.out.println(nodeCTR);
          switch (nodeCTR) {
            case "notebook" -> {
              NoteBook noteBook3 = (NoteBook) node;
              System.out.println(noteBook3);
              noteBook3.layoutXProperty().unbind();
              noteBook3.layoutYProperty().unbind();
              noteBook3.prefWidthProperty().unbind();
              noteBook3.prefHeightProperty().unbind();
            }
            case "linkimage" -> {
              LinkImage linkImage3 = (LinkImage) node;
              System.out.println(linkImage3);
              linkImage3.layoutXProperty().unbind();
              linkImage3.layoutYProperty().unbind();
              linkImage3.fitWidthProperty().unbind();
              linkImage3.fitHeightProperty().unbind();
            }
            case "linkbutton" -> {
              LinkButton linkButton3 = (LinkButton) node;
              System.out.println(linkButton3);
              linkButton3.layoutXProperty().unbind();
              linkButton3.layoutYProperty().unbind();
              linkButton3.prefWidthProperty().unbind();
              linkButton3.prefHeightProperty().unbind();
            }
          }

        }
      }
    }
  }



  public void readCSV(String filePath) throws IOException {
    try {
      List<String[]> rows = CSVPersonalReader.parseCSV(filePath);

      for (String[] row : rows) {

        String result = String.join("", row);
        String[] array = result.split(",");

        // 创建 ArrayList 并将字符串数组的元素添加到 ArrayList 中
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(array));

        // 输出 ArrayList
        for (String element : arrayList) {
          System.out.println(element);
        }
        int lastIndexOfDash = arrayList.get(0).lastIndexOf("-");
        String nodeCTR = arrayList.get(0).substring(0, lastIndexOfDash);
        System.out.println(nodeCTR);
        switch (nodeCTR) {
          case "notebook" -> {
            int autoNumber = NoteBook_i.getNextNumber();
            String str = String.valueOf(autoNumber);
            NoteBook notebook = new NoteBook("notebook-" + str, str);
            notebook.setLayoutX(Double.parseDouble(arrayList.get(2)));
            notebook.setLayoutY(Double.parseDouble(arrayList.get(3)));
            notebook.setPrefWidth(Double.parseDouble(arrayList.get(4)));
            notebook.setPrefHeight(Double.parseDouble(arrayList.get(5)));
            String withoutLastChar = arrayList.get(6).substring(0, arrayList.get(6).length() - 1); // Remove the first and last characters "\"" of the string.
            String withoutFirstChar = withoutLastChar.substring(1);
            notebook.setText(withoutFirstChar);
            notebook.setEditable(false);
            pa.tableNewItem(arrayList.get(1), "notebook-" + str);

            Circle circle = new Circle(5, Color.WHITE);
            circle.setLayoutX(-10);
            circle.setLayoutY(-10);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);
            circle.setOpacity(0);
            LCreateRoot.getChildren().addAll(circle, notebook);

            EventHandler<MouseEvent> circleEventP = event -> {
              lastX = event.getSceneX();
              lastY = event.getSceneY();
              lastWidth = notebook.getWidth();
              lastHeight = notebook.getHeight();
              diffX = lastX - notebook.getLayoutX();
              diffY = lastY - notebook.getLayoutY();
              event.consume();
            };

            EventHandler<MouseEvent> circleEventD = event -> {
              double newWidth = lastWidth + event.getSceneX() - lastX;
              double newHeight = lastHeight + event.getSceneY() - lastY;
              notebook.setPrefSize(newWidth, newHeight);
              circle.setLayoutX(notebook.getLayoutX() + notebook.getWidth());
              circle.setLayoutY(notebook.getLayoutY() + notebook.getHeight());
              event.consume();
            };

            EventHandler<MouseEvent> notebookEventP = event -> {
              this.callbackID = notebook.getId();
              lastX = event.getSceneX();
              lastY = event.getSceneY();
              diffX = lastX - notebook.getLayoutX();
              diffY = lastY - notebook.getLayoutY();
              event.consume();
            };

            // Handle the mouse dragged event on the root pane
            EventHandler<MouseEvent> notebookEventD = event -> {
              double newX = event.getSceneX() - diffX;
              double newY = event.getSceneY() - diffY;
              notebook.setLayoutX(newX);
              notebook.setLayoutY(newY);
              circle.setLayoutX(newX + notebook.getWidth());
              circle.setLayoutY(newY + notebook.getHeight());
              event.consume();
            };
            circle.addEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
            circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
            notebook.addEventFilter(MouseEvent.MOUSE_PRESSED, notebookEventP);
            notebook.addEventFilter(MouseEvent.MOUSE_DRAGGED, notebookEventD);

            editable.addEventHandler(ActionEvent.ACTION, event -> {
              if (editable.isSelected()) {
                circle.addEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
                circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
                notebook.addEventFilter(MouseEvent.MOUSE_PRESSED, notebookEventP);
                notebook.addEventFilter(MouseEvent.MOUSE_DRAGGED, notebookEventD);

              } else {
                circle.removeEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
                circle.removeEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
                notebook.removeEventFilter(MouseEvent.MOUSE_PRESSED, notebookEventP);
                notebook.removeEventFilter(MouseEvent.MOUSE_DRAGGED, notebookEventD);

              }
            });
            buttonChange.addEventHandler(ActionEvent.ACTION, event -> {
              if (buttonChange.isSelected()) {
                circle.removeEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
                circle.removeEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
                notebook.removeEventFilter(MouseEvent.MOUSE_PRESSED, notebookEventP);
                notebook.removeEventFilter(MouseEvent.MOUSE_DRAGGED, notebookEventD);

              } else {
                circle.addEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
                circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
                notebook.addEventFilter(MouseEvent.MOUSE_PRESSED, notebookEventP);
                notebook.addEventFilter(MouseEvent.MOUSE_DRAGGED, notebookEventD);

              }
            });
          }

          case "linkbutton" -> {
            int autoNumber = LinkButton_i.getNextNumber();
            String str = String.valueOf(autoNumber);
            LinkButton linkbutton = new LinkButton("linkbutton-" + str, str);
            linkbutton.setLayoutX(Double.parseDouble(arrayList.get(2)));
            linkbutton.setLayoutY(Double.parseDouble(arrayList.get(3)));
            linkbutton.setPrefWidth(Double.parseDouble(arrayList.get(4)));
            linkbutton.setPrefHeight(Double.parseDouble(arrayList.get(5)));
            linkbutton.setOnMouseClicked(
                event1 -> getHostServices().showDocument(arrayList.get(7)));

            pa.tableNewItem(arrayList.get(1), "linkbutton-" + str);

            Circle circle = new Circle(5, Color.WHITE);
            circle.setLayoutX(-10);
            circle.setLayoutY(-10);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);
            circle.setOpacity(0);
            LCreateRoot.getChildren().addAll(circle, linkbutton);

            EventHandler<MouseEvent> circleEventP = event -> {
              lastX = event.getSceneX();
              lastY = event.getSceneY();
              lastWidth = linkbutton.getWidth();
              lastHeight = linkbutton.getHeight();
              diffX = lastX - linkbutton.getLayoutX();
              diffY = lastY - linkbutton.getLayoutY();
              event.consume();
            };

            EventHandler<MouseEvent> circleEventD = event -> {
              double newWidth = lastWidth + event.getSceneX() - lastX;
              double newHeight = lastHeight + event.getSceneY() - lastY;
              linkbutton.setPrefSize(newWidth, newHeight);
              circle.setLayoutX(linkbutton.getLayoutX() + linkbutton.getWidth());
              circle.setLayoutY(linkbutton.getLayoutY() + linkbutton.getHeight());
              event.consume();
            };

            EventHandler<MouseEvent> linkbuttonEventP = event -> {
              this.callbackID = linkbutton.getId();
              lastX = event.getSceneX();
              lastY = event.getSceneY();
              diffX = lastX - linkbutton.getLayoutX();
              diffY = lastY - linkbutton.getLayoutY();
              event.consume();
            };

            // Handle the mouse dragged event on the root pane
            EventHandler<MouseEvent> linkbuttonEventD = event -> {
              double newX = event.getSceneX() - diffX;
              double newY = event.getSceneY() - diffY;
              linkbutton.setLayoutX(newX);
              linkbutton.setLayoutY(newY);
              circle.setLayoutX(newX + linkbutton.getWidth());
              circle.setLayoutY(newY + linkbutton.getHeight());
              event.consume();
            };
            circle.addEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
            circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
            linkbutton.addEventFilter(MouseEvent.MOUSE_PRESSED, linkbuttonEventP);
            linkbutton.addEventFilter(MouseEvent.MOUSE_DRAGGED, linkbuttonEventD);

            editable.addEventHandler(ActionEvent.ACTION, event -> {
              if (editable.isSelected()) {
                circle.addEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
                circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
                linkbutton.addEventFilter(MouseEvent.MOUSE_PRESSED, linkbuttonEventP);
                linkbutton.addEventFilter(MouseEvent.MOUSE_DRAGGED, linkbuttonEventD);

              } else {
                circle.removeEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
                circle.removeEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
                linkbutton.removeEventFilter(MouseEvent.MOUSE_PRESSED, linkbuttonEventP);
                linkbutton.removeEventFilter(MouseEvent.MOUSE_DRAGGED, linkbuttonEventD);

              }
            });
            buttonChange.addEventHandler(ActionEvent.ACTION, event -> {
              if (buttonChange.isSelected()) {
                circle.removeEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
                circle.removeEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
                linkbutton.removeEventFilter(MouseEvent.MOUSE_PRESSED, linkbuttonEventP);
                linkbutton.removeEventFilter(MouseEvent.MOUSE_DRAGGED, linkbuttonEventD);

              } else {
                circle.addEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
                circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
                linkbutton.addEventFilter(MouseEvent.MOUSE_PRESSED, linkbuttonEventP);
                linkbutton.addEventFilter(MouseEvent.MOUSE_DRAGGED, linkbuttonEventD);

              }
            });
          }

          case "linkimage" -> {
            int autoNumber = LinkImage_i.getNextNumber();
            String str = String.valueOf(autoNumber);
            LinkImage linkimage = new LinkImage("linkimage-" + str, new Image(arrayList.get(8)));

            linkimage.setLayoutX(Double.parseDouble(arrayList.get(2)));
            linkimage.setLayoutY(Double.parseDouble(arrayList.get(3)));
            linkimage.setFitWidth(Double.parseDouble(arrayList.get(4)));
            linkimage.setFitHeight(Double.parseDouble(arrayList.get(5)));
            linkimage.setOnMouseClicked(
                event1 -> getHostServices().showDocument(arrayList.get(7)));

            linkimage.setPreserveRatio(true);
            linkimage.setSmooth(false);
            pa.tableNewItem(arrayList.get(1), "linkimage-" + str);

            Circle circle = new Circle(5, Color.WHITE);
            circle.setLayoutX(-10);
            circle.setLayoutY(-10);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);
            circle.setOpacity(0);
            LCreateRoot.getChildren().addAll(circle, linkimage);

            EventHandler<MouseEvent> circleEventP = event -> {
              lastX = event.getSceneX();
              lastY = event.getSceneY();
              lastWidth = linkimage.getFitWidth();
              lastHeight = linkimage.getFitHeight();
              diffX = lastX - linkimage.getLayoutX();
              diffY = lastY - linkimage.getLayoutY();
              event.consume();
            };

            EventHandler<MouseEvent> circleEventD = event -> {
              double newWidth = lastWidth + event.getSceneX() - lastX;
              double newHeight = lastHeight + event.getSceneY() - lastY;
              linkimage.setFitWidth(newWidth);
              linkimage.setFitHeight(newHeight);
              circle.setLayoutX(linkimage.getLayoutX() + linkimage.getFitWidth());
              circle.setLayoutY(linkimage.getLayoutY() + linkimage.getFitHeight());
              event.consume();
            };

            EventHandler<MouseEvent> linkimageEventP = event -> {
              this.callbackID = linkimage.getId();
              lastX = event.getSceneX();
              lastY = event.getSceneY();
              diffX = lastX - linkimage.getLayoutX();
              diffY = lastY - linkimage.getLayoutY();
              event.consume();
            };

            // Handle the mouse dragged event on the root pane
            EventHandler<MouseEvent> linkimageEventD = event -> {
              double newX = event.getSceneX() - diffX;
              double newY = event.getSceneY() - diffY;
              linkimage.setLayoutX(newX);
              linkimage.setLayoutY(newY);
              circle.setLayoutX(newX + linkimage.getFitWidth());
              circle.setLayoutY(newY + linkimage.getFitHeight());
              event.consume();
            };
            circle.addEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
            circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
            linkimage.addEventFilter(MouseEvent.MOUSE_PRESSED, linkimageEventP);
            linkimage.addEventFilter(MouseEvent.MOUSE_DRAGGED, linkimageEventD);

            editable.addEventHandler(ActionEvent.ACTION, event -> {
              if (editable.isSelected()) {
                circle.addEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
                circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
                linkimage.addEventFilter(MouseEvent.MOUSE_PRESSED, linkimageEventP);
                linkimage.addEventFilter(MouseEvent.MOUSE_DRAGGED, linkimageEventD);

              } else {
                circle.removeEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
                circle.removeEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
                linkimage.removeEventFilter(MouseEvent.MOUSE_PRESSED, linkimageEventP);
                linkimage.removeEventFilter(MouseEvent.MOUSE_DRAGGED, linkimageEventD);

              }
            });
            buttonChange.addEventHandler(ActionEvent.ACTION, event -> {
              if (buttonChange.isSelected()) {
                circle.removeEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
                circle.removeEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
                linkimage.removeEventFilter(MouseEvent.MOUSE_PRESSED, linkimageEventP);
                linkimage.removeEventFilter(MouseEvent.MOUSE_DRAGGED, linkimageEventD);

              } else {
                circle.addEventFilter(MouseEvent.MOUSE_PRESSED, circleEventP);
                circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, circleEventD);
                linkimage.addEventFilter(MouseEvent.MOUSE_PRESSED, linkimageEventP);
                linkimage.addEventFilter(MouseEvent.MOUSE_DRAGGED, linkimageEventD);

              }
            });
          }
        }

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }




  public static void main(String[] args) {
    launch(args);
  }

}

