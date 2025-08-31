package org.csu.demo.yuanqi.entities;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.csu.demo.yuanqi.components.EnemyComponent;
import org.csu.demo.yuanqi.components.HealthComponent;
import org.csu.demo.yuanqi.components.NetworkComponent;
import org.csu.demo.yuanqi.components.PlayerComponent;
import org.csu.demo.yuanqi.types.EntityType;

// 1. 实现 FXGL 的 EntityFactory 接口
public class YuanqiFactory implements EntityFactory {

    // 2. 使用 @Spawns 注解将方法与实体名称绑定
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.PLAYER) // 设置实体类型
                .viewWithBBox(new Rectangle(30, 30, Color.BLUE)) // 视图和碰撞箱
                .with(new PlayerComponent()) // 添加玩家逻辑组件
                .with(new HealthComponent(10))
                .with(new NetworkComponent())
                .collidable() // 开启碰撞
                .build();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox(new Rectangle(40, 40, Color.RED))
                .with(new EnemyComponent()) //【新增】Add enemy AI component
                .with(new HealthComponent(3))  //【新增】Enemy has 3 health
                .collidable()
                .build();
    }

    @Spawns("bullet")
    public Entity newBullet(SpawnData data) {
        Point2D direction = data.get("direction");
        return FXGL.entityBuilder(data)
                .type(EntityType.BULLET)
                .viewWithBBox(new Rectangle(10, 5, Color.BLACK))
                .with(new ProjectileComponent(direction, 600)) // Give it a direction and speed
                .collidable()
                .build();
    }
}