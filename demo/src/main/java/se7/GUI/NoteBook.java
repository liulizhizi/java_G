package se7.GUI;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NoteBook extends TextArea {
  public NoteBook(String var1) {
    this(var1, "");
  }
  public NoteBook(String var1, String var2){
    super(var2);
    this.setId(var1);// this is the name id of NoteBook, that Used to tag NoteBook for user.
    //this.Four_Corners(this.getLayoutX(), this.getLayoutY(), this.getWidth(), this.getHeight());
  }
  private void Four_Corners(double x, double y, double w, double h) {


  }


}
