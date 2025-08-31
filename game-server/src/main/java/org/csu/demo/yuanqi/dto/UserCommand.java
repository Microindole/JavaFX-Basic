package org.csu.demo.yuanqi.dto;

import lombok.Data;

@Data
public class UserCommand {
    private String playerId;
    private CommandType type; // e.g., MOVE, SHOOT
    private long timestamp; // 客户端发送指令时的精确时间 (System.currentTimeMillis())
    private float moveDirectionX; // 移动方向
    private float moveDirectionY;


    public enum CommandType {
        MOVE, SHOOT
    }

    // 省略构造函数、getter和setter
    // 您可以使用 Lombok 的 @Data 注解来简化
}