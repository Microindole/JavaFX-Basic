package org.csu.demo.basic;

import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello World");
        primaryStage.show();
    }
}
