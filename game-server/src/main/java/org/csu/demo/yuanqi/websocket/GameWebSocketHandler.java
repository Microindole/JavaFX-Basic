package org.csu.demo.yuanqi.websocket; // 确保包名正确

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    // 使用一个线程安全的列表来存储所有连接的会话
    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 当有新的客户端连接时调用
        sessions.add(session);
        System.out.println("【服务器】新连接: " + session.getId() + " | 当前在线: " + sessions.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 当接收到客户端消息时调用
        String payload = message.getPayload();
        System.out.println("【服务器】收到来自 " + session.getId() + " 的消息: " + payload);
        
        // 将收到的消息广播给所有其他客户端
        broadcast(session, payload);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 当有客户端断开连接时调用
        sessions.remove(session);
        System.out.println("【服务器】连接断开: " + session.getId() + " | 当前在线: " + sessions.size());
    }

    // 广播消息给所有（除自己外）的客户端
    private void broadcast(WebSocketSession self, String message) {
        for (WebSocketSession session : sessions) {
            // if (!session.getId().equals(self.getId())) { // 如果不想发给自己，可以取消这行注释
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            // }
        }
    }
}