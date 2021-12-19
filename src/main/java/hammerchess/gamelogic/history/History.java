package hammerchess.gamelogic.history;

import java.util.Stack;

public class History {
    private Stack<Move> moves = new Stack<>();
    private Stack<Move> toRedo = new Stack<>();

    public void addMove(Move move){
        moves.add(move);
        if(!toRedo.empty())
            toRedo.clear();
    }

    public Move checkLast(){
        return moves.peek();
    }

    public boolean canUndo(){
        return !moves.empty();
    }
    public boolean canRedo(){
        return !toRedo.empty();
    }

    public Move undo(){
        toRedo.add(moves.peek());
        return moves.pop();
    }
    public Move redo(){
        moves.add(toRedo.peek());
        return toRedo.pop();
    }

    public void clearRedo(){
        toRedo.clear();
    }
    public void restart() {
        moves.clear();
        toRedo.clear();
    }
}
