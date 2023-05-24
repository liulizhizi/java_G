package se7.Function.utils;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmDialog extends Stage {

  private boolean confirmed = false;

  public ConfirmDialog(String message) {
    setTitle("Confirmation");
    Label label = new Label(message);
    Button yesButton = new Button("Continue");
    yesButton.setOnAction(event -> {
      confirmed = true;
      close();
    });
    Button noButton = new Button("Cancel");
    noButton.setOnAction(event -> {
      confirmed = false;
      close();
    });
    HBox buttonBox = new HBox(10, yesButton, noButton);
    Scene scene = new Scene(new VBox(20, label, buttonBox), 300, 100);
    setScene(scene);

  }

  public boolean isConfirmed() {
    return confirmed;
  }
}

