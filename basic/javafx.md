# JavaFX

## 根布局

根布局是 `Scene` 对象的第一个，也是唯一的直接子节点。你可以把它想象成一个房子的“地基和框架”，所有其他的组件（按钮、文本框等）和嵌套的布局都将放置在这个框架之上。

Java

```
// VBox就是这个场景(Scene)的根布局
VBox root = new VBox();
Scene scene = new Scene(root, 800, 600);
```

下面我们详细介绍几种最适合做根布局的容器，并分析它们的特点和适用场景。

------



### 1. `BorderPane`：经典的五方位布局



`BorderPane` 是最常用、最灵活的根布局之一，它将窗口划分为五个区域：顶部 (Top)、底部 (Bottom)、左侧 (Left)、右侧 (Right) 和中心 (Center)。

- **核心思想**：如同一个经典的网页或桌面应用程序框架。

- **工作原理**：

  - Top 和 Bottom 区域会占据其内容的推荐高度，宽度则延伸至整个窗口宽。
  - Left 和 Right 区域会占据其内容的推荐宽度，高度则填充 Top 和 Bottom 之间的剩余空间。
  - **Center 区域会自动填充所有剩余的空间**，这是 `BorderPane` 最强大的特性。当窗口拉伸时，Center 区域会随之变大。

- **适用场景**：

  - 几乎所有典型的桌面应用主窗口。
  - 顶部放置菜单栏或工具栏。
  - 左侧放置导航树或导航菜单。
  - 底部放置状态栏。
  - 中心放置主要的工作区或内容显示区。

- **关键代码**：

  Java

  ```
  BorderPane root = new BorderPane();
  root.setTop(new ToolBar(...));
  root.setLeft(new VBox(...)); // 导航栏
  root.setCenter(new TextArea(...)); // 主要内容
  root.setBottom(new Label("状态：就绪"));
  ```



### 2. `VBox` 和 `HBox`：线性布局



这两个是最基础的布局，一个垂直排列，一个水平排列。



#### `VBox` (垂直盒子)



- **核心思想**：将所有子节点从上到下垂直堆叠成一列。
- **适用场景**：
  - 简单的表单（标签和输入框垂直排列）。
  - 设置窗口或对话框。
  - 左侧的导航栏本身。
- **关键属性**：
  - `spacing`：设置子节点之间的垂直间距。
  - `alignment`：设置子节点在水平方向上的对齐方式（如 `Pos.CENTER_LEFT`）。
  - `VBox.setVgrow()`：设置某个子节点是否应该在垂直方向上拉伸以填充额外空间。



#### `HBox` (水平盒子)



- **核心思想**：将所有子节点从左到右水平排列成一行。

- **适用场景**：

  - 顶部的工具栏或菜单栏。
  - 对话框底部的“确定”、“取消”按钮行。

- **关键属性**：

  - `spacing`：设置子节点之间的水平间距。
  - `alignment`：设置子节点在垂直方向上的对齐方式（如 `Pos.CENTER`）。
  - `HBox.setHgrow()`：设置某个子节点是否应该在水平方向上拉伸以填充额外空间（常用于搜索框）。

- **关键代码 (HBox)**：

  Java

  ```
  HBox root = new HBox(10); // 间距为10
  root.setPadding(new Insets(15));
  root.setAlignment(Pos.CENTER);
  root.getChildren().addAll(new Button("确定"), new Button("取消"));
  ```



### 3. `GridPane`：网格布局



`GridPane` 是功能最强大的布局之一，它允许你将子节点放置在灵活的行列网格中，就像一个表格或棋盘。

- **核心思想**：在一个不可见的网格单元格中精确放置组件。

- **工作原理**：通过指定行列的索引来添加节点。可以定义列宽和行高的约束，使网格具有非常灵活的自适应能力。节点还可以跨越多行或多列 (`rowSpan`, `columnSpan`)。

- **适用场景**：

  - 复杂的表单和登录界面（例如，标签在第一列，输入框在第二列）。
  - 计算器界面。
  - 需要精确对齐的任何复杂布局。

- **关键代码**：

  Java

  ```
  GridPane root = new GridPane();
  root.setHgap(10); // 水平间距
  root.setVgap(10); // 垂直间距
  
  // 在第0列，第0行添加一个标签
  root.add(new Label("用户名:"), 0, 0); 
  // 在第1列，第0行添加一个输入框
  root.add(new TextField(), 1, 0); 
  
  root.add(new Label("密码:"), 0, 1);
  root.add(new PasswordField(), 1, 1);
  ```



### 4. `StackPane`：堆叠布局



`StackPane` 会将所有子节点像一叠纸一样堆叠起来，后添加的节点会显示在先添加的节点之上。

- **核心思想**：在 Z 轴（深度）方向上重叠节点。

- **工作原理**：默认情况下，所有子节点都会居中对齐。你可以修改对齐方式来让它们对齐到不同的角落。

- **适用场景**：

  - 在图片上叠加文字（如水印或标题）。
  - 在主界面上显示一个加载指示器或“加载中”的遮罩层。
  - 游戏中的 HUD (Heads-Up Display)，将玩家信息叠加在游戏画面上。

- **关键代码**：

  Java

  ```
  StackPane root = new StackPane();
  ImageView background = new ImageView("background.jpg");
  Label title = new Label("壮丽的山川");
  title.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.5);");
  
  // background在底层, title在顶层
  root.getChildren().addAll(background, title); 
  ```



### 5. `AnchorPane`：锚点布局



`AnchorPane` 允许你将子节点的边缘“锚定”到父容器的边缘。

- **核心思想**：通过设置节点到容器四边的距离来定位。

- **工作原理**：你可以为一个节点设置上、下、左、右四个方向的锚点距离。当容器大小改变时，节点会保持与锚定边的距离不变，从而实现拉伸或固定位置。

- **适用场景**：

  - 当需要某个按钮始终固定在右下角时。
  - 当需要一个组件的宽度随窗口宽度拉伸，但与左右边框保持固定距离时。
  - 类似 Photoshop 或其他设计软件的工具面板布局。

- **关键代码**：

  Java

  ```
  AnchorPane root = new AnchorPane();
  Button closeButton = new Button("X");
  
  // 将按钮锚定在右上角，距离顶部和右边各10像素
  AnchorPane.setTopAnchor(closeButton, 10.0);
  AnchorPane.setRightAnchor(closeButton, 10.0);
  
  root.getChildren().add(closeButton);
  ```



### 总结与选择建议



| 布局名称            | 核心特点                     | 最适用作根布局的场景                             |
| ------------------- | ---------------------------- | ------------------------------------------------ |
| **`BorderPane`**    | 五方位布局，中心区域自动填充 | 绝大多数标准桌面应用的主窗口                     |
| **`VBox` / `HBox`** | 垂直/水平线性排列            | 简单的、单一方向的窗口，如设置对话框、工具栏     |
| **`GridPane`**      | 二维网格，精确定位           | 复杂的表单、数据录入界面、需要严格对齐的仪表盘   |
| **`StackPane`**     | Z轴堆叠，节点重叠            | 需要在背景上叠加UI元素（如文字、加载动画）的场景 |
| **`AnchorPane`**    | 锚定到边缘，固定或拉伸       | 需要组件固定在特定角落或随窗口边缘拉伸的场景     |

**最佳实践：嵌套使用**

在真实项目中，很少只使用一种布局。最强大的方式是将它们**嵌套使用**。一个典型的例子：

- 使用 `BorderPane` 作为根布局。
- 在 Top 区域放一个 `HBox` 作为工具栏。
- 在 Left 区域放一个 `VBox` 作为导航菜单。
- 在 Center 区域放一个 `ScrollPane`，`ScrollPane` 里面再放一个 `GridPane` 来显示复杂的数据。



## 模态窗口

### 1. 什么是模态窗口？



首先，我们从概念上理解“模态”。

**模态 (Modal)** 是一种窗口行为模式。当一个窗口以模态形式打开时，它会**“霸占”用户的焦点**，并**阻止用户与应用程序的其他窗口进行交互**，直到这个模态窗口被关闭。

你可以把它想象成一个“强制对话框”。应用程序弹出一个问题或信息，并对你说：“嘿，在处理完我这里的事情之前，你别想去操作后面的主窗口。”

**常见的例子：**

- **文件保存提示**：当你关闭一个未保存的文档时，弹出的“是否保存更改？”对话框就是模态的。你必须点击“是”、“否”或“取消”，才能继续其他操作。
- **错误提示框**：应用程序发生错误时弹出的警告框。
- **登录窗口**：在主应用启动前，要求你必须输入用户名和密码的窗口。

与之相对的是**非模态 (Non-modal)** 窗口，它允许你在打开它的同时，自由地与应用程序的其他窗口交互，例如软件中的工具面板、属性检查器等。

------



### 2. JavaFX 如何实现模态窗口



在 JavaFX 中，任何一个窗口都是一个 `Stage` 对象。要将一个普通的 `Stage` 变成模态窗口，你需要做两件核心的事情：

1. **`initOwner(Window owner)`**：设置窗口的“所有者”。你需要告诉这个新的模态窗口，它“属于”哪个窗口。通常，它的所有者就是打开它的那个主窗口。
2. **`initModality(Modality modality)`**：设置模态的类型。这是实现模态行为最关键的一步。



#### `Modality` 枚举类型详解



`Modality` 是一个枚举类，它有三个可选值，决定了窗口的“霸道”程度：

- **`Modality.NONE`**
  - **行为**：默认值。非模态。这个窗口不会阻塞任何其他窗口。
  - **用途**：适用于独立的、可以和主窗口并存的窗口，如工具箱、属性面板等。
- **`Modality.WINDOW_MODAL`**
  - **行为**：**窗口级别**的模态。它只会阻塞它的**直接所有者（Owner）窗口**。如果你的应用有多个独立的窗口，它只对那个“生”出它的窗口生效，其他窗口不受影响。
  - **用途**：这是最常用的模态类型。适用于与特定窗口相关的操作，例如，在一个多文档编辑器中，一个文档窗口的“查找/替换”对话框，它只应该阻塞当前的文档窗口，而不应该影响你编辑另一个文档。
- **`Modality.APPLICATION_MODAL`**
  - **行为**：**应用级别**的模态。它会阻塞这个 JavaFX 应用程序的**所有窗口**。只要这个模态窗口不关闭，整个应用都无法操作。
  - **用途**：适用于全局性的、非常重要的操作。例如，在应用启动时必须完成的登录窗口、严重错误的全局警报、要求重启应用的通知等。

------



### 3. `show()` vs `showAndWait()`：一个关键区别



当你显示一个模态窗口时，通常有两种方法，它们有本质的区别：

- **`stage.show()`**
  - **行为**：**异步**执行。调用这行代码后，窗口会显示出来，但程序会**立即继续往下执行**。
  - **用途**：不常用。虽然窗口本身是模态的（UI被阻塞），但启动它的代码流并不会等待它关闭。
- **`stage.showAndWait()`**
  - **行为**：**同步**执行。调用这行代码后，窗口会显示出来，并且**代码会停在这一行，直到这个模态窗口被关闭**。
  - **用途**：**这是用于模态对话框的正确且最常用的方法**。因为你通常需要等待用户的输入结果（比如用户点击了“确定”还是“取消”）才能决定下一步做什么。

------



### 4. 完整代码示例



下面是一个完整的、可运行的例子，演示了如何创建一个 `APPLICATION_MODAL` 类型的模态窗口，并使用 `showAndWait()` 来等待它的关闭。

Java

```
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalWindowExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("主窗口");

        Button openModalButton = new Button("打开模态窗口");

        openModalButton.setOnAction(event -> {
            // 创建一个新的 Stage 作为模态窗口
            final Stage modalStage = new Stage();
            modalStage.setTitle("这是一个模态窗口");

            // 1. 关键步骤：设置模态类型
            // Modality.APPLICATION_MODAL 会阻塞应用的所有窗口
            // Modality.WINDOW_MODAL 只会阻塞 primaryStage
            modalStage.initModality(Modality.APPLICATION_MODAL);

            // 2. 关键步骤：设置所有者
            modalStage.initOwner(primaryStage);

            // 设置模态窗口的场景和内容
            Label label = new Label("你必须先关闭我，才能操作主窗口。");
            Button closeButton = new Button("关闭");
            closeButton.setOnAction(e -> modalStage.close());

            VBox modalLayout = new VBox(20, label, closeButton);
            modalLayout.setAlignment(Pos.CENTER);

            Scene modalScene = new Scene(modalLayout, 400, 200);
            modalStage.setScene(modalScene);
            
            // 将模态窗口的位置设置在主窗口的中心
            modalStage.setX(primaryStage.getX() + primaryStage.getWidth() / 2 - modalScene.getWidth() / 2);
            modalStage.setY(primaryStage.getY() + primaryStage.getHeight() / 2 - modalScene.getHeight() / 2);


            // 3. 使用 showAndWait() 来显示窗口并等待它关闭
            System.out.println("模态窗口即将显示，代码将在此处阻塞...");
            modalStage.showAndWait();

            // 当模态窗口关闭后，下面的代码才会执行
            System.out.println("模态窗口已关闭，代码继续执行。");
        });

        VBox root = new VBox(20, openModalButton);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```



#### 如何运行和观察：



1. 运行上面的代码，你会看到一个“主窗口”。
2. 点击“打开模态窗口”按钮。
3. 此时会弹出一个新的、标题为“这是一个模态窗口”的窗口。
4. 尝试去点击或拖动后面的“主窗口”，你会发现它完全没有反应，因为焦点被模态窗口锁定了。
5. 观察你的IDE控制台，你会看到输出了 "模态窗口即将显示，代码将在此处阻塞..."。
6. 点击模态窗口上的“关闭”按钮。
7. 模态窗口消失后，“主窗口”恢复正常交互。
8. 同时，控制台会输出 "模态窗口已关闭，代码继续执行。" 这证明了 `showAndWait()` 的阻塞和同步特性。



### 总结



| 特性                    | 解释                                                         | 关键代码                                               |
| ----------------------- | ------------------------------------------------------------ | ------------------------------------------------------ |
| **所有者 (Owner)**      | 定义模态窗口属于谁，影响其相对位置和 `WINDOW_MODAL` 的作用范围。 | `modalStage.initOwner(primaryStage);`                  |
| **模态类型 (Modality)** | 定义阻塞范围，是窗口级别还是应用级别。                       | `modalStage.initModality(Modality.APPLICATION_MODAL);` |
| **显示与等待**          | 以同步阻塞的方式显示窗口，并等待其关闭以获取结果或继续流程。 | `modalStage.showAndWait();`                            |

Export to Sheets

正确地使用模态窗口可以引导用户完成必要的操作流程，是构建健壮桌面应用不可或缺的一部分。