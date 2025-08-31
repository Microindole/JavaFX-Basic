package org.csu.demo.yuanqi.components;

import com.almasb.fxgl.entity.component.Component;

public class HealthComponent extends Component {
    private int health;

    public HealthComponent(int initialHealth) {
        this.health = initialHealth;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            entity.removeFromWorld(); // If health is 0 or less, remove the entity
        }
    }

    public int getHealth() {
        return health;
    }
}