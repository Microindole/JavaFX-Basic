package org.csu.demo.basic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PropertyDeepDive {
    public static void main(String[] args) {
        // 1. 创建一个具体的 Property 实现
        StringProperty name1 = new SimpleStringProperty("Alice");
        StringProperty name2 = new SimpleStringProperty("Bob");

        // 2. 添加 InvalidationListener (来自 Observable)
        name1.addListener(observable -> {
            System.out.println("InvalidationListener: name1 可能变了。");
        });

        // 3. 添加 ChangeListener (来自 ObservableValue)
        name1.addListener((observable, oldValue, newValue) -> {
            System.out.println("ChangeListener: name1 从 '" + oldValue + "' 变为 '" + newValue + "'");
        });

        // 4. 修改值 (来自 WritableValue)
        System.out.println("--- 第一次修改值 ---");
        name1.set("Alicia");

        // 5. 进行绑定 (来自 Property)
        System.out.println("\n--- 绑定 name2 到 name1 ---");
        name2.bind(name1); // name2 的值现在由 name1 决定

        System.out.println("绑定后 name2 的值: " + name2.get());

        // 6. 修改被绑定的源头
        System.out.println("\n--- 第二次修改 name1 的值 ---");
        name1.set("Alexandra");
        System.out.println("修改后 name2 的值: " + name2.get()); // name2 自动更新

        // 7. 尝试修改被绑定的属性 (会抛出异常)
        try {
            name2.set("Charlie");
        } catch (RuntimeException e) {
            System.out.println("\n试图修改被绑定的属性失败: " + e.getMessage());
        }
    }
}