import java.util.ArrayDeque;

/**
 * Created by chasrickarby on 9/1/16.
 */
public class Node {
    public int cost;
    public Node parent;
    public State state;
    public ArrayDeque<Character> actions;

    public Node (World currentState){
        state = new State(currentState.startingLocation, currentState.dirt);
        cost = 0;
        parent = null;
        actions = new ArrayDeque<>();
    }

    public Node (State curState, Node parent, ArrayDeque<Character> queue){
        this.parent = parent;
        this.state = new State(curState.currentLocation, curState.remainingDirt);
        this.cost = parent.getCost() + 1;
        actions = new ArrayDeque<>(queue);
    }

    public int getCost(){
        return cost;
    }
}
