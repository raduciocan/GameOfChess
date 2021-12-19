package hammerchess.gamelogic.board;

import hammerchess.gamelogic.pieces.ChessPiecesUnicodeAdapter;
import hammerchess.gamelogic.pieces.Piece;

public class ChessBoardCell {
    private final int x, y;
    private Piece piece;

    public ChessBoardCell(int x, int y, Piece piece) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getPieceSymbol() {
        return ChessPiecesUnicodeAdapter.getPieceChar(piece).toString();
    }

    public void testPrint() {
        System.out.println("\tx:" + x + "\ty:" + y + "\t"+piece.toString() );
    }
}