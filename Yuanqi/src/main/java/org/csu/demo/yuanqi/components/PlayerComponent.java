package org.csu.demo.yuanqi.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class PlayerComponent extends Component {

    private double speed = 300;


    public void moveLeft() {
        entity.translateX(-speed * FXGL.tpf());
    }
    public void moveRight() {
        entity.translateX(speed * FXGL.tpf());
    }
    public void moveUp() {
        entity.translateY(-speed * FXGL.tpf());
    }
    public void moveDown() {
        entity.translateY(speed * FXGL.tpf());
    }

    //【新增】Method to rotate the player towards the mouse
    public void rotateToMouse() {
        Point2D mousePos = FXGL.getInput().getMousePositionWorld();
        entity.rotateToVector(mousePos.subtract(entity.getCenter()));
    }

    // 在 PlayerComponent.java 文件中

    public void shoot() {
        Point2D center = entity.getCenter();

        // 【修改】下面是正确的代码
        // 1. 获取实体当前的角度 (单位: 度)
        double rotationAngleDegrees = entity.getRotation();

        // 2. 将角度转换为弧度
        double rotationAngleRadians = Math.toRadians(rotationAngleDegrees);

        // 3. 使用三角函数计算出方向向量
        Point2D direction = new Point2D(Math.cos(rotationAngleRadians), Math.sin(rotationAngleRadians));

        FXGL.spawn("bullet", new SpawnData(center).put("direction", direction));
    }
}