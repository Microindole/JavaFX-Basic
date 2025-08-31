package org.csu.demo.yuanqi.dto;

import lombok.Data;

@Data
public class PlayerState {
    private String playerId;
    private double x, y;
    private double rotation;

    public PlayerState(String playerId, double x, double y, double rotation) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }
    
    // --- Getters and Setters ---
}