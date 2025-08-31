package org.csu.demo.yuanqi.service;

import org.csu.demo.yuanqi.dto.UserCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class GameLoopService {
    public static final long TICK_RATE = 30; // 降低Tick率便于调试，之后可调回64
    private final Queue<UserCommand> commandQueue = new ConcurrentLinkedQueue<>();

    @Autowired
    private GameStateService gameStateService;
    @Autowired private WebSocketBroadcastService broadcaster;

    @Scheduled(fixedRate = 1000 / TICK_RATE)
    public void gameLoop() {
        processInputs();
        // 未来可以在这里更新敌人AI等
        broadcaster.broadcast(gameStateService.createSnapshot());
    }

    public void queueCommand(UserCommand command) {
        commandQueue.add(command);
    }

    private void processInputs() {
        while (!commandQueue.isEmpty()) {
            UserCommand command = commandQueue.poll();
            gameStateService.updatePlayerState(command);
        }
    }
}