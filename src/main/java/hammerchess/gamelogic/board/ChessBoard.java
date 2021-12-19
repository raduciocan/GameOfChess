package hammerchess.gamelogic.board;

import hammerchess.gamelogic.pieces.*;

import java.util.Stack;


public class ChessBoard {
    private Stack<Piece> pieces;
    private Stack<Piece> whiteDead;
    private Stack<Piece> blackDead;
    private ChessBoardCell[][] cells;

    public ChessBoard() {
        pieces = new Stack<>();
        whiteDead = new Stack<>();
        blackDead = new Stack<>();
        cells = new ChessBoardCell[8][8];
        initiate();
        addPiecesToPiecesList();
    }

    public Stack<Piece> getWhiteDead() {
        return whiteDead;
    }
    public Stack<Piece> getBlackDead() {
        return blackDead;
    }
    public void addWhiteDead(Piece piece){
        whiteDead.add(piece);
    }
    public void addBlackDead(Piece piece){
        blackDead.add(piece);
    }
    public void popWhite() {
        whiteDead.pop();
    }
    public void popBlack() {
        blackDead.pop();
    }

    public boolean isValid (int x, int y) {
        return 0 <= x && x < 8 && 0 <= y && y < 8;
    }
    //first param is y (the row) then x (the column)
    public ChessBoardCell getCell(int x, int y) {
        return cells[y][x];
    }

    public void initiate() {
        //trick to initialize first rows for both colors in less lines
        for (int y = 0; y < 8; y += 7) {
            cells[y][0] = new ChessBoardCell(0, y, new Rook(y == 0));
            cells[y][1] = new ChessBoardCell(1, y, new Knight(y == 0));
            cells[y][2] = new ChessBoardCell(2, y, new Bishop(y == 0));
            cells[y][3] = new ChessBoardCell(3, y, new Queen(y == 0));
            cells[y][4] = new ChessBoardCell(4, y, new King(y == 0));
            cells[y][5] = new ChessBoardCell(5, y, new Bishop(y == 0));
            cells[y][6] = new ChessBoardCell(6, y, new Knight(y == 0));
            cells[y][7] = new ChessBoardCell(7, y, new Rook(y == 0));
        }
        //pawns for both colors
        for(int x = 0; x < 8; x++){
            cells[1][x] = new ChessBoardCell(x, 1, new Pawn(true));
            cells[6][x] = new ChessBoardCell(x, 6, new Pawn(false));
        }
        //empty cells
        Piece nullPiece = new Null(false);
        for (int y = 2; y < 6; y++) {
            for (int x = 0; x < 8; x++) {
                cells[y][x] = new ChessBoardCell(x, y, nullPiece);
            }
        }
    }

    public void restart(){
        initiate();
        pieces.clear();
        whiteDead.clear();
        blackDead.clear();
    }

    private void addPiecesToPiecesList() {
        //white pieces
        for(int y = 0; y < 2; y++){
            for(int x = 0; x < 8; x++)
                pieces.add(cells[y][x].getPiece());
        }
        //black pieces
        for(int y = 7; y > 5; y--){
            for(int x = 0; x < 8; x++)
                pieces.add(cells[y][x].getPiece());
        }
    }

    public void testPrint(){
        for (ChessBoardCell[] cellrow : cells)
        {
            for (ChessBoardCell cell : cellrow)
            {
                System.out.print(cell.getX() + "" + cell.getY() + " ");
            }
            System.out.println();
        }
    }

}
