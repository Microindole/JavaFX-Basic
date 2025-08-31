package org.csu.demo.yuanqi.util;// 在一个 util 或 manager 包下
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {
    private static Stage primaryStage;

    public static void init(Stage stage) {
        primaryStage = stage;
    }

    public static void loadScene(String fxmlFile) throws IOException {
        // 注意 getResource 的路径，确保能找到你的 fxml 文件
        Parent root = FXMLLoader.load(SceneManager.class.getResource("/fxml/" + fxmlFile));
        primaryStage.getScene().setRoot(root);
    }
    
    // 您需要一个方法来切换到 FXGL 游戏视图
    public static void startGame() {
        // 这一步比较复杂。您需要在这里初始化并启动您的 FXGL GameApplication。
        // 一种方法是在您的主启动类中有一个返回 FXGL 场景的方法。
        System.out.println("切换到游戏场景是下一步的工作！");
    }
}