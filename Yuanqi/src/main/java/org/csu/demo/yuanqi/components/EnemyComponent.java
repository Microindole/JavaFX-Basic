package org.csu.demo.yuanqi.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import org.csu.demo.yuanqi.types.EntityType;

import java.util.Optional;

public class EnemyComponent extends Component {
    // 【修改】不再在 onAdded 中初始化 player，声明为 Optional 类型更安全
    private Optional<Entity> player = Optional.empty();
    private double speed = 75; // Slower speed for the enemy

    @Override
    public void onAdded() {
        // 【修改】onAdded 方法现在什么都不做，或者只做不依赖其他实体的初始化
    }

    @Override
    public void onUpdate(double tpf) {
        // 【核心修正】
        // 1. 检查 player 是否已经找到，或者玩家是否已经死亡 (不再 active)
        if (player.isEmpty() || !player.get().isActive()) {
            // 2. 如果是，则重新在游戏世界里查找玩家
            player = FXGL.getGameWorld().getSingletonOptional(EntityType.PLAYER);
        }

        // 3. 只有在确认找到了存活的玩家时，才执行移动逻辑
        player.ifPresent(p -> {
            entity.translateTowards(p.getPosition(), speed * tpf);
        });
    }
}