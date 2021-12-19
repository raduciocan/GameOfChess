package hammerchess.gamelogic.pieces;

import hammerchess.gamelogic.PlayerColor;
import javafx.util.Pair;

import java.util.Map;
import java.util.Objects;

public class ChessPiecesUnicodeAdapter {
    private static final Map<String, Character> pieceCharTable_White = Map.of(
            "Pawn" , '\u2659',
            "Rook", '\u2656',
            "Bishop", '\u2657',
            "Knight", '\u2658',
            "Queen", '\u2655',
            "King", '\u2654',
            "Null", '\u0020'
    );
    private static final Map<String, Character> pieceCharTable_Black = Map.of(
            "Pawn" , '\u265F',
            "Rook", '\u265C',
            "Bishop", '\u265D',
            "Knight", '\u265E',
            "Queen", '\u265B',
            "King", '\u265A',
            "Null", '\u0020'
    );
    public static Character getPieceChar(Piece piece){
        String key = piece.getClass().getSimpleName();
        if(piece.isWhite())
            return pieceCharTable_White.get(key);
        return pieceCharTable_Black.get(key);
    }

    public static Pair<String, PlayerColor> reverseCharCode(Character code) {
        for(Map.Entry<String, Character> entry : pieceCharTable_White.entrySet())
            if(Objects.equals(code, entry.getValue()))
                return new Pair<String, PlayerColor>(entry.getKey(), PlayerColor.WHITE);
        for(Map.Entry<String, Character> entry : pieceCharTable_Black.entrySet())
            if(Objects.equals(code, entry.getValue()))
                return new Pair<String, PlayerColor>(entry.getKey(), PlayerColor.BLACK);
        return new Pair<String, PlayerColor>("", null);
    }
}
