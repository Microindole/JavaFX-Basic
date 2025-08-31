package org.csu.demo.yuanqi.service;

import org.csu.demo.yuanqi.dto.UserCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class GameLoopService {

    public static final long TICK_RATE = 64; // 每秒64个Tick
    private final Queue<UserCommand> commandQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // TODO: 在这里创建和管理游戏世界状态
    // private GameState gameState = new GameState();

    @Autowired
    private WebSocketBroadcastService broadcaster; // 我们将创建一个广播服务

    // Spring Boot 的 @Scheduled 注解，让这个方法以固定的频率被调用
    @Scheduled(fixedRate = 1000 / TICK_RATE)
    public void gameLoop() {
        // 1. 处理所有收到的玩家指令（Sub-tick的核心在这里）
        processInputs();

        // 2. 更新游戏世界状态（例如AI移动、物理计算等）
        // gameState.update(1.0 / TICK_RATE);

        // 3. 将最新的世界状态快照广播给所有客户端
        // GameStateSnapshot snapshot = gameState.createSnapshot();
        // broadcaster.broadcast(snapshot);
    }

    // 将来自WebSocket的指令放入队列
    public void queueCommand(UserCommand command) {
        commandQueue.add(command);
    }

    private void processInputs() {
        // 这里是 Sub-tick 的简化实现
        // 实际的 Sub-tick 需要更复杂的基于时间戳的排序和模拟
        while (!commandQueue.isEmpty()) {
            UserCommand command = commandQueue.poll();
            // 在这里，我们暂时先简单地立即应用指令
            // TODO: 根据 command.getTimestamp() 来精确模拟
            applyCommand(command);
        }
    }
    
    private void applyCommand(UserCommand command) {
        // TODO: 根据指令更新 gameState 中的玩家状态
        System.out.println("Applying command from " + command.getPlayerId() + " of type " + command.getType());
    }
    
    // session 管理
    public void registerSession(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session.getId());
    }
}