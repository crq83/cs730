import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;

public class Main {

    private static boolean useUniformCost;
    private static HashMap<Integer, State> closedList;  // <G-Value, Associated State>
    private static Vector<Node> openList;               // Expanded states

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
        World world = new World(textFromFile);
        vacuum(world);
    }

    private static void vacuum(World map) {
        Node currentNode = new Node(map);
        openList.addElement(currentNode);
        boolean unsuccessful = true;
        while(unsuccessful){
            if(openList.size() == 0){
                break;
            }
            currentNode = openList.firstElement();
            openList.remove(currentNode);
            if(currentNode.state.remainingDirt.size() == 0){
                unsuccessful = false;
                break;
            }else{
                Vector<Node> children = expandNode(currentNode);
                
            }
        }

    }

    private static Vector<Node> expandNode(Node currentNode) {
        Vector<Node> neighbors = new Vector<>();
        Pair<Integer,Integer> curLoc = currentNode.state.currentLocation;

        return neighbors;
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
