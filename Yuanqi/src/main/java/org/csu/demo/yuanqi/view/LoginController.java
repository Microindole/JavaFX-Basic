package org.csu.demo.yuanqi.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.csu.demo.yuanqi.network.NetworkManager;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    // 【新增】一个Runnable，用于在登录成功后执行回调操作
    private Runnable onLoginSuccessListener;

    // 【新增】一个公共方法，让外部可以设置这个回调
    public void setOnLoginSuccessListener(Runnable action) {
        this.onLoginSuccessListener = action;
    }

    @FXML
    private void onLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        new Thread(() -> {
            try {
                String response = NetworkManager.getInstance().login(username, password);
                Platform.runLater(() -> {
                    if ("Login successful!".equals(response)) {
                        showAlert(Alert.AlertType.INFORMATION, "成功", "登录成功！");
                        // 【修改】登录成功后，执行回调
                        if (onLoginSuccessListener != null) {
                            onLoginSuccessListener.run();
                        }
                    } else {
                        showAlert(Alert.AlertType.ERROR, "登录失败", response);
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "连接错误", "无法连接到服务器。"));
                e.printStackTrace();
            }
        }).start();
    }

    // onRegister 和 showAlert 方法保持不变...
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}