package hammerchess.gamelogic;

import com.hammerchess.gameofchess.UserSelection;
import hammerchess.gamelogic.board.ChessBoard;
import hammerchess.gamelogic.board.ChessBoardCell;
import hammerchess.gamelogic.history.History;
import hammerchess.gamelogic.history.Move;
import hammerchess.gamelogic.history.MoveType;
import hammerchess.gamelogic.pieces.King;
import hammerchess.gamelogic.pieces.Null;
import hammerchess.gamelogic.pieces.Pawn;
import hammerchess.gamelogic.pieces.Piece;

public class GameMaster {
    private ChessBoard board;
    private PlayerColor currentPlayer;
    private History history;
    private GameState state;

    public GameMaster(ChessBoard board, History history) {
        this.board = board;
        this.history = history;
        initiate();
    }

    private void initiate() {
        state = GameState.ACTIVE;
        currentPlayer = PlayerColor.WHITE;
        board.initiate();
    }


    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }
    public PlayerColor flipCurrentPlayer() {
        currentPlayer = currentPlayer.switchPlayer();
        return currentPlayer;
    }
    public GameState getGameState() {
        return this.state;
    }
    public void setGameState(GameState state) {
        this.state = state;
    }

    public boolean tryMove(UserSelection selection, boolean isWhite) {
        MoveType moveType = MoveType.MOVE;
        int startX = selection.getFromX();
        int startY = selection.getFromY();
        int endX = selection.getToX();
        int endY = selection.getToY();
        System.out.println("UserSelection -to- Y: " + selection.getToY());

        if (!board.isValid(startX, startY) || !board.isValid(endX, endY)) {
            System.out.println("INVALID MOVE ERROR:\tPosition is not on the table");
            return false;
        }
        ChessBoardCell startPos = board.getCell(startX, startY);
        ChessBoardCell endPos = board.getCell(endX, endY);

        Piece sourcePiece = startPos.getPiece();
        Piece destPiece = endPos.getPiece();

        if (sourcePiece instanceof Null) {
            System.out.println("INVALID MOVE ERROR:\tSource is empty");
        return false;
        }

        if(sourcePiece.isWhite() != currentPlayer.isWhite) {
            System.out.println("INVALID MOVE ERROR:\tPiece moved is not your piece!");
            return false;
        }

        if(!(destPiece instanceof Null) && destPiece.isWhite() == currentPlayer.isWhite) {
            System.out.println("INVALID MOVE ERROR:\tCan't eat your own piece");
            return false;
        }

        if(!sourcePiece.canMove(board, startPos, endPos)) {
            System.out.println("INVALID MOVE ERROR:\tMove is not valid!");
            return false;
        }

        //kill verification
        if(!(destPiece instanceof Null) && !(destPiece instanceof King)) {
            moveType = MoveType.CAPTURE;
            System.out.println("\tDEBUG:\tDestination piece has been killed");
            //we set killed property of the destination piece to true
            board.getCell(endX, endY).getPiece().kill();

            if(destPiece.isWhite()) {
                //add killed piece to dead white pieces
                board.addWhiteDead(destPiece);
            }
            else {
                //add killed piece to dead white pieces
                board.addBlackDead(destPiece);
            }
        }

        //check to see if move is of special type
        if(sourcePiece instanceof Pawn && (endY == 0 || endY == 7))
            moveType = MoveType.PROMOTION;
        if(sourcePiece instanceof King && ((King) sourcePiece).canSmallCast(board, startPos) && endX == 6 && (endY == 0 || endY == 7))
            moveType = MoveType.CASTLING_SMALL;
        if(sourcePiece instanceof King && ((King) sourcePiece).canBigCast(board, startPos) && endX == 1 && (endY == 0 || endY == 7))
            moveType = MoveType.CASTLING_SMALL;

        //create move object and store in history ( !!! MUST HAPPEN BEFORE MOVE IS PERFORMED !!! )
        Move move = new Move(isWhite, startPos, endPos, moveType);

        history.addMove(move);
        //if all checks passed, we do the actual move
        doMove(startPos, endPos);

        //update current player color
        currentPlayer = currentPlayer.switchPlayer();
        return true;
    }

    public void doMove(ChessBoardCell startPos, ChessBoardCell endPos) {
        //we put the moved piece on the new destination cell
        board.getCell(endPos.getX(), endPos.getY()).setPiece(startPos.getPiece());
        //we set the start piece from the initial tile position to Null piece
        board.getCell(startPos.getX(), startPos.getY()).setPiece(new Null(false));
    }

    public boolean undoMove(){
        if(!history.canUndo())
            return false;
        Move moveToUndo = history.undo();
        //reset captured piece
        board.getCell(moveToUndo.getEndPos().getX(), moveToUndo.getEndPos().getY()).setPiece(moveToUndo.getPieceKilled());
        //reset moved piece
        board.getCell(moveToUndo.getStartPos().getX(), moveToUndo.getStartPos().getY()).setPiece(moveToUndo.getPieceMoved());

        if(moveToUndo.getMoveType() == MoveType.CAPTURE)
            if(moveToUndo.isWhite())
                board.popBlack();
            else
                board.popWhite();

        return true;
    }

    public boolean redoMove() {
        if(!history.canRedo())
            return false;
        Move moveToRedo = history.redo();
        doMove(moveToRedo.getStartPos(), moveToRedo.getEndPos());

        if(moveToRedo.getMoveType() == MoveType.CAPTURE)
            if(moveToRedo.isWhite())
                board.addBlackDead(moveToRedo.getPieceKilled());
            else
                board.addWhiteDead(moveToRedo.getPieceKilled());

        return true;
    }

    public int lastKilled(){
        //0 if no kill made
        if (!history.checkLast().hasKilled()) return 0;
        //if last piece was white, then black died, return 2
        if (history.checkLast().isWhite()) return 2;
        //if last piece was black, white died, return 1
        return 1;
    }

    public void restart() {
        initiate();
        history.restart();
        board.restart();
    }

}
