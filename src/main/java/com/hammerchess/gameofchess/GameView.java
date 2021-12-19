package com.hammerchess.gameofchess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameView extends Application {

    static GameController gameController;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 560, 600);
        gameController = (GameController)fxmlLoader.getController();
        stage.setTitle("GameMaster of Chess");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}