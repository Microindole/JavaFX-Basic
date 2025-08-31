package org.csu.demo.yuanqi.service;

import com.google.gson.Gson;
import org.csu.demo.yuanqi.dto.GameStateSnapshot;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketBroadcastService {
    
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Gson gson = new Gson();

    public void register(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    public void deregister(WebSocketSession session) {
        sessions.remove(session.getId());
    }

    public void broadcast(GameStateSnapshot snapshot) {
        if (sessions.isEmpty()) return;
        String jsonSnapshot = gson.toJson(snapshot);
        TextMessage message = new TextMessage(jsonSnapshot);
        for (WebSocketSession session : sessions.values()) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                sessions.remove(session.getId());
            }
        }
    }
}