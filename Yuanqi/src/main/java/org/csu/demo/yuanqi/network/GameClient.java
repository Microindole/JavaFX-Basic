package org.csu.demo.yuanqi.network;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class GameClient extends WebSocketClient {

    public GameClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("【客户端】成功连接到服务器！");
    }

    @Override
    public void onMessage(String message) {
        // 当从服务器接收到消息时调用
        System.out.println("【客户端】收到消息: " + message);
        // TODO: 在这里解析消息，并更新其他玩家的状态
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