package hammerchess.gamelogic;

public enum GameState {
    ACTIVE(true),
    CHECK(true),
    CHECKMATE_BLACK(false),
    CHECKMATE_WHITE(false),
    FORFEIT(false),
    STALEMATE(false);

    public final boolean running;
    GameState(boolean running) {
        this.running = running;
    }

}
