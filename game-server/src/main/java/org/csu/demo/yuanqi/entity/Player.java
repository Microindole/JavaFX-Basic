package org.csu.demo.yuanqi.entity;// in package ...model.entity


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data // Lombok annotation
@TableName("player") // Maps this class to the 'player' table
public class Player {

    @TableId(type = IdType.AUTO) // Marks this as the primary key with auto-increment
    private Long id;

    private String username;
    private String password; // In a real app, this should be hashed!
}