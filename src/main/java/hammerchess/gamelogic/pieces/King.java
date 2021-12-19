package hammerchess.gamelogic.pieces;

import hammerchess.gamelogic.board.ChessBoard;
import hammerchess.gamelogic.board.ChessBoardCell;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private boolean castlingable = true;

    public King(boolean white) {
        super(white);
    }

    public void setCastling(boolean castlingDone) {
        this.castlingable = castlingDone;
    }
    public boolean isCastlingPossible() {
        return castlingable;
    }

    public boolean canSmallCast(ChessBoard board, ChessBoardCell fromPos) {
        //refactoring needed
        if(castlingable && fromPos.getX() == 4 && (fromPos.getY() == 0 || fromPos.getY() == 7))
            return board.getCell(5, fromPos.getY()).getPiece() instanceof Null && board.getCell(6, fromPos.getY()).getPiece() instanceof Null && board.getCell(7, fromPos.getY()).getPiece() instanceof Rook;
        return false;
    }
    public boolean canBigCast(ChessBoard board, ChessBoardCell fromPos) {
        //refactoring needed
        if(castlingable && fromPos.getX() == 4 && (fromPos.getY() == 0 || fromPos.getY() == 7))
            return board.getCell(3, fromPos.getY()).getPiece() instanceof Null && board.getCell(2, fromPos.getY()).getPiece() instanceof Null && board.getCell(1, fromPos.getY()).getPiece() instanceof Null && board.getCell(0, fromPos.getY()).getPiece() instanceof Rook;
        return false;
    }

    @Override
    public List<ChessBoardCell> getAvailableMoves(ChessBoard board, ChessBoardCell fromPos) {
        List<ChessBoardCell> possibleMoves = new ArrayList<>();
        int[][] combinations = {
                {-1, 1},
                {0, 1},
                {1, 1},
                {-1, 0},
                {1, 0},
                {-1, -1},
                {0, -1},
                {1, -1}
        };
        for(int i = 0; i < 8; i++)
            if(board.isValid(fromPos.getX() + combinations[i][0], fromPos.getY() + combinations[i][1]))
                possibleMoves.add(board.getCell(fromPos.getX() + combinations[i][0], fromPos.getY() + combinations[i][1]));

        if (canSmallCast(board, fromPos))
            possibleMoves.add(board.getCell(1, 0));
        if (canBigCast(board, fromPos))
            possibleMoves.add(board.getCell(6, 0));

        return possibleMoves;
    }

    @Override
    public boolean canMove(ChessBoard board, ChessBoardCell fromPos, ChessBoardCell toPos) {
        List<ChessBoardCell> possibleMoves = getAvailableMoves(board, fromPos);
        castlingable = false;
        return possibleMoves.contains(toPos);
    }
}
