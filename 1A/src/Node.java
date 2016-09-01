/**
 * Created by chasrickarby on 9/1/16.
 */
public class Node {
    public int cost;
    public Node parent;
    public State state;

    public Node (World currentState){
        state = new State(currentState.startingLocation, currentState.dirt);
        cost = 0;
        parent = null;
    }
}
