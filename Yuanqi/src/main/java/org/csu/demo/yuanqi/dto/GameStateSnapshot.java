package org.csu.demo.yuanqi.dto;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class GameStateSnapshot {
    private final long timestamp = System.currentTimeMillis();
    private Map<String, PlayerState> players = new ConcurrentHashMap<>();
}