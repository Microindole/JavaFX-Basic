package org.csu.demo.yuanqi.service;

import org.csu.demo.yuanqi.dto.PlayerState;
import org.csu.demo.yuanqi.dto.UserCommand;
import org.csu.demo.yuanqi.dto.GameStateSnapshot;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameStateService {

    private final Map<String, PlayerState> playerStates = new ConcurrentHashMap<>();

    public void updatePlayerState(UserCommand command) {
        PlayerState state = new PlayerState(command.getPlayerId(), command.getX(), command.getY(), command.getRotation());
        playerStates.put(command.getPlayerId(), state);
    }

    public void removePlayer(String playerId) {
        playerStates.remove(playerId);
    }

    public GameStateSnapshot createSnapshot() {
        GameStateSnapshot snapshot = new GameStateSnapshot();
        snapshot.setPlayers(new ConcurrentHashMap<>(playerStates)); // 创建一个副本以保证线程安全
        return snapshot;
    }
}