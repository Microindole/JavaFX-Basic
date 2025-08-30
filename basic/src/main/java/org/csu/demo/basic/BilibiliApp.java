package org.csu.demo.basic;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Optional;
// generate by Gemini
public class BilibiliApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("仿Bilibili布局示例");

        // 使用 BorderPane 作为根布局
        BorderPane root = new BorderPane();

        // 1. 创建顶部栏
        HBox topBar = createTopBar();
        root.setTop(topBar);

        // 2. 创建左侧导航栏
        VBox leftNav = createLeftNav();
        root.setLeft(leftNav);

        // 3. 创建中心内容区
        ScrollPane contentArea = createContentArea();
        root.setCenter(contentArea);

        // 4. 创建场景
        Scene scene = new Scene(root, 1200, 800);
        // (可选) 引入CSS文件进行美化
        // scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // 5. 设置窗口关闭请求事件
        primaryStage.setOnCloseRequest(event -> {
            // 消费掉默认的关闭事件，我们自己处理
            event.consume();
            // 调用自定义的退出确认方法
            showExitConfirmation(primaryStage);
        });

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    // 创建顶部栏
    private HBox createTopBar() {
        HBox topBar = new HBox(15);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #FFFFFF; -fx-border-width: 0 0 1 0; -fx-border-color: #E3E5E7;");

        Label logo = new Label("bilibili");
        logo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        logo.setTextFill(Color.web("#FB7299"));

        TextField searchField = new TextField();
        searchField.setPromptText("搜索视频...");
        searchField.setPrefWidth(400);

        // 弹簧, 用于将右侧图标推到最右边
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // 模拟右侧的用户图标
        Circle userAvatar = new Circle(18, Color.LIGHTGRAY);
        Label userName = new Label("User");

        topBar.getChildren().addAll(logo, searchField, spacer, userAvatar, userName);
        return topBar;
    }

    // 创建左侧导航栏
    private VBox createLeftNav() {
        VBox leftNav = new VBox(10);
        leftNav.setPadding(new Insets(10));
        leftNav.setPrefWidth(180);
        leftNav.setStyle("-fx-background-color: #F6F7F8;");

        leftNav.getChildren().addAll(
                createNavButton("首页"),
                createNavButton("动态"),
                createNavButton("热门"),
                createNavButton("频道"),
                createNavButton("我的收藏"),
                createNavButton("历史记录")
        );
        return leftNav;
    }

    // 辅助方法：创建导航按钮
    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle("-fx-background-color: transparent; -fx-font-size: 14px;");
        return button;
    }

    // 创建中心内容区
    private ScrollPane createContentArea() {
        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(20));
        tilePane.setHgap(20); // 水平间距
        tilePane.setVgap(20); // 垂直间距

        // 模拟添加20个视频卡片
        for (int i = 0; i < 20; i++) {
            tilePane.getChildren().add(createVideoCard("这是一个有趣的视频标题 " + (i + 1), "UP主: Gemini"));
        }

        // 将TilePane放入ScrollPane，以便内容可以滚动
        ScrollPane scrollPane = new ScrollPane(tilePane);
        scrollPane.setFitToWidth(true); // 宽度自适应
        scrollPane.setStyle("-fx-background-color: #FFFFFF;");
        return scrollPane;
    }

    // 辅助方法：创建视频卡片
    private Node createVideoCard(String title, String author) {
        VBox card = new VBox(5);
        card.setPrefWidth(220);

        // 视频封面占位符
        Rectangle thumbnail = new Rectangle(220, 124);
        thumbnail.setFill(Color.LIGHTGRAY);
        thumbnail.setArcWidth(10);
        thumbnail.setArcHeight(10);

        Label titleLabel = new Label(title);
        titleLabel.setWrapText(true); // 标题自动换行
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        Label authorLabel = new Label(author);
        authorLabel.setTextFill(Color.GRAY);

        card.getChildren().addAll(thumbnail, titleLabel, authorLabel);
        return card;
    }

    // 显示退出确认对话框的方法
    private void showExitConfirmation(Stage stage) {
        // 创建一个确认类型的对话框
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("退出确认");
        alert.setHeaderText("您确定要退出应用程序吗？"); // 对话框头部的文本
        alert.setContentText("所有未保存的进度将会丢失。"); // 对话框内容

        // 设置所有者，让对话框显示在主窗口之上
        alert.initOwner(stage);

        // 显示对话框并等待用户的响应
        Optional<ButtonType> result = alert.showAndWait();

        // 检查用户的选择
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // 如果用户点击了“确定”按钮
            System.out.println("程序已退出。");
            stage.close(); // 真正地关闭窗口
        } else {
            // 如果用户点击了“取消”或关闭了对话框
            System.out.println("退出操作已取消。");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}