package hammerchess.gamelogic.pieces;

import hammerchess.gamelogic.board.ChessBoard;
import hammerchess.gamelogic.board.ChessBoardCell;

import java.util.List;

public abstract class Piece {
    private boolean alive;
    private final boolean white;

    public Piece(boolean white)
    {
        this.white = white;
        alive = true;
    }

    public boolean isWhite()
    {
        return this.white;
    }

    public boolean isAlive()
    {
        return this.alive;
    }

    public void kill()
    {
        this.alive = false;
    }
    public void resurrect()
    {
        this.alive = true;
    }

    public abstract List<ChessBoardCell> getAvailableMoves(ChessBoard board, ChessBoardCell fromPos);
    public abstract boolean canMove(ChessBoard board, ChessBoardCell fromPos, ChessBoardCell toPos);
}