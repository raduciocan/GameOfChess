package hammerchess.gamelogic.pieces;

import hammerchess.gamelogic.board.ChessBoard;
import hammerchess.gamelogic.board.ChessBoardCell;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    private boolean isPromoted = false;

    public Knight(boolean white) {
        super(white);
    }
    public Knight(boolean white, boolean isPromoted) {
        super(white);
        this.isPromoted = isPromoted;
    }

    public void setPromoted(boolean isPromoted) {
        this.isPromoted = isPromoted;
    }
    public boolean isPromoted() {
        return isPromoted;
    }

    @Override
    public List<ChessBoardCell> getAvailableMoves(ChessBoard board, ChessBoardCell fromPos) {
        List<ChessBoardCell> possibleMoves = new ArrayList<ChessBoardCell>();
        int[][] combinations = {
                {1, 2},
                {1, -2},
                {-1, 2},
                {-1, -2},
                {2, 1},
                {2, -1},
                {-2, 1},
                {-2, -1}
        };
        for(int i = 0; i < 8; i++)
            if(board.isValid(fromPos.getX() + combinations[i][0], fromPos.getY() + combinations[i][1]))
            possibleMoves.add(board.getCell(fromPos.getX() + combinations[i][0], fromPos.getY() + combinations[i][1]));

        return possibleMoves;
    }

    @Override
    public boolean canMove(ChessBoard board, ChessBoardCell fromPos, ChessBoardCell toPos) {
        //very cool math trick to check if move is valid
        int x = Math.abs(fromPos.getX() - toPos.getX());
        int y = Math.abs(fromPos.getY() - toPos.getY());
        return x * y == 2;
    }
}
