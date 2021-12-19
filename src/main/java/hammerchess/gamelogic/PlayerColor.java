package hammerchess.gamelogic;

public enum PlayerColor {
    WHITE(true),
    BLACK(false);

    public final boolean isWhite;

    PlayerColor(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public PlayerColor switchPlayer() {
        return this == WHITE ? BLACK : WHITE;
    }
}
