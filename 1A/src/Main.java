import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.util.Comparator.comparing;

import java.util.*;

public class Main {

    private static boolean useUniformCost;
    private static Hashtable<Integer, Node> closedList;  // <State hash code, Node (contains g-value)>
    private static PriorityQueue<Node> openList;        // Expanded states
    private static World world;
    private static NodeComparator comparator;

    public static void main(String[] args) throws IOException {
        switch (args[0]){
            case "uniform-cost":
                useUniformCost = true;
                break;
            default:
                useUniformCost = false;
                break;
        }
        Vector<String> textFromFile = openFile(args[1]);
        world = new World(textFromFile);
        comparator = new NodeComparator();
        openList = new PriorityQueue<>(comparator);
        closedList = new Hashtable<>();
        vacuum(world);
    }

    private static void vacuum(World map) {
        Node currentNode = new Node(map);
        openList.add(currentNode);
        boolean unsuccessful = true;
        while(unsuccessful){
            if(openList.size() == 0){
                unsuccessful = true;
                break;
            }
            currentNode = openList.remove();
            closedList.put(currentNode.state.hashCode(), currentNode);
            if(currentNode.state.remainingDirt.size() == 0){
                unsuccessful = false;
                break;
            }else{
                List<Node> children = expandNode(currentNode);
                openList.addAll(children);
            }
        }
    }

    private static List<Node> expandNode(Node currentNode) {
        ArrayList<Node> options = new ArrayList<>();
        Location curLoc = currentNode.state.currentLocation;


        if(onDirt(currentNode.state.currentLocation)){
            // Vacuum
            State curState = currentNode.state;
            curState.remainingDirt.remove(currentNode.state.currentLocation);
            if(!closedList.containsKey(curState.hashCode())){
                options.add(new Node(curState, currentNode));
            }
        }

        // Assess the neighbors
        ArrayList<Location> neighborLocations = getNeighborLocations(curLoc.row, curLoc.col);
        for (Location loc: neighborLocations) {
            if(isWall(loc) || outsideMap(loc)){
                continue;
            }
            State curState = new State(currentNode.state.currentLocation, currentNode.state.remainingDirt);
            curState.currentLocation = loc;
            if(!closedList.containsKey(curState.hashCode())){
                options.add(new Node(curState, currentNode));
                System.out.println(loc.print());
            }
        }

        return options;
    }

    private static ArrayList<Location> getNeighborLocations(int row, int col) {
        ArrayList<Location> neighbors = new ArrayList<>();
        neighbors.add(new Location(row - 1, col));
        neighbors.add(new Location(row, col - 1));
        neighbors.add(new Location(row + 1, col));
        neighbors.add(new Location(row, col + 1));
        return neighbors;
    }

    private static boolean isWall(Location location){
        if(world.walls.contains(location)){
            return true;
        }
        return false;
    }

    private static boolean outsideMap(Location location){
        if(location.row < 0 || location.row >= world.row){
            return true;
        }
        if(location.col < 0 || location.col >= world.col){
            return true;
        }
        return false;
    }

    private static boolean onDirt(Location location){
        if (world.dirt.contains(location)){
            return true;
        }
        return false;
    }

    private static Vector<String> openFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
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
