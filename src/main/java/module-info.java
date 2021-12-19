module com.hammerchess.gameofchess {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.hammerchess.gameofchess to javafx.fxml;
    exports com.hammerchess.gameofchess;
    exports hammerchess.gamelogic.pieces;
    opens hammerchess.gamelogic.pieces to javafx.fxml;
    exports hammerchess.gamelogic;
    opens hammerchess.gamelogic to javafx.fxml;
}