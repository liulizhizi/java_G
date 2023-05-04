package se7;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PageController {


    @FXML
    private ImageView imgView;

    @FXML
    void achievementsAction(ActionEvent event) {
        imgView.setImage(null);
    }

    @FXML
    void backAction(ActionEvent event) {

        imgView.setImage(new Image("1.jpg"));

    }

    @FXML
    void noteAction(ActionEvent event) {
        imgView.setImage(null);
    }

    @FXML
    void timelineAction(ActionEvent event) {
        imgView.setImage(null);
    }

}


