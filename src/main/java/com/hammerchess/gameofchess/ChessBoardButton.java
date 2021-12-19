package com.hammerchess.gameofchess;

import javafx.scene.control.Button;

//we add board position information to existing button class
public class ChessBoardButton extends Button {
    private int x, y;
    private boolean white;

    private boolean selected;
    ChessBoardButton(int x, int y, boolean white){
        super();
        this.x = x;
        this.y = y;
        this.white = white;
        selected = false;
    }
    ChessBoardButton(String text, int x, int y, boolean white){
        super(text);
        this.x = x;
        this.y = y;
        this.white = white;
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean isWhite() {
        return white;
    }
    public void setWhite(boolean white) {
        this.white = white;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

}
