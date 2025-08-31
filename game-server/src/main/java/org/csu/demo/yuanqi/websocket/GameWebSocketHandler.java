package org.csu.demo.yuanqi.websocket;

import com.google.gson.Gson;
import org.csu.demo.yuanqi.dto.UserCommand;
import org.csu.demo.yuanqi.service.GameLoopService;
import org.csu.demo.yuanqi.service.GameStateService;
import org.csu.demo.yuanqi.service.WebSocketBroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    @Autowired private GameLoopService gameLoopService;
    @Autowired private WebSocketBroadcastService broadcastService;
    @Autowired private GameStateService gameStateService; // 注入GameStateService
    private final Gson gson = new Gson();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        broadcastService.register(session);
        System.out.println("【服务器】新连接: " + session.getId());

        // 【核心修改】向新连接的客户端发送它的专属ID
        String welcomeMessage = String.format("{\"type\":\"WELCOME\", \"id\":\"%s\"}", session.getId());
        try {
            session.sendMessage(new TextMessage(welcomeMessage));
        } catch (IOException e) {
            System.err.println("发送欢迎消息失败: " + e.getMessage());
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            UserCommand command = gson.fromJson(message.getPayload(), UserCommand.class);
            command.setPlayerId(session.getId());
            gameLoopService.queueCommand(command);
        } catch (Exception e) {
            System.err.println("JSON解析错误: " + message.getPayload());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        broadcastService.deregister(session);
        gameStateService.removePlayer(session.getId()); // 玩家断开连接时，从游戏状态中移除
        System.out.println("【服务器】连接断开: " + session.getId());
    }
}