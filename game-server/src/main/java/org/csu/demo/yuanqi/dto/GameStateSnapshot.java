package org.csu.demo.yuanqi.dto;

import java.util.Map;

public class GameStateSnapshot {
    private long timestamp;
    private Map<String, PlayerState> playerStates;
    // 未来还可以加入 Map<String, EnemyState> enemyStates; 等

    // 省略构造函数、getter和setter
}