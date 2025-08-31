package org.csu.demo.yuanqi.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.csu.demo.yuanqi.dto.GameStateSnapshot;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;


public class GameClient extends WebSocketClient {

    private Consumer<GameStateSnapshot> snapshotConsumer;
    private final Gson gson = new Gson();
    private String myId;

    public GameClient(URI serverUri) {
        super(serverUri);
    }

    public String getMyId() {
        return myId;
    }

    public void setSnapshotConsumer(Consumer<GameStateSnapshot> consumer) {
        this.snapshotConsumer = consumer;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("【客户端】成功连接到服务器！");
    }

    @Override
    public void onMessage(String message) {
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);

        if (jsonObject.has("type") && jsonObject.get("type").getAsString().equals("WELCOME")) {
            // 如果是服务器发送的欢迎消息，就保存我的ID
            this.myId = jsonObject.get("id").getAsString();
            System.out.println("【客户端】已分配ID: " + this.myId);
        } else if (jsonObject.has("players")) {
            // 否则，它应该是一个游戏世界快照
            GameStateSnapshot snapshot = gson.fromJson(message, GameStateSnapshot.class);
            if (snapshotConsumer != null) {
                snapshotConsumer.accept(snapshot);
            }
        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("【客户端】与服务器断开连接。原因: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("【客户端】发生错误: " + ex.getMessage());
    }
}