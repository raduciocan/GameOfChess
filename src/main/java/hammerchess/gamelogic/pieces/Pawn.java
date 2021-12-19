package hammerchess.gamelogic.pieces;

import hammerchess.gamelogic.board.ChessBoard;
import hammerchess.gamelogic.board.ChessBoardCell;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(boolean white) {
        super(white);
    }

    @Override
    public List<ChessBoardCell> getAvailableMoves(ChessBoard board, ChessBoardCell fromPos) {
        List<ChessBoardCell> possibleMoves = new ArrayList<>();
        //for white
        if(isWhite()) {
            if(board.getCell(fromPos.getX(), fromPos.getY() + 1).getPiece() instanceof Null) {
                //we can only go 1 space ahead by default
                possibleMoves.add(board.getCell(fromPos.getX(), fromPos.getY() + 1));
                //if it's the first white pawn move, we can advance 2 spaces
            }

            if(fromPos.getY() == 1 && board.getCell(fromPos.getX(), fromPos.getY() + 2).getPiece() instanceof Null)
                possibleMoves.add(board.getCell(fromPos.getX(), fromPos.getY() + 2));

            //if there are enemies in the upper corners, and we are not on the edge
            //we can eat one of them and move 1 up & 1 right/left
            if(fromPos.getX() != 0)
                if (!(board.getCell(fromPos.getX() - 1, fromPos.getY() + 1).getPiece() instanceof Null))
                    possibleMoves.add(board.getCell(fromPos.getX() - 1, fromPos.getY() + 1));
            if(fromPos.getX() != 7)
                if (!(board.getCell(fromPos.getX() + 1, fromPos.getY() + 1).getPiece() instanceof Null))
                    possibleMoves.add(board.getCell(fromPos.getX() + 1, fromPos.getY() + 1));

        }
        //for black
        else {
            if(board.getCell(fromPos.getX(), fromPos.getY() - 1).getPiece() instanceof Null) {
                //we can only go 1 space ahead by default
                possibleMoves.add(board.getCell(fromPos.getX(), fromPos.getY() - 1));
                //if it's the first black pawn move, we can advance 2 spaces
            }

            if(fromPos.getY() == 6 && board.getCell(fromPos.getX(), fromPos.getY() - 2).getPiece() instanceof Null)
                possibleMoves.add(board.getCell(fromPos.getX(), fromPos.getY() - 2));

            //if there are enemies in the upper corners, and we are not on the edge
            //we can eat one of them and move 1 down & 1 right/left
            if(fromPos.getX() != 0)
                if (!(board.getCell(fromPos.getX() - 1, fromPos.getY() - 1).getPiece() instanceof Null))
                    possibleMoves.add(board.getCell(fromPos.getX() - 1, fromPos.getY() - 1));
            if(fromPos.getX() != 7)
                if (!(board.getCell(fromPos.getX() + 1, fromPos.getY() - 1).getPiece() instanceof Null))
                    possibleMoves.add(board.getCell(fromPos.getX() + 1, fromPos.getY() - 1));

        }

        return possibleMoves;
    }

    @Override
    public boolean canMove(ChessBoard board, ChessBoardCell fromPos, ChessBoardCell toPos) {
        List<ChessBoardCell> possibleMoves = getAvailableMoves(board, fromPos);
        return possibleMoves.contains(toPos);
    }
}
