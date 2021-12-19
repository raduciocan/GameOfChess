package hammerchess.gamelogic.history;

import hammerchess.gamelogic.board.ChessBoardCell;
import hammerchess.gamelogic.pieces.Null;
import hammerchess.gamelogic.pieces.Piece;

public class Move {
    private boolean white;
    private ChessBoardCell startPos;
    private ChessBoardCell endPos;
    private Piece pieceMoved;
    private Piece pieceKilled;
    MoveType type;

    public Move(boolean white, ChessBoardCell startPos, ChessBoardCell endPos, MoveType type) {
        this.white = white;
        this.startPos = startPos;
        this.endPos = endPos;
        this.pieceMoved = startPos.getPiece();
        this.pieceKilled = endPos.getPiece();
        this.type = type;
    }


    public Piece getPieceMoved() {
        return pieceMoved;
    }
    public Piece getPieceKilled() {
        return pieceKilled;
    }
    public ChessBoardCell getStartPos() {
        return startPos;
    }
    public ChessBoardCell getEndPos() {
        return endPos;
    }
    public boolean isWhite() {
        return white;
    }
    public boolean hasKilled(){
        return !(pieceKilled instanceof Null);
    }
    public boolean isCastlingMove() {
        return this.type == MoveType.CASTLING_BIG || this.type == MoveType.CASTLING_SMALL;
    }
    public boolean isPromotionMove()
    {
        return this.type == MoveType.PROMOTION;
    }
    public MoveType getMoveType() {
        return type;
    }
    public void setMoveType(MoveType type){
        this.type = type;
    }
}
