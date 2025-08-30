package org.csu.demo.basic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

public class HelloApplication extends Application {

    // Application 类有调用主机服务的方法

    public static void main(String[] args) {
        Application.launch(args);
        // launch 方法默认调用 init的方法，其次是start方法,
        // 最后是stop方法
        // stop方法是在应用程序关闭时调用的
    }

//    @Override
//    public void init() throws Exception {
//        super.init();
//        System.out.println("init...");
//    }

    // Stage 类表示窗口
    // 设置窗口图标： Stage的getIcons。add
    //                  里面放一个image的实例
    // 窗口样式设置 initStyle(StageStyle.下面四个枚举类型之一)
    // DECORATED UN~ TRANSPARENT UTILITY
    // UNDECORATED 用于自定义窗口类型

    @Override
    public void start(Stage primaryStage) throws Exception{

//        Label label = new Label("Hello World");
////        BorderPane 默认有上下左右中五个布局，默认是中
//        BorderPane pane = new BorderPane(label);
//        Scene scene = new Scene(pane, 300, 300);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("This is window");
//        primaryStage.show();
//
//        System.out.println("start...");

        Button button = new Button("Hello World");
        BorderPane root = new BorderPane(button);

        button.setOnAction(event -> {
            getHostServices().showDocument("www.bilibili.com");
        });
        Scene scene = new Scene(root,800, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello World");
        primaryStage.getIcons().add(new Image("https://www.bilibili.com"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

//    @Override
//    public void stop() throws Exception {
//        super.stop();
//        System.out.println("stop...");
//    }


}
