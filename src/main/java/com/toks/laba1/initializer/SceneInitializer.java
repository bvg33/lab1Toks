package com.toks.laba1.initializer;

import com.toks.laba1.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneInitializer {

    public void initScene(Stage primaryStage) throws IOException {
        URL url = getClass().getResource("/chatScene.fxml");
        Parent root = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.setTitle(Main.getStageTitle());
        primaryStage.show();
    }
}
