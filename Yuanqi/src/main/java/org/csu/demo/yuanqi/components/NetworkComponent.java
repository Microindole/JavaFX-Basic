package org.csu.demo.yuanqi.components;

import com.almasb.fxgl.entity.component.Component;
import com.google.gson.Gson;
import javafx.util.Duration;
import org.csu.demo.yuanqi.dto.UserCommand;
import org.csu.demo.yuanqi.network.GameClient;

import java.net.URI;
import java.net.URISyntaxException;

public class NetworkComponent extends Component {
    private GameClient client;
    private static final Duration SEND_INTERVAL = Duration.millis(1000 / 20); // 每秒发送20次
    private double timeSinceLastSend = 0;
    private Gson gson = new Gson();

    public String getClientId() {
        return (client != null) ? client.getMyId() : null;
    }

    @Override
    public void onAdded() {
        try {
            URI serverUri = new URI("ws://localhost:8080/game");
            client = new GameClient(serverUri);
            client.connectBlocking(); // 使用阻塞连接确保连接成功
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate(double tpf) {
        timeSinceLastSend += tpf;
        if (client != null && client.isOpen() && timeSinceLastSend >= SEND_INTERVAL.toSeconds()) {
            timeSinceLastSend = 0;

            UserCommand cmd = new UserCommand();
            cmd.setType(UserCommand.CommandType.UPDATE_STATE);
            cmd.setTimestamp(System.currentTimeMillis());
            cmd.setX(entity.getX());
            cmd.setY(entity.getY());
            cmd.setRotation(entity.getRotation());

            client.send(gson.toJson(cmd));
        }
    }

    @Override
    public void onRemoved() {
        if (client != null) {
            client.close();
        }
    }

    // 增加一个方法，让外部可以设置消息处理器
    public void setSnapshotConsumer(java.util.function.Consumer<org.csu.demo.yuanqi.dto.GameStateSnapshot> consumer) {
        if (client != null) {
            client.setSnapshotConsumer(consumer);
        }
    }
}