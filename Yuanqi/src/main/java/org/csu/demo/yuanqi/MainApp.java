package org.csu.demo.yuanqi;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;
import org.csu.demo.yuanqi.components.HealthComponent;
import org.csu.demo.yuanqi.components.PlayerComponent;
import org.csu.demo.yuanqi.entities.YuanqiFactory;
import org.csu.demo.yuanqi.types.EntityType;
import org.csu.demo.yuanqi.view.LoginScene;

public class MainApp extends GameApplication {

    private Entity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("元气骑士阉割版");
        settings.setVersion("0.1");

        // 【MODIFIED】 Move the SceneFactory setting inside initSettings
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public LoginScene newLoginScene() {
                LoginScene scene = new LoginScene();
                scene.getController().setOnLoginSuccessListener(() -> {
                    FXGL.getGameController().gotoMainMenu();
                });
                return scene;
            }
        });
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new YuanqiFactory());
        player = FXGL.spawn("player", FXGL.getAppWidth() / 2.0, FXGL.getAppHeight() / 2.0);

        // Spawn an enemy every 2 seconds
        FXGL.run(() -> {
            FXGL.spawn("enemy", FXGL.random(0, FXGL.getAppWidth() - 40), FXGL.random(0, FXGL.getAppHeight() - 40));
        }, Duration.seconds(2));
    }

    @Override
    protected void initInput() {
        // Input is now cleaner, just calls methods on the component
        FXGL.onKey(KeyCode.A, () -> player.getComponent(PlayerComponent.class).moveLeft());
        FXGL.onKey(KeyCode.D, () -> player.getComponent(PlayerComponent.class).moveRight());
        FXGL.onKey(KeyCode.W, () -> player.getComponent(PlayerComponent.class).moveUp());
        FXGL.onKey(KeyCode.S, () -> player.getComponent(PlayerComponent.class).moveDown());

        FXGL.onBtnDown(MouseButton.PRIMARY, () -> player.getComponent(PlayerComponent.class).shoot());
    }

    //【新增】onUpdate handles logic that runs every frame
    @Override
    protected void onUpdate(double tpf) {
        // Make the player always look at the mouse
        player.getComponent(PlayerComponent.class).rotateToMouse();
    }

    @Override
    protected void initPhysics() {
        //【修改】Collision logic now uses the HealthComponent
        FXGL.onCollisionBegin(EntityType.BULLET, EntityType.ENEMY, (bullet, enemy) -> {
            bullet.removeFromWorld(); // Bullet is always removed
            enemy.getComponent(HealthComponent.class).takeDamage(1); // Enemy takes 1 damage
        });

        //【新增】Handle player and enemy collision
        FXGL.onCollisionBegin(EntityType.PLAYER, EntityType.ENEMY, (player, enemy) -> {
            // Player takes damage and the enemy is destroyed (like a kamikaze)
            player.getComponent(HealthComponent.class).takeDamage(1);
            enemy.removeFromWorld();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}