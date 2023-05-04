package se7.GUI;

import javafx.scene.Node;
import javafx.scene.control.Button;


public class LinkButton extends Button {

  //protected String link; // most important
  public LinkButton(String var1) {
    this(var1, "");
  }
  public LinkButton(String var1, String var2){
    super(var2);
    this.setId(var1); // this is the name id of NoteBook, that Used to tag NoteBook for user.

  }
  public LinkButton(String var1, String var2, Node var3){
    super(var2, var3);
    this.setId(var1); // this is the name id of NoteBook, that Used to tag NoteBook for user.
    //this.setOnAction(e-> {
    //      getHostServices().showDocument("D:/360MoveData/Users/13521/Desktop/cankao.txt");
    //    });
  }

}
