import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Move> history;

    public History(List<Move> history) {
        this.history = history;
    }

    public History() {
        this.history = new ArrayList();
    }

    public void addtoHistory(Move Zug) {
        history.add(Zug);
    }

    public void removeMoveFromHistory(int index) {
        history.remove(index);
    }

    public Move getLastMove() {
    	if (history.size() == 0) {
    		return null;
    	}
    	else {
    		return history.get(history.size() - 1);	
    	}
    }

    public Move getMoveAtIndex(int index) {
        return history.get(index);
    }
    public void clearHistory() {
        history.clear();
    }

}
