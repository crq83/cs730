import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.util.Comparator.comparing;

import java.util.*;

public class Main {

    private static boolean useUniformCost;
    private static Hashtable<Integer, Node> closedList;  // <State hash code, Node (contains g-value)>
    private static World world;
    private static int nodesGenerated, nodesExpanded, initialHash;

    public static void main(String[] args) throws IOException {
        switch (args[0]){
            case "uniform-cost":
                useUniformCost = true;
                break;
            default:
                useUniformCost = false;
                break;
        }
        Vector<String> textFromFile = openFile();
        world = new World(textFromFile);
        closedList = new Hashtable<>();
        vacuum(world);
    }

    private static void vacuum(World map) {
        if (useUniformCost){
            uniformCostSearch(map);
        }else {
            depthFirstSearch(map);
        }
    }

    private static void uniformCostSearch(World map) {
        Node currentNode = new Node(map);
        NodeComparator comparator = new NodeComparator();
        PriorityQueue<Node> openList = new PriorityQueue<>(comparator);
        openList.add(currentNode);
        while(true){
            if(openList.size() == 0 && currentNode.state.remainingDirt.size() != 0){
                System.out.println("Too hard to clean, time to hire The Maids.");
                break;
            }

            currentNode = openList.remove();
            closedList.put(currentNode.state.hashCode(), currentNode);

            List<Node> children = UCSexpandNode(currentNode);
            nodesExpanded++;
            if(children.size() == 1 && children.get(0).state.remainingDirt.size() == 0){
                print(children.get(0).actions);
                break;
            }
            openList.addAll(children);
        }
    }

    private static void depthFirstSearch(World map) {
        Node currentNode = new Node(map);
        initialHash = currentNode.state.hashCode();
        Stack<Node> openList = new Stack<>();
        openList.push(currentNode);
        while(true){
            if(openList.size() == 0 && currentNode.state.remainingDirt.size() != 0){
                System.out.println("Too hard to clean, time to hire The Maids.");
                break;
            }

            currentNode = openList.pop();

            if(currentNode.state.remainingDirt.size() == 0){
                print(currentNode.actions);
                break;
            }else{
                List<Node> children = DFSexpandNode(currentNode);
                nodesExpanded++;
                children.forEach(openList::push);
            }
        }
    }

    private static List<Node> DFSexpandNode(Node currentNode) {
        ArrayList<Node> options = new ArrayList<>();
        Location curLoc = currentNode.state.currentLocation;

        if(onDirt(currentNode.state.currentLocation)){
            // Vacuum
            State curState = currentNode.state;
            ArrayDeque<Character> actions = new ArrayDeque<>(currentNode.actions);
            curState.remainingDirt.remove(currentNode.state.currentLocation);
            actions.add('V');
            Node newNode = new Node(curState, currentNode, actions);
            nodesGenerated++;
            options.add(newNode);
            if(currentNode.state.remainingDirt.size() == 0){
                return options;
            }
        }

        // Assess the neighbors
        ArrayList<Location> neighborLocations = getNeighborLocations(curLoc.row, curLoc.col);
        for (Location loc: neighborLocations) {
            nodesGenerated++;
            if(isWall(loc) || outsideMap(loc)){
                continue;
            }
            State curState = new State(currentNode.state.currentLocation, currentNode.state.remainingDirt);
            ArrayDeque<Character> actions = new ArrayDeque<>(currentNode.actions);
            curState.currentLocation = loc;
            actions.add(loc.direction);
            Node newNode = new Node(curState, currentNode, actions);
            if(!beenThere(newNode)){
                options.add(newNode);
            }
        }

        return options;
    }

    private static boolean beenThere(Node currentNode) {
        int currentHashCode = currentNode.state.hashCode();
        while(currentNode.parent != null){
            if(currentHashCode == currentNode.parent.state.hashCode()){
                return true;
            }
            currentNode = currentNode.parent;
        }
        return false;
    }

    private static void print(ArrayDeque<Character> actions) {
        for (Character c:actions) {
            System.out.println(c);
        }
        System.out.println(nodesGenerated + " nodes generated");
        System.out.println(nodesExpanded + " nodes expanded");
    }

    private static List<Node> UCSexpandNode(Node currentNode) {
        currentNode.state.currentLocation.direction = '\0';
        ArrayList<Node> options = new ArrayList<>();
        Location curLoc = currentNode.state.currentLocation;

        if(onDirt(currentNode.state.currentLocation)){
            // Vacuum
            State curState = new State(currentNode.state.currentLocation, currentNode.state.remainingDirt);
            ArrayDeque<Character> actions = new ArrayDeque<>(currentNode.actions);
            curState.remainingDirt.remove(currentNode.state.currentLocation);
            nodesGenerated++;
            if(!closedList.containsKey(curState.hashCode())){
                actions.add('V');
                options.add(new Node(curState, currentNode, actions));
                if(curState.remainingDirt.size() == 0){
                    return options;
                }
            }
        }

        // Assess the neighbors
        ArrayList<Location> neighborLocations = getNeighborLocations(curLoc.row, curLoc.col);
        for (Location loc: neighborLocations) {
            nodesGenerated++;
            if(isWall(loc) || outsideMap(loc)){
                continue;
            }
            State curState = new State(currentNode.state.currentLocation, currentNode.state.remainingDirt);
            ArrayDeque<Character> actions = new ArrayDeque<>(currentNode.actions);
            curState.currentLocation = loc;
            if(!closedList.containsKey(curState.hashCode())){
                actions.add(loc.direction);
                options.add(new Node(curState, currentNode, actions));
            }
        }

        return options;
    }

    private static ArrayList<Location> getNeighborLocations(int row, int col) {
        ArrayList<Location> neighbors = new ArrayList<>();
        neighbors.add(new Location(row - 1, col, 'N')); // North
        neighbors.add(new Location(row, col - 1, 'W')); // West
        neighbors.add(new Location(row + 1, col, 'S')); // South
        neighbors.add(new Location(row, col + 1, 'E')); // East
        return neighbors;
    }

    private static boolean isWall(Location location){
        return world.walls.contains(location);
    }

    private static boolean outsideMap(Location location) {
        return location.row < 0 || location.row >= world.row || location.col < 0 || location.col >= world.col;
    }

    private static boolean onDirt(Location location){
        return world.dirt.contains(location);
    }

    private static Vector<String> openFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Vector<String> data = new Vector<>();

        String line = reader.readLine();

        while(line != null) {
            data.addElement(line);
            line = reader.readLine();
        }
        reader.close();
        return data;
    }
}
