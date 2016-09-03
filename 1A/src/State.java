import javafx.util.Pair;

import java.util.HashSet;
import java.util.Stack;
import java.lang.Object;
import java.util.Vector;

/**
 * Created by chasrickarby on 9/1/16.
 */
public class State {
    public Stack<Character> actions;
    public Location currentLocation;
    public HashSet<Location> remainingDirt;

    public State(Location location, HashSet<Location> dirt){
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.currentLocation.row;
        hash = 13 * hash + this.remainingDirt.size();
        hash = 13 * hash + this.currentLocation.col;
        return hash;
    }
}
