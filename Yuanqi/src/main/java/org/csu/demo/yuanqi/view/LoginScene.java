package org.csu.demo.yuanqi.view; // Or your scene package

import com.almasb.fxgl.app.scene.FXGLScene;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.Parent;
import org.csu.demo.yuanqi.controller.LoginController; // Ensure your controller's package is correct

public class LoginScene extends FXGLScene {

    private final LoginController controller;

    public LoginScene() {
        // 【CORRECTED】 FXGL's AssetLoader directly loads the FXML and returns the Parent node.
        // The controller is created and can be retrieved from the loader service *after* loading.
        Parent root = FXGL.getAssetLoader().loadFXML("login.fxml");

        // 【CORRECTED】 After loading, the controller instance is available via the asset loader.
        this.controller = FXGL.getAssetLoader().getController("login.fxml");

        // Add the loaded FXML content to the scene
        getContentRoot().getChildren().add(root);
    }

    // This is your own public method to access the controller
    public LoginController getController() {
        return controller;
    }
}