package org.csu.demo.yuanqi.websocket;

import com.google.gson.Gson;
import org.csu.demo.yuanqi.dto.UserCommand;
import org.csu.demo.yuanqi.service.GameLoopService;
import org.csu.demo.yuanqi.service.WebSocketBroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private GameLoopService gameLoopService;

    @Autowired
    private WebSocketBroadcastService broadcastService;

    private final Gson gson = new Gson();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        broadcastService.register(session);
        System.out.println("【服务器】新连接: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            // 将收到的 JSON 消息转换为 UserCommand 对象
            UserCommand command = gson.fromJson(message.getPayload(), UserCommand.class);
            command.setPlayerId(session.getId()); // 确保 playerId 是服务器端设置的

            // 将指令放入游戏循环的处理队列
            gameLoopService.queueCommand(command);

        } catch (Exception e) {
            // handle json parse error
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        broadcastService.deregister(session);
        System.out.println("【服务器】连接断开: " + session.getId());
    }
}