package com.toks.laba1;

import com.toks.laba1.codding.HammingCoder;
import com.toks.laba1.initializer.SceneInitializer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;

public class Main extends Application {

    private static String stageTitle;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String serialPortNumber = getParameters().getRaw().get(0);
        stageTitle = "COM"+serialPortNumber;
        SceneInitializer initializer = new SceneInitializer();
        initializer.initScene(primaryStage);
    }

    public static String getStageTitle() {
        return stageTitle;
    }
}
