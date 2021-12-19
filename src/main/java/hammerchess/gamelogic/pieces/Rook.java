package hammerchess.gamelogic.pieces;

import hammerchess.gamelogic.board.ChessBoard;
import hammerchess.gamelogic.board.ChessBoardCell;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private boolean isPromoted = false;

    public Rook(boolean white) {
        super(white);
    }
    public Rook(boolean white, boolean isPromoted) {
        super(white);
        this.isPromoted = isPromoted;
    }

    public boolean isPromoted() {
        return isPromoted;
    }

    @Override
    public List<ChessBoardCell> getAvailableMoves(ChessBoard board, ChessBoardCell fromPos) {
        List<ChessBoardCell> possibleMoves = new ArrayList<>();
        for(int Y = fromPos.getY() + 1; Y < 8; Y++) {
            possibleMoves.add(board.getCell(fromPos.getX(), Y));
            if(!(board.getCell(fromPos.getX(), Y).getPiece() instanceof Null))
                break;
        }
        for(int Y = fromPos.getY() - 1; Y > 0; Y--) {
            possibleMoves.add(board.getCell(fromPos.getX(), Y));
            if(!(board.getCell(fromPos.getX(), Y).getPiece() instanceof Null))
                break;
        }
        for(int X = fromPos.getX() + 1; X < 8; X++) {
            possibleMoves.add(board.getCell(X, fromPos.getY()));
            if(!(board.getCell(X, fromPos.getY()).getPiece() instanceof Null))
                break;
        }
        for(int X = fromPos.getX() - 1; X > 0; X--) {
            possibleMoves.add(board.getCell(X, fromPos.getY()));
            if(!(board.getCell(X,fromPos.getY()).getPiece() instanceof Null))
                break;
        }

        return possibleMoves;
    }

    @Override
    public boolean canMove(ChessBoard board, ChessBoardCell fromPos, ChessBoardCell toPos) {
        List<ChessBoardCell> possibleMoves = getAvailableMoves(board, fromPos);
        return possibleMoves.contains(toPos);
    }
}
