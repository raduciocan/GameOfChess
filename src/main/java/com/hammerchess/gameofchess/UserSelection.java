package com.hammerchess.gameofchess;

public class UserSelection {
    private int fromX, fromY, toX, toY;
    private boolean complete, empty;

    UserSelection(){
        reset();
    }

    public void reset() {
        fromX = toX = fromY = toY = 0;
        complete = false;
        empty = true;
    }

    public void setFrom(int x, int y){
        fromX = x;
        fromY = y;
        empty = false;
    }

    public void setTo(int x, int y){
        toX = x;
        toY = y;
        complete = true;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public boolean isComplete() {
        return complete;
    }

    public boolean isEmpty() {
        return empty;
    }

}
