package test.server; // 这是一个临时的包

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class MockServer extends WebSocketServer {

    public MockServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // 当有新的客户端连接时调用
        System.out.println("【服务器】新连接: " + conn.getRemoteSocketAddress());
        broadcast("一个新玩家加入了！");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // 当有客户端断开连接时调用
        System.out.println("【服务器】连接断开: " + conn.getRemoteSocketAddress() + " 原因: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // 当接收到客户端消息时调用
        System.out.println("【服务器】收到来自 " + conn.getRemoteSocketAddress() + " 的消息: " + message);
        // 将收到的消息广播给所有连接的客户端
        broadcast(message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("【服务器】发生错误: " + ex.getMessage());
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("====== 模拟服务器已启动在端口 " + getPort() + " ======");
        System.out.println("====== 等待客户端连接... ======");
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8887; // 定义一个端口号

        // 创建并启动服务器
        MockServer server = new MockServer(new InetSocketAddress(host, port));
        server.start();
    }
}