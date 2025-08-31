package org.csu.demo.yuanqi.dto;

import lombok.Data;

@Data
public class UserCommand {
    private String playerId;
    private CommandType type;
    private long timestamp;
    private double x;
    private double y;
    private double rotation;

    public enum CommandType {
        UPDATE_STATE // 一个统一的状态更新指令
    }
}