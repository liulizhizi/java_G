package se7.Function.utils;

public class ControlInfo {
  private final String id;
  private final String name;
  private final double x;
  private final double y;
  private final double width;
  private final double height;
  private final String content;
  private final String image;
  private final String link;

  public ControlInfo(String id, String name, double x, double y, double width, double height, String content, String imagestring, String link) {
    this.id = id;
    this.name = name;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.content = content;
    this.image = imagestring;
    this.link = link;

  }

  // getter and setter methods
  public String getID(){
    return this.id;
  }
  public String getName(){
    return this.name;
  }

  public double getX(){
    return this.x;
  }

  public double getY(){
    return this.y;
  }

  public double getWidth(){
    return this.width;
  }

  public double getHeight(){
    return this.height;
  }

  public String getContent(){
    return content;
  }

  public String getImageString(){
    return image;
  }

  public String getLink(){
    return link;
  }

}
