package se7;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class pages extends Application {
    FXMLLoader fxmlLoader = new FXMLLoader(pages.class.getResource("page.fxml"));
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("page!");
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