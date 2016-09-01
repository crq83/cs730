import javafx.util.Pair;
import java.util.Stack;
import java.util.Vector;

/**
 * Created by chasrickarby on 9/1/16.
 */
public class State {
    public Stack<Character> actions;
    public Pair<Integer, Integer> currentLocation;
    public Vector<Pair<Integer, Integer>> remainingDirt;

    public State(Pair<Integer,Integer> location, Vector<Pair<Integer,Integer>> dirt){
        remainingDirt = dirt;
        currentLocation = location;
        actions = new Stack<>();
    }

    @Override
    public boolean equals(Object ob){
        if (ob == null) return false;
        if (ob.getClass() != getClass()) return false;
        State other = (State) ob;
        if(other.currentLocation == currentLocation && other.remainingDirt == remainingDirt) {
            return true;
        }else{
            return false;
        }
    }
}
