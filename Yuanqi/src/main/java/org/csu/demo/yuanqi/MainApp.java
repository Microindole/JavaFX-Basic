package org.csu.demo.yuanqi;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.csu.demo.yuanqi.components.HealthComponent;
import org.csu.demo.yuanqi.components.NetworkComponent;
import org.csu.demo.yuanqi.components.PlayerComponent;
import org.csu.demo.yuanqi.dto.GameStateSnapshot;
import org.csu.demo.yuanqi.dto.PlayerState;
import org.csu.demo.yuanqi.entities.YuanqiFactory;
import org.csu.demo.yuanqi.network.NetworkManager;
import org.csu.demo.yuanqi.types.EntityType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MainApp extends GameApplication {

    private Entity player;
    private VBox loginUI;
    private TimerAction enemySpawnTimer;

    private Map<String, Entity> networkPlayers = new ConcurrentHashMap<>();

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("元气骑士阉割版");
        settings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new YuanqiFactory());
    }

    @Override
    protected void initUI() {
        showLoginUI();
    }

    private void showLoginUI() {
        Text title = FXGL.getUIFactoryService().newText("元气骑士 - 联机版", Color.WHITE, 36);
        TextField usernameField = new TextField();
        usernameField.setPromptText("用户名");
        usernameField.setMaxWidth(300);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("密码");
        passwordField.setMaxWidth(300);
        Button loginButton = new Button("登 录");
        loginButton.setMaxWidth(300);
        Text statusText = FXGL.getUIFactoryService().newText("", Color.RED, 14);
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            loginButton.setDisable(true);
            statusText.setText("登录中...");
            new Thread(() -> {
                try {
                    String response = NetworkManager.getInstance().login(username, password);
                    Platform.runLater(() -> {
                        if ("Login successful!".equals(response)) {
                            statusText.setText("登录成功！");
                            FXGL.getGameScene().removeUINode(loginUI);
                            startGame();
                        } else {
                            statusText.setText("登录失败: " + response);
                            loginButton.setDisable(false);
                        }
                    });
                } catch (Exception e) {
                    Platform.runLater(() -> {
                        statusText.setText("连接错误: " + e.getMessage());
                        loginButton.setDisable(false);
                    });
                }
            }).start();
        });
        loginUI = new VBox(20, title, usernameField, passwordField, loginButton, statusText);
        loginUI.setAlignment(Pos.CENTER);
        loginUI.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
        loginUI.setStyle("-fx-background-color: #282828;");
        FXGL.getGameScene().addUINode(loginUI);
    }

    private void startGame() {
        player = FXGL.spawn("player", FXGL.getAppWidth() / 2.0, FXGL.getAppHeight() / 2.0);
        player.getComponent(HealthComponent.class).setOnDied(this::resetToLogin);
        player.getComponent(NetworkComponent.class).setSnapshotConsumer(this::onStateSnapshot);
        FXGL.onKey(KeyCode.A, () -> { if (player.isActive()) player.getComponent(PlayerComponent.class).moveLeft(); });
        FXGL.onKey(KeyCode.D, () -> { if (player.isActive()) player.getComponent(PlayerComponent.class).moveRight(); });
        FXGL.onKey(KeyCode.W, () -> { if (player.isActive()) player.getComponent(PlayerComponent.class).moveUp(); });
        FXGL.onKey(KeyCode.S, () -> { if (player.isActive()) player.getComponent(PlayerComponent.class).moveDown(); });
        FXGL.onBtnDown(MouseButton.PRIMARY, () -> { if (player.isActive()) player.getComponent(PlayerComponent.class).shoot(); });
        this.enemySpawnTimer = FXGL.run(() -> {
            FXGL.spawn("enemy", FXGL.random(0, FXGL.getAppWidth() - 40), FXGL.random(0, FXGL.getAppHeight() - 40));
        }, Duration.seconds(2));
    }

    private void resetToLogin() {
        // 【核心修正】使用 runOnce 将清理操作推迟到下一帧安全执行
        FXGL.runOnce(() -> {
            // 1. 停止生成敌人
            if (enemySpawnTimer != null) {
                enemySpawnTimer.expire();
            }

            // 2. 清空游戏世界里的所有实体
            FXGL.getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);

            // 3. 清除所有键盘和鼠标的绑定
            FXGL.getInput().clearAll();

            // 4. 重置 player 变量
            this.player = null;

            // 5. 重新显示登录界面
            showLoginUI();
        }, Duration.seconds(0)); // 延迟0秒，意味着在下一帧立即执行
    }


    @Override
    protected void initInput() { /* 留空 */ }

    @Override
    protected void onUpdate(double tpf) {
        if (player != null && player.isActive()) {
            player.getComponent(PlayerComponent.class).rotateToMouse();
        }
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollisionBegin(EntityType.PLAYER, EntityType.ENEMY, (player, enemy) -> {
            // 只处理玩家存活的情况
            if(player.isActive()) {
                player.getComponent(HealthComponent.class).takeDamage(1);
            }
            // 敌人总是被移除
            enemy.removeFromWorld();
        });

        // 将子弹碰撞逻辑放在后面，确保不会有冲突
        FXGL.onCollisionBegin(EntityType.BULLET, EntityType.ENEMY, (bullet, enemy) -> {
            bullet.removeFromWorld();
            enemy.getComponent(HealthComponent.class).takeDamage(1);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void onStateSnapshot(GameStateSnapshot snapshot) {
        if (player == null || !player.isActive()) return;

        // 获取我自己的客户端ID，它是在连接建立时由WebSocket库生成的
        String myId = player.getComponent(NetworkComponent.class).getClientId();

        // 使用 Platform.runLater 确保所有UI操作都在JavaFX主线程上执行
        Platform.runLater(() -> {
            // 移除已经断线的玩家
            networkPlayers.keySet().removeIf(id -> !snapshot.getPlayers().containsKey(id));

            for (PlayerState state : snapshot.getPlayers().values()) {
                if (state.getPlayerId().equals(myId)) {
                    // 这是我自己的状态，忽略（服务器权威模式下也可以用来修正位置）
                    continue;
                }

                Entity entity = networkPlayers.get(state.getPlayerId());
                if (entity == null) {
                    // 新玩家加入，为他生成一个实体
                    entity = FXGL.spawn("networkPlayer", state.getX(), state.getY());
                    networkPlayers.put(state.getPlayerId(), entity);
                }

                // 【同步】更新其他玩家的位置和旋转
                // TODO: 在这里加入插值算法，让移动更平滑
                entity.setPosition(state.getX(), state.getY());
                entity.setRotation(state.getRotation());
            }
        });
    }
}