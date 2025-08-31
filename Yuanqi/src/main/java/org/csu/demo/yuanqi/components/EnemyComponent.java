package org.csu.demo.yuanqi.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import org.csu.demo.yuanqi.types.EntityType;

public class EnemyComponent extends Component {
    private Entity player;
    private double speed = 75; // Slower speed for the enemy

    @Override
    public void onAdded() {
        // When the enemy is added to the world, find the player entity
        this.player = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);
    }

    @Override
    public void onUpdate(double tpf) {
        // On every frame, move towards the player
        if (player.isActive()) {
            entity.translateTowards(player.getPosition(), speed * tpf);
        }
    }
}