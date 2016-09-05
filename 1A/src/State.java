import java.util.HashSet;
import java.lang.Object;

/**
 * Created by chasrickarby on 9/1/16.
 */
public class State {
    public Location currentLocation;
    public HashSet<Location> remainingDirt;

    public State(Location location, HashSet<Location> dirt){
        remainingDirt = new HashSet<>(dirt);
        currentLocation = location;
    }

    @Override
    public boolean equals(Object ob){
        if (ob == null) return false;
        if (ob.getClass() != getClass()) return false;
        State other = (State) ob;
        return other.currentLocation == currentLocation && other.remainingDirt == remainingDirt;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.currentLocation.row;
        hash = 11 * hash + this.remainingDirt.size();
        hash = 17 * hash + this.currentLocation.col;
        return hash;
    }
}
