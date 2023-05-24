package se7;

import java.nio.file.Paths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PageController {


    @FXML
    private ImageView imgView;
    public void initialize() {
        // 加载图像并设置到 ImageView
        Image image = new Image(getClass().getResource("1.jpg").toExternalForm());
        imgView.setImage(image);
        imgView.setFitWidth(720);
        imgView.setFitHeight(540);
    }

}


