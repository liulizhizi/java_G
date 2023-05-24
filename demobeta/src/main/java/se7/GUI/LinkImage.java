package se7.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LinkImage extends ImageView {
  private String image = "";
  private String link = "";
  public LinkImage(String var1){
    super();
    this.setId(var1);

  }

  public LinkImage(String var1, String var2){
    super(var2);
    this.setId(var1);

  }

  public LinkImage(String var1, Image var2){
    super(var2);
    this.setId(var1);

  }

  public void setImageString(String image) {
    this.image = image;
  }

  public void setLink(String link){
    this.link = link;
  }

  public String getImageString() {
    return image;
  }
  public String getLink(){
    return link;
  }

}
