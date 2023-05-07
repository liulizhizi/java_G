package se7.GUI;

import javafx.stage.Stage;

public class MyStage extends Stage {

  public MyStage() {
    // 调用父类构造函数
    super();

    // 设置窗口标题
    setTitle("My Stage");

    // 设置窗口大小
    setWidth(400);
    setHeight(300);
  }

  public void doSomething() {
    // 自定义方法
    System.out.println("MyStage: doSomething() called");
  }
}