package com.hammerchess.gameofchess;

import hammerchess.gamelogic.GameMaster;
import hammerchess.gamelogic.GameState;
import hammerchess.gamelogic.PlayerColor;
import hammerchess.gamelogic.board.ChessBoard;
import hammerchess.gamelogic.history.History;
import hammerchess.gamelogic.pieces.ChessPiecesUnicodeAdapter;
import hammerchess.gamelogic.pieces.Null;
import hammerchess.gamelogic.pieces.Piece;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;


public class GameController {
    GameMaster game;
    History history;
    ChessBoard chessBoard;
    UserSelection selection;
    ChessBoardButton[][] tiles;
    PlayerColor currentPlayer;


    private Tooltip piecesToolTip;
    @FXML
    private TilePane boardView;
    @FXML
    private VBox whiteGraveyard;
    @FXML
    private VBox blackGraveyard;
    @FXML
    private Label currentPlayerLabel;
    @FXML
    private Separator separatorBlack;
    @FXML
    private Separator separatorWhite;
    @FXML
    private ProgressIndicator indicatorWhite;
    @FXML
    private ProgressIndicator indicatorBlack;
    @FXML
    private Label infoLabel;
    @FXML
    private Button undoButton;
    @FXML
    private Button redoButton;


    @FXML
    protected void onGiveUpButtonClick() {
        game.setGameState(GameState.FORFEIT);
        indicatorWhite.setVisible(false);
        indicatorBlack.setVisible(false);
        boardView.getScene().getWindow().hide();
        openWinView();
    }
    @FXML
    protected void onUndoButtonClick() {
        if(game.undoMove()) {
            //update visual components
            updateUndoRedoButtons();
            updateWholeBoard();
            updateWhiteGraveyard();
            updateBlackGraveyard();
            updateInfoLabel("Last move has been undone!", Color.NAVY);
            //if move undone, player turn flips
            currentPlayer = game.flipCurrentPlayer();
            updateCurrentPlayerLabel();
        }
    }
    @FXML
    protected void onRedoButtonClick() {
        if (game.redoMove()) {
            //update visual components
            updateUndoRedoButtons();
            updateWholeBoard();
            updateWhiteGraveyard();
            updateBlackGraveyard();
            updateInfoLabel("Move has been redone!", Color.BLUE);
            //if move undone, player turn flips
            currentPlayer = game.flipCurrentPlayer();
            updateCurrentPlayerLabel();
        }
    }

    public void showGame(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("game-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 560, 600);
            Stage stage = new Stage();
            stage.setTitle("GameMaster of Chess");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        initializeBoard();
        selection = new UserSelection();
    }

    public void initializeBoard() {
        chessBoard = new ChessBoard();
        history = new History();
        tiles = new ChessBoardButton[8][8];
        game = new GameMaster(chessBoard, history);
        currentPlayer = game.getCurrentPlayer();

        piecesToolTip = new Tooltip();
        piecesToolTip.setShowDelay(Duration.ONE);
        //creating the board starting with a black tile
        boolean isTileWhite = false;
        //dynamically creating tiles (as buttons) for the game board
        for (int y = 7; y >= 0; y--) {
            for (int x = 7; x >= 0; x--) {
                String buttonText = chessBoard.getCell(x, y).getPieceSymbol();
                ChessBoardButton button = new ChessBoardButton(buttonText, x, y, isTileWhite);
                button.setMaxWidth(50);
                button.setMinWidth(50);
                button.setPrefWidth(50);
                button.setMaxHeight(50);
                button.setMinHeight(50);
                button.setPrefHeight(50);
                button.setTooltip(piecesToolTip);

                //selection event handler
                button.setOnMousePressed(e -> {
                    System.out.println("X: " + button.getX() + ", Y: " + button.getY() + ", tile is white: " + button.isWhite());
                    select(button.getX(), button.getY());
                });

                //colorize text depending on piece color
                if(chessBoard.getCell(x, y).getPiece().isWhite())
                    button.setTextFill(Color.AQUA);
                else
                    button.setTextFill(Color.ORANGE);

                //colorize tiles depending on color
                setTileStyle(button, isTileWhite);

                tiles[y][x] = button;
                boardView.getChildren().add(button);
                //flip color after every tile
                isTileWhite = !isTileWhite;
            }
            //flip color again after one row to obtain the zig-zag effect
            isTileWhite = !isTileWhite;
        }
    }

    private void setTileStyle(ChessBoardButton button, boolean isWhite) {
        if(isWhite) {
            button.setStyle(GameTileStyles.defaultWhiteTileStyle);
            //mouse hover event handlers
            button.setOnMouseEntered(e -> {
                button.setStyle(GameTileStyles.hoverWhiteTileStyle);
                //update tooltip on mouse hover ith corresponding piece
                updateToolTip(button);
            });
            button.setOnMouseExited(e -> {
                button.setStyle(GameTileStyles.defaultWhiteTileStyle);
            });
        }
        else {
            button.setStyle(GameTileStyles.defaultBlackTileStyle);
            //mouse hover event handlers
            button.setOnMouseEntered(e -> {
                button.setStyle(GameTileStyles.hoverBlackTileStyle);
                //update tooltip on mouse hover ith corresponding piece
                updateToolTip(button);
            });
            button.setOnMouseExited(e -> {
                button.setStyle(GameTileStyles.defaultBlackTileStyle);
            });
        }
    }

    //selector method used by the game button tiles' click handler
    public void select(int x, int y){
        //if nothing selected:
        if(selection.isEmpty()){
            if(chessBoard.getCell(x, y).getPiece() instanceof Null) {
                updateInfoLabel("Can't select empty tile!", Color.CRIMSON);
                return;
            }
            if(chessBoard.getCell(x, y).getPiece().isWhite() == currentPlayer.isWhite) {

                //UserSelection of first position here:
                selection.setFrom(x, y);

                Pair<String, PlayerColor> conversion = ChessPiecesUnicodeAdapter.reverseCharCode(tiles[y][x].getText().charAt(0));
                String pieceString = conversion.getKey().equals("Null") ? "empty cell" : conversion.getValue().toString().toLowerCase() + " " + conversion.getKey();
                pieceString += (" from line " + y + ", row " + x + " !");
                updateInfoLabel("You selected the " + pieceString, Color.LIGHTSEAGREEN);
                return;
            }
            updateInfoLabel("Selected piece is not your piece!", Color.CRIMSON);
            return;
        }
        //if only first piece was selected
        if(!selection.isComplete()){
            if(x == selection.getFromX() && y == selection.getFromY()) {
                updateInfoLabel("Piece deselected", Color.LIGHTSEAGREEN);
                selection.reset();
                return;
            }
            //UserSelection of second position here:
            selection.setTo(x, y);

            System.out.println("Selected second piece:" + selection.getToX() + ", " + selection.getToY());
        }

        //if both selected cells are valid, we try to perform the move
        if(game.tryMove(selection, currentPlayer.isWhite)) {
            //update current player
            currentPlayer = currentPlayer.switchPlayer();
            updateInfoLabel("Valid move executed!", Color.LIGHTSEAGREEN);
            updateCurrentPlayerLabel();
            updateUndoRedoButtons();

            //if move was committed, reset the history redo list since the current game state is no longer matching old next moves
            history.clearRedo();

            //if last move a piece has been killed, we add it to its respective VBox
            if(game.lastKilled() == 1) {
                updateInfoLabel("Valid move executed : White piece captured!", Color.LIGHTSEAGREEN);
                updateWhiteGraveyard();
            }
            if(game.lastKilled() == 2) {
                updateInfoLabel("Valid move executed : Black piece captured!", Color.LIGHTSEAGREEN);
                updateBlackGraveyard();
            }
        }
        else {
            updateInfoLabel("That move is illegal!", Color.CRIMSON);
        }
        //update button tiles from selected locations
        updateGameTilePair(selection);
        //reset redo button, in case redo no longer available
        updateUndoRedoButtons();
        //once move has been committed, we clear the selection
        selection.reset();
    }

    //will use to highlight and revert selected button tile
    private void selectTile(int x, int y) {
        tiles[y][x].setSelected(true);
    }
    private void resetTile(int x, int y) {
        tiles[y][x].setSelected(false);
    }

    //update undo and redo buttons aspect (disabled or not)
    private void updateUndoRedoButtons() {
        undoButton.setDisable(!history.canUndo());
        redoButton.setDisable(!history.canRedo());
    }

    //update info label under headline ith selection information
    private void updateInfoLabel(String text, Color color){
        infoLabel.setText(text);
        infoLabel.setTextFill(color);
    }

    //update game tiles tooltip to display hovered button piece
    private void updateToolTip(ChessBoardButton button) {
        Pair<String, PlayerColor> conversion = ChessPiecesUnicodeAdapter.reverseCharCode(button.getText().charAt(0));
        if(conversion.getKey().equals("Null")) {
            piecesToolTip.setText("empty");
        }
        else {
            piecesToolTip.setText(conversion.getValue().toString().toLowerCase() + " " + conversion.getKey());
        }
    }

    //update VBox containers to display pieces captured
    private void updateWhiteGraveyard() {
        whiteGraveyard.getChildren().clear();
        for(Piece piece : chessBoard.getWhiteDead()){
            Label pieceLabel = new Label();
            pieceLabel.setTextFill(Color.AQUA);
            pieceLabel.setEffect(new DropShadow(12.0, Color.BLACK));
            pieceLabel.setFont(Font.font("Arial", 20));
            pieceLabel.setText(ChessPiecesUnicodeAdapter.getPieceChar(piece).toString());
            whiteGraveyard.getChildren().add(pieceLabel);
        }
    }
    private void updateBlackGraveyard() {
        blackGraveyard.getChildren().clear();
        for(Piece piece : chessBoard.getBlackDead()){
            Label pieceLabel = new Label();
            pieceLabel.setTextFill(Color.ORANGE);
            pieceLabel.setEffect(new DropShadow(12.0, Color.BLACK));
            pieceLabel.setFont(Font.font("Arial", 20));
            pieceLabel.setText(ChessPiecesUnicodeAdapter.getPieceChar(piece).toString());
            blackGraveyard.getChildren().add(pieceLabel);
        }
    }

    //update headline label to display the current player that can move
    private void updateCurrentPlayerLabel() {
        if(currentPlayer.isWhite){
            currentPlayerLabel.setText("WHITE");
            currentPlayerLabel.setTextFill(Color.WHITE);
            separatorBlack.setStyle(null);
            separatorWhite.setStyle("-fx-background-color: AQUA;");
            indicatorWhite.setVisible(true);
            indicatorBlack.setVisible(false);
        }
        else {
            currentPlayerLabel.setText("BLACK");
            currentPlayerLabel.setTextFill(Color.BLACK);
            separatorBlack.setStyle("-fx-background-color: ORANGE;");
            separatorWhite.setStyle(null);
            indicatorWhite.setVisible(false);
            indicatorBlack.setVisible(true);
        }
    }

    //update aspect of the tiles contained by the move
    private void updateGameTilePair(UserSelection selection) {
        System.out.println("UPDATING TILES");
        String buttonText;
        int fromX = selection.getFromX();
        int fromY = selection.getFromY();
        int toX = selection.getToX();
        int toY = selection.getToY();

        //update text for source tile
        buttonText = chessBoard.getCell(fromX, fromY).getPieceSymbol();
        System.out.println(buttonText);
        tiles[fromY][fromX].setText(buttonText);

        //update text for dest tile
        buttonText = chessBoard.getCell(toX, toY).getPieceSymbol();
        System.out.println(buttonText);
        tiles[toY][toX].setText(buttonText);

        //update text color for source tile
        if(chessBoard.getCell(fromX, fromY).getPiece().isWhite())
            tiles[fromY][fromX].setTextFill(Color.AQUA);
        else
            tiles[fromY][fromX].setTextFill(Color.ORANGE);

        //update text color for dest tile
        if(chessBoard.getCell(toX, toY).getPiece().isWhite())
            tiles[toY][toX].setTextFill(Color.AQUA);
        else
            tiles[toY][toX].setTextFill(Color.ORANGE);
    }

    //reset aspect of the whole chessboard
    private void updateWholeBoard() {
        boolean isWhite = false;
        for (int y = 7; y >= 0; y--) {
            for (int x = 7; x >= 0; x--) {
                String buttonText = chessBoard.getCell(x, y).getPieceSymbol();
                tiles[y][x].setText(buttonText);

                if(chessBoard.getCell(x, y).getPiece().isWhite())
                    tiles[y][x].setTextFill(Color.AQUA);
                else
                    tiles[y][x].setTextFill(Color.ORANGE);

            }
        }
    }

    //display victory window
    private void openWinView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("win-screen.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.initOwner((Stage)boardView.getScene().getWindow());
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            WinScreenController controller = loader.getController();
            controller.setData(game.getGameState(), currentPlayer.isWhite);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //reinitialization of game logic and visual components reset
    public void restartGame() {
        game.restart();
        selection.reset();
        currentPlayer = game.getCurrentPlayer();
        updateWholeBoard();
        updateWhiteGraveyard();
        updateBlackGraveyard();
        updateCurrentPlayerLabel();
    }

}
