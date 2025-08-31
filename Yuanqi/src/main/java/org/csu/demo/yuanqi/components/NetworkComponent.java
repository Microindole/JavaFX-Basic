package org.csu.demo.yuanqi.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.util.Duration;
import org.csu.demo.yuanqi.network.GameClient;

import java.net.URI;
import java.net.URISyntaxException;

public class NetworkComponent extends Component {
    private GameClient client;
    private double lastSendTime = 0;
    // 每秒发送10次数据（100毫秒一次）
    private static final Duration SEND_INTERVAL = Duration.millis(100);

    @Override
    public void onAdded() {
        try {
            // 连接到我们之前启动的模拟服务器
            // 端口号变为 8080，并且加上了我们在 WebSocketConfig 中定义的路径 /game
            client = new GameClient(new URI("ws://localhost:8080/game"));
            client.connect(); // 尝试连接
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate(double tpf) {
        // tpf 是每帧的时间
        lastSendTime += tpf;
        
        // 检查是否到了发送数据的时间
        if (client != null && client.isOpen() && lastSendTime >= SEND_INTERVAL.toSeconds()) {
            // 重置计时器
            lastSendTime = 0;
            
            // 构造要发送的数据（这里用简单的字符串，未来可以用JSON）
            String message = String.format("POS,%.2f,%.2f,%.2f",
                    entity.getX(),
                    entity.getY(),
                    entity.getRotation()
            );

            // 发送消息
            client.send(message);
        }
    }

    @Override
    public void onRemoved() {
        // 当实体被移除时，关闭网络连接
        if (client != null) {
            client.close();
        }
    }
}