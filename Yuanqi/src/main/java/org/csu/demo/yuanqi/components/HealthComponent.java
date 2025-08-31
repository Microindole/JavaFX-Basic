package org.csu.demo.yuanqi.components;

import com.almasb.fxgl.entity.component.Component;

public class HealthComponent extends Component {
    private int health;
    private Runnable onDied = () -> {}; // 一个空的 "onDied" 回调，默认为空操作

    public HealthComponent(int initialHealth) {
        this.health = initialHealth;
    }

    // 提供一个公共方法，让外部可以设置死亡时要执行的动作
    public void setOnDied(Runnable action) {
        this.onDied = action;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            // 在移除实体之前，执行 onDied 回调
            onDied.run();
            entity.removeFromWorld();
        }
    }

    public int getHealth() {
        return health;
    }
}