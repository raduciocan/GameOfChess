package com.hammerchess.gameofchess;

import hammerchess.gamelogic.GameState;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class WinScreenController {
    private GameState state;
    private boolean winnerIsWhite;
    private double xOffset, yOffset;
    @FXML
    private AnchorPane winContainer;
    @FXML
    private Label winnerLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private Tooltip dragTip;
    @FXML
    private Button exitButton;
    @FXML
    private Button restartButton;
    @FXML
    protected void exit(){
        Platform.exit();
        System.exit(0);
    }
    @FXML
    protected void restart(){
        GameView.gameController.restartGame();
        GameView.gameController.showGame();
        winContainer.getScene().getWindow().hide();
    }

    private void setDragControls() {
        dragTip.setShowDelay(Duration.ZERO);
        winContainer.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        winContainer.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                winContainer.getScene().getWindow().setX(event.getScreenX() - xOffset);
                winContainer.getScene().getWindow().setY(event.getScreenY() - yOffset);
            }
        });
    }
    public void setData(GameState state, boolean winnerIsWhite) {
        this.state = state;
        this.winnerIsWhite = (state != GameState.FORFEIT) == winnerIsWhite;
        updateWinnerInfo();
        setDragControls();
    }

    private void updateWinnerInfo() {
        if(winnerIsWhite){
            winnerLabel.setText("WHITE");
            winnerLabel.setTextFill(Color.CYAN);
        }
        else {
            winnerLabel.setText("BLACK");
            winnerLabel.setTextFill(Color.ORANGE);
        }

        switch (state) {
            case CHECKMATE_WHITE:
            case CHECKMATE_BLACK:
                infoLabel.setText("Win by checkmate!");
                break;
            case FORFEIT:
                String loser = winnerIsWhite? "BLACK" : "WHITE";
                infoLabel.setText("because " + loser + " has given up!");
                break;
        }
    }

}
