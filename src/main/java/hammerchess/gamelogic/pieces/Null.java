package hammerchess.gamelogic.pieces;

import hammerchess.gamelogic.board.ChessBoard;
import hammerchess.gamelogic.board.ChessBoardCell;

import java.util.List;

public class Null extends Piece{
    public Null(boolean white) {
        super(false);
    }

    @Override
    public List<ChessBoardCell> getAvailableMoves(ChessBoard board, ChessBoardCell fromPos) {
        return null;
    }

    @Override
    public boolean canMove(ChessBoard board, ChessBoardCell fromPos, ChessBoardCell toPos) {
        return false;
    }
}
