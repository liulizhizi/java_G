package se7;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class reviewApplication extends Application {
    FXMLLoader fxmlLoader = new FXMLLoader(reviewApplication.class.getResource("gpaReview.fxml"));
    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(fxmlLoader.load(), 720, 540);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public Parent getRoot() throws IOException{
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}